package com.example.helplineapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.helplineapp.data.UserRole
import com.example.helplineapp.data.local.RequestEntity
import com.example.helplineapp.ui.MainViewModel

@Composable
fun DashboardScreen(viewModel: MainViewModel) {
    val currentRole by viewModel.currentRole.collectAsState()
    val availableRequests by viewModel.availableRequests.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        
        // Role Switcher
        RoleSwitcher(currentRole = currentRole, onRoleSwitch = { viewModel.switchRole(it) })
        
        Spacer(modifier = Modifier.height(16.dp))

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

@Composable
fun RoleSwitcher(currentRole: UserRole, onRoleSwitch: (UserRole) -> Unit) {
    TabRow(selectedTabIndex = currentRole.ordinal) {
        Tab(
            selected = currentRole == UserRole.REQUESTER,
            onClick = { onRoleSwitch(UserRole.REQUESTER) },
            text = { Text("REQUESTER") }
        )
        Tab(
            selected = currentRole == UserRole.HELPER,
            onClick = { onRoleSwitch(UserRole.HELPER) },
            text = { Text("HELPER") }
        )
    }
}

@Composable
fun RequesterView(onSubmitRequest: (String, String, Double) -> Unit) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var reward by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Post a Help Request", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title / Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        OutlinedTextField(
            value = reward,
            onValueChange = { reward = it },
            label = { Text("Reward Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = {
                val amount = reward.toDoubleOrNull() ?: 0.0
                if (title.isNotBlank() && location.isNotBlank() && amount > 0) {
                    onSubmitRequest(title, location, amount)
                    title = ""
                    location = ""
                    reward = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Post Request")
        }
    }
}

@Composable
fun HelperView(availableRequests: List<RequestEntity>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Available Requests", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (availableRequests.isEmpty()) {
            Text("No requests available right now.", modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(availableRequests) { request ->
                    RequestItemCard(request)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun RequestItemCard(request: RequestEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = request.description, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Location: ${request.location}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Reward: $${request.rewardAmount}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
