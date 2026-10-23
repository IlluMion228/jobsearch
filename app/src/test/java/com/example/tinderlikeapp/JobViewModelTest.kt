package com.example.tinderlikeapp

import android.app.Application
import com.example.tinderlikeapp.model.JobCategory
import com.example.tinderlikeapp.model.SwipeType
import com.example.tinderlikeapp.ui.JobViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class JobViewModelTest {

    private lateinit var viewModel: JobViewModel

    @Before
    fun setUp() {
        viewModel = JobViewModel(Application())
    }

    @Test
    fun initialDeck_containsFilteredSampleJobs() {
        val initialJobs = viewModel.activeJobs.value
        assertTrue("Active jobs should not be empty initially", initialJobs.isNotEmpty())
    }

    @Test
    fun distanceFilter_reducesActiveJobs() {
        val countBefore = viewModel.activeJobs.value.size
        
        // Set max distance to 3 km
        viewModel.setMaxDistanceFilter(3f)
        val countAfter = viewModel.activeJobs.value.size

        assertTrue("Distance filter should narrow job deck", countAfter < countBefore)
        assertTrue("All active jobs should be within 3 km", viewModel.activeJobs.value.all { it.calculatedDistanceKm <= 3.0 })
    }

    @Test
    fun categoryFilter_filtersBySelectedCategory() {
        viewModel.setCategoryFilter(JobCategory.HOSPITALITY)
        
        val activeJobs = viewModel.activeJobs.value
        assertTrue("Active jobs should only contain Hospitality", activeJobs.all { it.category == JobCategory.HOSPITALITY })
    }

    @Test
    fun swipingJob_removesItFromActiveDeckAndSavesHistory() {
        val topJob = viewModel.activeJobs.value.first()
        viewModel.swipeJob(topJob, SwipeType.LIKE)

        val activeJobs = viewModel.activeJobs.value
        assertTrue("Swiped job should no longer be in active deck", activeJobs.none { it.id == topJob.id })
        assertEquals(SwipeType.LIKE, viewModel.swipeHistory.value[topJob.id])
    }

    @Test
    fun undoLastSwipe_restoresSwipedJobToDeck() {
        val topJob = viewModel.activeJobs.value.firstOrNull()
        assertNotNull(topJob)
        viewModel.swipeJob(topJob!!, SwipeType.PASS)
        
        viewModel.undoLastSwipe()

        val restoredTopJob = viewModel.activeJobs.value.firstOrNull()
        assertNotNull("Top job should be restored", restoredTopJob)
        assertEquals(topJob.id, restoredTopJob?.id)
    }

    @Test
    fun superLike_guaranteesMutualMatch() {
        val topJob = viewModel.activeJobs.value.firstOrNull()
        assertNotNull(topJob)
        viewModel.swipeJob(topJob!!, SwipeType.SUPER_LIKE)

        val matches = viewModel.matchedJobs.value
        assertTrue("Super like must result in mutual match", matches.any { it.job.id == topJob.id && it.isMutualMatch })
        assertNotNull("Match dialog job should be set", viewModel.matchDialogJob.value)
    }
}
