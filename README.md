Frontline Jobs Mobile Prototype

Android app prototype for frontline job discovery built with Kotlin and Jetpack Compose.

Features

Swipeable job cards with right swipe to apply and left swipe to pass.
Distance and pay rate filtering using real device GPS location.
Simulated employer match system.
Matches tab with direct message simulation.
Profile setup for job category and rate preferences.
Undo action for previous swipe.

Tech Stack

Kotlin
Jetpack Compose
Material 3
AndroidViewModel and StateFlow
FusedLocationProviderClient
JUnit

Technical Decisions

Distance calculation uses FusedLocationProviderClient to request device GPS coordinates and calculate exact Haversine distance to each job. If GPS is disabled or permission is denied, it falls back to default coordinates.
Employer response uses probability rules. Super Like forces a mutual match.
State is kept in memory inside ViewModel.

Testing Steps

Launch app and grant location permission when prompted.
Swipe job cards or adjust max distance slider to test distance filtering.
Swipe right or use Super Like to generate mutual match and check Matches tab.
Edit profile preferences on Profile tab.

Time Spent

17:30 till 20:00, 1 hour break, 20:00 till 01:00.
