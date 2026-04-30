package com.example.helplineapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Person

import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TrackScreen() {
    var isTrackMyRequest by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F0FE)) // Soft map background
    ) {
        // Top App Bar with Toggle (floating over the "map")
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(4.dp)
                    .shadow(4.dp, RoundedCornerShape(50)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToggleOption(
                    text = "Track My Request",
                    isSelected = isTrackMyRequest,
                    onClick = { isTrackMyRequest = true }
                )
                ToggleOption(
                    text = "My Helping Tasks",
                    isSelected = !isTrackMyRequest,
                    onClick = { isTrackMyRequest = false }
                )
            }
        }

        // Mock Map Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // Map Route Line
            Canvas(modifier = Modifier.fillMaxSize()) {
                val start = Offset(size.width * 0.2f, size.height * 0.8f)
                val mid = Offset(size.width * 0.5f, size.height * 0.5f)
                val end = Offset(size.width * 0.8f, size.height * 0.2f)

                drawLine(
                    color = Color(0xFF00B4D8), // Teal line
                    start = start,
                    end = mid,
                    strokeWidth = 12f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color.LightGray,
                    start = mid,
                    end = end,
                    strokeWidth = 12f,
                    cap = StrokeCap.Round
                )
            }

            // Pins and Vehicle
            Box(modifier = Modifier.fillMaxSize()) {
                // Start Pin
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = null,
                    tint = Color(0xFF2D6A4F),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 60.dp, bottom = 60.dp)
                        .size(36.dp)
                )

                // Vehicle
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 8.dp,
                    modifier = Modifier.align(Alignment.Center).size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.TwoWheeler,
                            contentDescription = "Motorbike",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // End Pin
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = null,
                    tint = Color(0xFFD00000),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 60.dp, top = 60.dp)
                        .size(36.dp)
                )
            }
        }

        // Tracking Bottom Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Drag handle
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (isTrackMyRequest) "Arriving in 15 mins" else "Delivery in Progress",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Order #8902 - Pharmacy Pickup",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                
                Spacer(modifier = Modifier.height(32.dp))

                // Progress Bar
                ProgressStepper()

                Spacer(modifier = Modifier.height(40.dp))

                // Role-Based Bottom Actions
                if (isTrackMyRequest) {
                    // Track My Request View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, contentDescription = "Helper", tint = MaterialTheme.colorScheme.primary)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text("Rahul S.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("Helper (4.9 ★)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            }
                        }
                        
                        OutlinedButton(
                            onClick = { /* Call */ },
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                } else {
                    // My Helping Tasks View
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Navigate */ },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Navigation, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Navigate")
                        }
                        
                        Button(
                            onClick = { /* Confirm Delivery */ },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Confirm Delivery", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(72.dp)) // padding for bottom nav
            }
        }
    }
}

@Composable
fun ProgressStepper() {
    val steps = listOf("Accepted", "Picked Up", "On Way", "Delivered")
    val currentStep = 1 // Picked Up is active

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        steps.forEachIndexed { index, step ->
            val isActive = index <= currentStep
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                // Circle Node
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(if (isActive) MaterialTheme.colorScheme.primary else Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (index < currentStep) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Label
                Text(
                    text = step,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray,
                    fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
