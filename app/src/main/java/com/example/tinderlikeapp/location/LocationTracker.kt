package com.example.tinderlikeapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class LocationState {
    data class Success(val latitude: Double, val longitude: Double, val isGps: Boolean) : LocationState()
    data class PermissionDenied(val fallbackLat: Double = 40.7128, val fallbackLng: Double = -74.0060) : LocationState()
    data class Unavailable(val fallbackLat: Double = 40.7128, val fallbackLng: Double = -74.0060) : LocationState()
}

class LocationTracker(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context.applicationContext)

    val defaultLat = 40.7128
    val defaultLng = -74.0060

    fun hasLocationPermission(): Boolean {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineGranted || coarseGranted
    }

    @SuppressLint("MissingPermission")
    fun getLocationFlow(): Flow<LocationState> = callbackFlow {
        if (!hasLocationPermission()) {
            trySend(LocationState.PermissionDenied(defaultLat, defaultLng))
            close()
            return@callbackFlow
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    trySend(LocationState.Success(location.latitude, location.longitude, isGps = true))
                }
            }
        }

        // Fetch last known location for instantaneous initial distance calculation
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    trySend(LocationState.Success(location.latitude, location.longitude, isGps = true))
                }
            }
            .addOnFailureListener {
                trySend(LocationState.Unavailable(defaultLat, defaultLng))
            }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000L
        ).setMinUpdateIntervalMillis(2000L).build()

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            trySend(LocationState.PermissionDenied(defaultLat, defaultLng))
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }
}
