package com.example.ygocarddex.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SidebarContent() {
    var selectedOption by remember { mutableStateOf("Home") } // State to track selected option

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 70.dp) // To position content below the navbar
    ) {
        SidebarOption(
            icon = Icons.Default.Home,
            label = "Home",
            isSelected = selectedOption == "Home",
            onClick = { selectedOption = "Home" }
        )
        SidebarOption(
            icon = Icons.Default.Favorite,
            label = "Favorites",
            isSelected = selectedOption == "Favorites",
            onClick = { selectedOption = "Favorites" }
        )
        SidebarOption(
            icon = Icons.Default.Settings,
            label = "Settings",
            isSelected = selectedOption == "Settings",
            onClick = { selectedOption = "Settings" }
        )
        SidebarOption(
            icon = Icons.Default.Info,
            label = "About",
            isSelected = selectedOption == "About",
            onClick = { selectedOption = "About" }
        )
    }
}

@Composable
fun SidebarOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean = false, // Add a flag for selection
    onClick: () -> Unit // Callback for click events
) {
    // Animate the background and text color changes
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent,
        label = "background color"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        label = "text color"
    )

    // Animate icon size based on selection
    val iconSize by animateFloatAsState(
        targetValue = if (isSelected) 32f else 24f, // Enlarge icon if selected
        label = "icon size"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Handle click events
            .background(backgroundColor) // Animated background color
            .padding(horizontal = 16.dp, vertical = 8.dp), // Inner padding for content
        verticalAlignment = Alignment.CenterVertically // Align icon and text vertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(iconSize.dp), // Apply animated icon size
            tint = textColor // Animated text/icon color
        )
        Spacer(modifier = Modifier.width(16.dp)) // Space between icon and text
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = textColor // Animated text color
        )
    }
}
