package com.example.helplineapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.helplineapp.data.UserRole
import com.example.helplineapp.data.local.RequestEntity
import com.example.helplineapp.ui.MainViewModel
import com.example.helplineapp.ui.components.StylishButton
import com.example.helplineapp.ui.components.StylishCard
import com.example.helplineapp.ui.components.StylishTextField

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val currentRole by viewModel.currentRole.collectAsState()
    val availableRequests by viewModel.availableRequests.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        
        // Header Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(top = 48.dp, bottom = 24.dp, start = 24.dp, end = 24.dp)
        ) {
            Column {
                Text(
                    text = "HelpLine",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                RoleSwitcher(currentRole = currentRole, onRoleSwitch = { viewModel.switchRole(it) })
            }
        }

        Column(modifier = Modifier.padding(24.dp)) {
            when (currentRole) {
                UserRole.REQUESTER -> {
                    RequesterView(onSubmitRequest = { desc, loc, amount ->
                        viewModel.postRequest(desc, loc, amount)
                    })
                }
                UserRole.HELPER -> {
                    HelperView(availableRequests = availableRequests)
                }
            }
        }
    }
}

@Composable
fun RoleSwitcher(currentRole: UserRole, onRoleSwitch: (UserRole) -> Unit) {
    // Custom pill-shaped tab row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isRequester = currentRole == UserRole.REQUESTER
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(if (isRequester) MaterialTheme.colorScheme.surface else androidx.compose.ui.graphics.Color.Transparent)
                .clickable { onRoleSwitch(UserRole.REQUESTER) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Requester",
                color = if (isRequester) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(24.dp))
                .background(if (!isRequester) MaterialTheme.colorScheme.surface else androidx.compose.ui.graphics.Color.Transparent)
                .clickable { onRoleSwitch(UserRole.HELPER) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Helper",
                color = if (!isRequester) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RequesterView(onSubmitRequest: (String, String, Double) -> Unit) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var reward by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Post a Help Request", 
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        
        StylishTextField(
            value = title,
            onValueChange = { title = it },
            label = "What do you need help with?"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        StylishTextField(
            value = location,
            onValueChange = { location = it },
            label = "Location"
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        StylishTextField(
            value = reward,
            onValueChange = { reward = it },
            label = "Reward Amount ($)"
        )
        Spacer(modifier = Modifier.height(32.dp))
        
        StylishButton(
            text = "Post Request",
            onClick = {
                val amount = reward.toDoubleOrNull() ?: 0.0
                if (title.isNotBlank() && location.isNotBlank() && amount > 0) {
                    onSubmitRequest(title, location, amount)
                    title = ""
                    location = ""
                    reward = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun HelperView(availableRequests: List<RequestEntity>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Available Requests", 
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (availableRequests.isEmpty()) {
            Text(
                text = "No requests available right now.", 
                modifier = Modifier.padding(vertical = 32.dp),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(availableRequests) { request ->
                    RequestItemCard(request)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun RequestItemCard(request: RequestEntity) {
    StylishCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = request.description, 
                fontWeight = FontWeight.Bold, 
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Location",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = request.location, 
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Reward: $${request.rewardAmount}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}
