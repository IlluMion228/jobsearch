package com.example.tinderlikeapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tinderlikeapp.data.JobRepository
import com.example.tinderlikeapp.location.LocationState
import com.example.tinderlikeapp.location.LocationTracker
import com.example.tinderlikeapp.model.Job
import com.example.tinderlikeapp.model.JobCategory
import com.example.tinderlikeapp.model.MatchedJob
import com.example.tinderlikeapp.model.SwipeType
import com.example.tinderlikeapp.model.WorkerProfile
import kotlinx.coroutines.Job as CoroutineJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class JobViewModel @JvmOverloads constructor(
    application: Application,
    private val repository: JobRepository = JobRepository(),
    customLocationTracker: LocationTracker? = null
) : AndroidViewModel(application) {

    private val allJobs = repository.getSampleJobs()

    private val locationTracker: LocationTracker? by lazy {
        customLocationTracker ?: try {
            LocationTracker(getApplication())
        } catch (_: Exception) {
            null
        }
    }

    private var locationTrackingJob: CoroutineJob? = null

    private val _profile = MutableStateFlow(WorkerProfile())
    val profile: StateFlow<WorkerProfile> = _profile.asStateFlow()

    private val _userLat = MutableStateFlow(40.7128)
    private val _userLng = MutableStateFlow(-74.0060)

    private val _locationStatusText = MutableStateFlow("Location: city center (default)")
    val locationStatusText: StateFlow<String> = _locationStatusText.asStateFlow()

    private val _swipeHistory = MutableStateFlow<LinkedHashMap<String, SwipeType>>(LinkedHashMap())
    val swipeHistory: StateFlow<Map<String, SwipeType>> = _swipeHistory.asStateFlow()

    private val _matchedJobs = MutableStateFlow<List<MatchedJob>>(emptyList())
    val matchedJobs: StateFlow<List<MatchedJob>> = _matchedJobs.asStateFlow()

    private val _activeJobs = MutableStateFlow<List<Job>>(emptyList())
    val activeJobs: StateFlow<List<Job>> = _activeJobs.asStateFlow()

    private val _matchDialogJob = MutableStateFlow<Job?>(null)
    val matchDialogJob: StateFlow<Job?> = _matchDialogJob.asStateFlow()

    init {
        refreshActiveDeck()
    }

    fun startLocationTracking() {
        val tracker = locationTracker
        if (tracker == null) {
            updateUserLocation(40.7128, -74.0060, "Location: city center (default)")
            return
        }

        if (!tracker.hasLocationPermission()) {
            updateUserLocation(tracker.defaultLat, tracker.defaultLng, "Location: permission needed, using city center")
            return
        }

        if (locationTrackingJob?.isActive == true) {
            return
        }

        locationTrackingJob = viewModelScope.launch {
            tracker.getLocationFlow().collect { state ->
                when (state) {
                    is LocationState.Success -> {
                        updateUserLocation(state.latitude, state.longitude, "Location: GPS active")
                    }
                    is LocationState.PermissionDenied -> {
                        updateUserLocation(state.fallbackLat, state.fallbackLng, "Location: permission needed, using city center")
                    }
                    is LocationState.Unavailable -> {
                        updateUserLocation(state.fallbackLat, state.fallbackLng, "Location: GPS off, using city center")
                    }
                }
            }
        }
    }

    fun hasLocationPermission(): Boolean {
        return locationTracker?.hasLocationPermission() == true
    }

    fun updateUserLocation(lat: Double, lng: Double, statusLabel: String) {
        _userLat.value = lat
        _userLng.value = lng
        _locationStatusText.value = statusLabel
        refreshActiveDeck()
    }

    fun updateProfile(newProfile: WorkerProfile) {
        _profile.value = newProfile
        refreshActiveDeck()
    }

    fun setCategoryFilter(category: JobCategory) {
        _profile.value = _profile.value.copy(selectedCategory = category)
        refreshActiveDeck()
    }

    fun setMaxDistanceFilter(distanceKm: Float) {
        _profile.value = _profile.value.copy(maxDistanceKm = distanceKm)
        refreshActiveDeck()
    }

    fun setMinHourlyRateFilter(minRate: Float) {
        _profile.value = _profile.value.copy(minHourlyRate = minRate)
        refreshActiveDeck()
    }

    fun swipeJob(job: Job, swipeType: SwipeType) {
        val history = LinkedHashMap(_swipeHistory.value)
        history[job.id] = swipeType
        _swipeHistory.value = history

        if (swipeType == SwipeType.LIKE || swipeType == SwipeType.SUPER_LIKE) {
            val isMatch = if (swipeType == SwipeType.SUPER_LIKE) {
                true
            } else {
                Random.nextDouble() <= job.employerMatchProbability
            }

            val newMatch = MatchedJob(
                job = job,
                isMutualMatch = isMatch,
                initialMessage = if (isMatch) {
                    "Hi! We saw your application for ${job.title} and would love to invite you for a quick interview!"
                } else {
                    "Application submitted to ${job.company}. Waiting for employer review."
                }
            )

            _matchedJobs.value = listOf(newMatch) + _matchedJobs.value

            if (isMatch) {
                _matchDialogJob.value = job
            }
        }

        refreshActiveDeck()
    }

    fun undoLastSwipe() {
        val history = LinkedHashMap(_swipeHistory.value)
        if (history.isEmpty()) return

        val lastJobId = history.keys.last()
        history.remove(lastJobId)
        _swipeHistory.value = history

        _matchedJobs.value = _matchedJobs.value.filter { it.job.id != lastJobId }

        refreshActiveDeck()
    }

    fun dismissMatchDialog() {
        _matchDialogJob.value = null
    }

    fun resetSwipes() {
        _swipeHistory.value = LinkedHashMap()
        _matchedJobs.value = emptyList()
        _matchDialogJob.value = null
        refreshActiveDeck()
    }

    private fun refreshActiveDeck() {
        val currentProfile = _profile.value
        val swipedIds = _swipeHistory.value.keys
        val lat = _userLat.value
        val lng = _userLng.value

        _activeJobs.value = allJobs
            .map { job -> job.withCalculatedDistance(lat, lng) }
            .filter { job ->
                val isNotSwiped = !swipedIds.contains(job.id)
                val matchesCategory = currentProfile.selectedCategory == JobCategory.ALL || job.category == currentProfile.selectedCategory
                val matchesDistance = job.calculatedDistanceKm <= currentProfile.maxDistanceKm
                val matchesRate = job.hourlyRate >= currentProfile.minHourlyRate

                isNotSwiped && matchesCategory && matchesDistance && matchesRate
            }
            .sortedBy { it.calculatedDistanceKm }
    }
}
