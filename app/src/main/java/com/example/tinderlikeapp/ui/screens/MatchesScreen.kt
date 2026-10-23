package com.example.tinderlikeapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinderlikeapp.model.MatchedJob
import com.example.tinderlikeapp.ui.JobViewModel
import com.example.tinderlikeapp.ui.components.JobDetailBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchesScreen(
    viewModel: JobViewModel,
    modifier: Modifier = Modifier
) {
    val matchedJobs by viewModel.matchedJobs.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }

    var selectedMatchedJob by remember { mutableStateOf<MatchedJob?>(null) }
    val sheetState = rememberModalBottomSheetState()

    val mutualMatches = matchedJobs.filter { it.isMutualMatch }
    val pendingLikes = matchedJobs.filter { !it.isMutualMatch }

    val currentList = if (selectedTab == 0) mutualMatches else pendingLikes

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        Text(
            text = "Matches & Applications",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )

        // Segmented Tabs
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = {
                    Text(
                        text = "Mutual Matches (${mutualMatches.size})",
                        fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = {
                    Text(
                        text = "Saved Likes (${pendingLikes.size})",
                        fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (currentList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (selectedTab == 0) "No mutual matches yet" else "No saved job likes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Swipe right on jobs in the Discover deck to get matched with employers!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(currentList, key = { it.job.id }) { item ->
                    MatchedJobCard(
                        matchedJob = item,
                        onClick = { selectedMatchedJob = item }
                    )
                }
            }
        }
    }

    // Detail Bottom Sheet
    selectedMatchedJob?.let { item ->
        JobDetailBottomSheet(
            matchedJob = item,
            sheetState = sheetState,
            onDismiss = { selectedMatchedJob = null }
        )
    }
}

@Composable
fun MatchedJobCard(
    matchedJob: MatchedJob,
    onClick: () -> Unit
) {
    val job = matchedJob.job

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = Color(job.primaryColorHex).copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = job.companyInitials, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = job.company,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = job.formattedRate,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = job.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = job.formattedDistance,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (matchedJob.isMutualMatch) Color(0xFFE8F5E9) else Color(0xFFFFF3E0)
                    ) {
                        Text(
                            text = if (matchedJob.isMutualMatch) "Match!" else "Pending",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (matchedJob.isMutualMatch) Color(0xFF2E7D32) else Color(0xFFE65100),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
