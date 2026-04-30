package com.example.helplineapp.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// Dummy Data Models for History
data class HistoryItem(
    val id: String,
    val title: String,
    val status: String, // "Completed", "Expired"
    val pickupLocation: String,
    val dropOffLocation: String,
    val rating: Int,
    val feedback: String,
    val date: String,
    val amount: Double
)

val dummyRequesterHistory = listOf(
    HistoryItem("1", "Grocery Run", "Completed", "Local Market", "Home", 5, "\"Fast and friendly delivery!\"", "Oct 24, 2:30 PM", 15.0),
    HistoryItem("2", "Pharmacy Pickup", "Expired", "CVS Pharmacy", "Home", 0, "", "Oct 20, 10:00 AM", 10.0),
    HistoryItem("3", "Hardware Store", "Completed", "Home Depot", "Worksite", 4, "\"Good, but took a little longer.\"", "Oct 15, 1:00 PM", 25.0)
)

val dummyHelperHistory = listOf(
    HistoryItem("4", "Document Delivery", "Completed", "City Hall", "Law Office", 5, "\"Lifesaver! Thanks so much.\"", "Oct 25, 4:00 PM", 20.0),
    HistoryItem("5", "Coffee Run", "Completed", "Starbucks", "Tech Park", 5, "\"Perfectly on time.\"", "Oct 22, 8:30 AM", 5.0)
)

@Composable
fun HistoryScreen() {
    var isMyRequests by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Top App Bar with Toggle
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ToggleOption(
                    text = "My Requests",
                    isSelected = isMyRequests,
                    onClick = { isMyRequests = true }
                )
                ToggleOption(
                    text = "My Helps",
                    isSelected = !isMyRequests,
                    onClick = { isMyRequests = false }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        val currentList = if (isMyRequests) dummyRequesterHistory else dummyHelperHistory

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(currentList) { item ->
                HistoryCard(item = item, isHelperView = !isMyRequests)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ToggleOption(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun HistoryCard(item: HistoryItem, isHelperView: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Title & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                val statusColor = if (item.status == "Completed") Color(0xFF2D6A4F) else Color(0xFFD00000)
                Surface(
                    color = statusColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = item.status,
                        color = statusColor,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Route Section
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Place, contentDescription = "Pickup", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    Canvas(modifier = Modifier.width(2.dp).height(24.dp).padding(vertical = 4.dp)) {
                        drawLine(
                            color = Color.LightGray,
                            start = androidx.compose.ui.geometry.Offset(size.width/2, 0f),
                            end = androidx.compose.ui.geometry.Offset(size.width/2, size.height),
                            strokeWidth = 2f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                        )
                    }
                    Icon(Icons.Default.Flag, contentDescription = "Drop-off", tint = Color(0xFFD00000), modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Pickup: ${item.pickupLocation}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Drop-off: ${item.dropOffLocation}", style = MaterialTheme.typography.bodySmall, color = Color.DarkGray)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Rating & Feedback
            if (item.status == "Completed") {
                Column {
                    RatingStars(rating = item.rating)
                    if (item.feedback.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.feedback,
                            style = MaterialTheme.typography.bodySmall,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            Divider(color = Color.LightGray.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))
            
            // Bottom Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                
                val amountText = if (isHelperView) "+$${item.amount.toInt()}" else "-$${item.amount.toInt()}"
                val amountColor = if (isHelperView) Color(0xFF2D6A4F) else MaterialTheme.colorScheme.onSurface
                
                Text(
                    text = amountText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = amountColor
                )
            }
        }
    }
}

@Composable
fun RatingStars(rating: Int) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
