package com.example.tinderlikeapp.model

import java.util.Locale
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val category: JobCategory,
    val hourlyRate: Double,
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val shiftInfo: String,
    val description: String,
    val tags: List<String>,
    val employerMatchProbability: Double = 0.65,
    val primaryColorHex: Long = 0xFF2196F3,
    val postedAgo: String = "Today",
    val calculatedDistanceKm: Double = 0.0
) {
    val companyInitials: String
        get() = company.split(" ").filter { it.isNotBlank() }.take(2).joinToString("") { it.first().uppercase() }

    val formattedRate: String
        get() = "$${String.format(Locale.US, "%.2f", hourlyRate)}/hr"

    val formattedDistance: String
        get() = if (calculatedDistanceKm < 1.0) "${(calculatedDistanceKm * 1000).toInt()} m away" else "${String.format(Locale.US, "%.1f", calculatedDistanceKm)} km away"

    fun withCalculatedDistance(userLat: Double, userLng: Double): Job {
        val distance = calculateHaversineKm(userLat, userLng, latitude, longitude)
        return this.copy(calculatedDistanceKm = distance)
    }

    companion object {
        fun calculateHaversineKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val r = 6371.0 // Earth radius in km
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = sin(dLat / 2) * sin(dLat / 2) +
                    cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                    sin(dLon / 2) * sin(dLon / 2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))
            return r * c
        }
    }
}
