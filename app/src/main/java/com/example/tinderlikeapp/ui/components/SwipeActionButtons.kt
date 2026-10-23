package com.example.tinderlikeapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tinderlikeapp.model.SwipeType

@Composable
fun SwipeActionButtons(
    onSwipe: (SwipeType) -> Unit,
    onUndo: () -> Unit,
    canUndo: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Undo Button
        FloatingActionButton(
            onClick = { if (canUndo) onUndo() },
            shape = CircleShape,
            containerColor = if (canUndo) Color(0xFFFFF3E0) else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (canUndo) Color(0xFFFF9800) else Color.Gray,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp),
            modifier = Modifier.size(46.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Undo Last Swipe",
                modifier = Modifier.size(22.dp)
            )
        }

        // Pass Button (Dislike)
        FloatingActionButton(
            onClick = { onSwipe(SwipeType.PASS) },
            shape = CircleShape,
            containerColor = Color(0xFFFFEBEE),
            contentColor = Color(0xFFE53935),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            modifier = Modifier.size(62.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Pass Job",
                modifier = Modifier.size(32.dp)
            )
        }

        // Super Like Button
        FloatingActionButton(
            onClick = { onSwipe(SwipeType.SUPER_LIKE) },
            shape = CircleShape,
            containerColor = Color(0xFFE3F2FD),
            contentColor = Color(0xFF1E88E5),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp),
            modifier = Modifier.size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Super Like Job",
                modifier = Modifier.size(26.dp)
            )
        }

        // Like Button (Apply)
        FloatingActionButton(
            onClick = { onSwipe(SwipeType.LIKE) },
            shape = CircleShape,
            containerColor = Color(0xFFE8F5E9),
            contentColor = Color(0xFF4CAF50),
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            modifier = Modifier.size(62.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Apply to Job",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
