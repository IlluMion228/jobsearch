package com.example.tinderlikeapp.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tinderlikeapp.ui.components.MatchDialog
import com.example.tinderlikeapp.ui.screens.DiscoverScreen
import com.example.tinderlikeapp.ui.screens.MatchesScreen
import com.example.tinderlikeapp.ui.screens.ProfileScreen

@Composable
fun MainScreen(
    viewModel: JobViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val matchedJobs by viewModel.matchedJobs.collectAsState()
    val matchDialogJob by viewModel.matchDialogJob.collectAsState()

    val mutualMatchCount = matchedJobs.count { it.isMutualMatch }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(imageVector = Icons.Default.Swipe, contentDescription = "Discover Jobs") },
                    label = { Text("Discover") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        if (mutualMatchCount > 0) {
                            BadgedBox(
                                badge = { Badge { Text(mutualMatchCount.toString()) } }
                            ) {
                                Icon(imageVector = Icons.Default.Badge, contentDescription = "Matches")
                            }
                        } else {
                            Icon(imageVector = Icons.Default.Badge, contentDescription = "Matches")
                        }
                    },
                    label = { Text("Matches") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> DiscoverScreen(viewModel = viewModel)
                1 -> MatchesScreen(viewModel = viewModel)
                2 -> ProfileScreen(viewModel = viewModel)
            }
        }
    }

    // Mutual Match Dialog Overlay
    matchDialogJob?.let { job ->
        MatchDialog(
            job = job,
            onDismiss = { viewModel.dismissMatchDialog() },
            onOpenMatches = {
                viewModel.dismissMatchDialog()
                selectedTab = 1
            }
        )
    }
}
