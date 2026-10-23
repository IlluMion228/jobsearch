package com.example.tinderlikeapp.model

data class WorkerProfile(
    val name: String = "Alex Rivera",
    val title: String = "Frontline Specialist",
    val bio: String = "Ready for flexible shifts in hospitality, retail or logistics. Experienced & reliable.",
    val minHourlyRate: Float = 18f,
    val maxDistanceKm: Float = 25f,
    val selectedCategory: JobCategory = JobCategory.ALL
)
