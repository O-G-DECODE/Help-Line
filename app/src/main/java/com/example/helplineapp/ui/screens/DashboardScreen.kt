package com.example.helplineapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalMall
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Title
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.helplineapp.ui.MainViewModel
import com.example.helplineapp.ui.models.Request
import com.example.helplineapp.ui.models.RequestStatus
import com.example.helplineapp.ui.models.ServiceType
import com.example.helplineapp.ui.components.StylishButton
import com.example.helplineapp.ui.components.StylishTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequesterView(onSubmitRequest: (String, String, ServiceType, String, String, Double, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf(ServiceType.BUY_AND_DELIVER) }
    var pickupLocation by remember { mutableStateOf("") }
    var dropOffLocation by remember { mutableStateOf("") }
    var reward by remember { mutableStateOf("") }
    var expiryTime by remember { mutableStateOf("1 Hour") }
    var expandedExpiry by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New Request",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Task Input
        StylishTextField(
            value = title,
            onValueChange = { title = it },
            label = "Task Title",
            leadingIcon = {
                Icon(
                    Icons.Default.Title,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Describe the Help Needed") },
            leadingIcon = {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().height(120.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = MaterialTheme.colorScheme.surface
            )
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Service Type
        Text(
            text = "Service Type",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { serviceType = ServiceType.BUY_AND_DELIVER },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    if (serviceType == ServiceType.BUY_AND_DELIVER) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (serviceType == ServiceType.BUY_AND_DELIVER) MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.1f
                    ) else Color.Transparent
                )
            ) {
                Icon(
                    Icons.Default.LocalMall,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Buy & Deliver", color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                onClick = { serviceType = ServiceType.PICK_AND_DROP },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(
                    1.dp,
                    if (serviceType == ServiceType.PICK_AND_DROP) MaterialTheme.colorScheme.primary else Color.LightGray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (serviceType == ServiceType.PICK_AND_DROP) MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.1f
                    ) else Color.Transparent
                )
            ) {
                Icon(
                    Icons.Default.LocalShipping,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pick & Drop", color = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Location Fields
        StylishTextField(
            value = pickupLocation,
            onValueChange = { pickupLocation = it },
            label = "From (Pickup Location)",
            leadingIcon = {
                Icon(
                    Icons.Default.Place,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        StylishTextField(
            value = dropOffLocation,
            onValueChange = { dropOffLocation = it },
            label = "To (Drop-off Location)",
            leadingIcon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Reward & Expiry
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StylishTextField(
                value = reward,
                onValueChange = { reward = it },
                label = "Reward ($)",
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    Icon(
                        Icons.Default.AttachMoney,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )

            ExposedDropdownMenuBox(
                expanded = expandedExpiry,
                onExpandedChange = { expandedExpiry = it },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = expiryTime,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Expires In") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedExpiry) },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedExpiry,
                    onDismissRequest = { expandedExpiry = false }
                ) {
                    listOf("1 Hour", "3 Hours", "End of Day").forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                expiryTime = selectionOption
                                expandedExpiry = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        StylishButton(
            text = "Post Request",
            onClick = {
                val amount = reward.toDoubleOrNull() ?: 0.0
                if (title.isNotBlank() && pickupLocation.isNotBlank() && dropOffLocation.isNotBlank() && amount > 0) {
                    onSubmitRequest(
                        title,
                        description,
                        serviceType,
                        pickupLocation,
                        dropOffLocation,
                        amount,
                        expiryTime
                    )
                    // Reset fields
                    title = ""
                    description = ""
                    pickupLocation = ""
                    dropOffLocation = ""
                    reward = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp)) // bottom padding for nav bar
    }
}


@Composable
fun HelperView(viewModel: MainViewModel? = null) {
    var startLocation by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var commuteType by remember { mutableStateOf("One-Way") }
    var detourFlex by remember { mutableStateOf(2.5f) } // 0f to 5f
    var vehicleType by remember { mutableStateOf("Car") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Set Your Path",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = MaterialTheme.colorScheme.primary)
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = "Profile", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Route Setup with Dashed Line
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Icons Column
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Icon(Icons.Default.Place, contentDescription = "Start", tint = Color(0xFF2D6A4F)) // Green pin
                
                // Dashed Line
                val lineColor = MaterialTheme.colorScheme.primary
                Canvas(modifier = Modifier.width(2.dp).height(50.dp).padding(vertical = 4.dp)) {
                    drawLine(
                        color = lineColor,
                        start = androidx.compose.ui.geometry.Offset(size.width/2, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width/2, size.height),
                        strokeWidth = 4f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
                
                Icon(Icons.Default.Flag, contentDescription = "Destination", tint = Color(0xFFD00000)) // Red flag
            }
            
            // Input Fields Column
            Column(modifier = Modifier.weight(1f)) {
                StylishTextField(
                    value = startLocation,
                    onValueChange = { startLocation = it },
                    label = "Your current place/Home..."
                )
                Spacer(modifier = Modifier.height(16.dp))
                StylishTextField(
                    value = destination,
                    onValueChange = { destination = it },
                    label = "Heading to Town/Work..."
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Commute Type
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { commuteType = "One-Way" },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (commuteType == "One-Way") MaterialTheme.colorScheme.primary else Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (commuteType == "One-Way") MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                )
            ) {
                Text("One-Way", color = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(
                onClick = { commuteType = "Daily Commute" },
                modifier = Modifier.weight(1f).height(48.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, if (commuteType == "Daily Commute") MaterialTheme.colorScheme.primary else Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (commuteType == "Daily Commute") MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent
                )
            ) {
                Text("Daily Commute", color = MaterialTheme.colorScheme.primary)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Detour Flex
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Detour Flex", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = String.format("%.1f km", detourFlex), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        Slider(
            value = detourFlex,
            onValueChange = { detourFlex = it },
            valueRange = 0f..5f,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("0m", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            Text("5km", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        
        Spacer(modifier = Modifier.height(32.dp))

        // Vehicle Type
        Text(text = "Vehicle Type", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            VehicleChip("Walking", Icons.Default.DirectionsWalk, vehicleType) { vehicleType = it }
            VehicleChip("Bike", Icons.Default.DirectionsBike, vehicleType) { vehicleType = it }
            VehicleChip("Motorbike", Icons.Default.TwoWheeler, vehicleType) { vehicleType = it }
            VehicleChip("Car", Icons.Default.DirectionsCar, vehicleType) { vehicleType = it }
        }

        Spacer(modifier = Modifier.height(48.dp))
        
        StylishButton(
            text = "Find Matches on My Route",
            onClick = { /* Find Matches logic */ },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp)) // bottom padding for nav bar
    }
}

@Composable
fun VehicleChip(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: String, onSelect: (String) -> Unit) {
    val isSelected = label == selected
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface)
            .clickable { onSelect(label) },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon, 
                contentDescription = label, 
                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label, 
                style = MaterialTheme.typography.labelSmall, 
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
