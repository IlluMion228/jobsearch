package com.example.tinderlikeapp.model

enum class SwipeType {
    LIKE,
    PASS,
    SUPER_LIKE
}

data class MatchedJob(
    val job: Job,
    val matchedAtTimestamp: Long = System.currentTimeMillis(),
    val isMutualMatch: Boolean = true,
    val initialMessage: String = "Hi! We saw your application and we'd love to chat about your availability."
)
