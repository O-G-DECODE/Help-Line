package com.example.helplineapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.helplineapp.ui.MainViewModel

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Helper : BottomNavScreen("helper", "Helper", Icons.Default.Handshake)
    object Requester : BottomNavScreen("requester", "Requester", Icons.Default.PanTool)
    object History : BottomNavScreen("history", "History", Icons.Default.History)
    object Track : BottomNavScreen("track", "Track", Icons.Default.LocationOn)
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    // Default to Requester as requested in the prompt
    var currentRoute by remember { mutableStateOf<BottomNavScreen>(BottomNavScreen.Requester) }

    val items = listOf(
        BottomNavScreen.Helper,
        BottomNavScreen.Requester,
        BottomNavScreen.History,
        BottomNavScreen.Track
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = {
                            Text(
                                text = screen.title,
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        selected = currentRoute == screen,
                        onClick = { currentRoute = screen },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary, // Teal
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), // Soft highlight
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (currentRoute) {
                BottomNavScreen.Helper -> {
                    HelperView(viewModel = viewModel)
                }
                BottomNavScreen.Requester -> {
                    RequesterView(onSubmitRequest = { title, desc, type, locFrom, locTo, amount, expiry ->
                        viewModel.postRequest(title, desc, type, locFrom, locTo, amount, expiry)
                    })
                }
                BottomNavScreen.History -> {
                    HistoryScreen()
                }
                BottomNavScreen.Track -> {
                    TrackScreen()
                }
            }
        }
    }
}
