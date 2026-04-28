package com.example.helplineapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.helplineapp.data.local.AppDatabase
import com.example.helplineapp.repository.AppRepository
import com.example.helplineapp.ui.MainViewModel
import com.example.helplineapp.ui.screens.DashboardScreen
import com.example.helplineapp.ui.screens.LoginScreen
import com.example.helplineapp.ui.screens.RegisterScreen
import com.example.helplineapp.ui.theme.HelpLineAppTheme

enum class Screen {
    LOGIN,
    REGISTER,
    DASHBOARD
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Room DB, Repository, and ViewModel
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "helpline-db"
        ).build()
        val repository = AppRepository(db.appDao())
        val viewModel = MainViewModel(repository)

        enableEdgeToEdge()
        setContent {
            HelpLineAppTheme {
                var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.LOGIN -> {
                                LoginScreen(
                                    onLoginSuccess = { userId ->
                                        viewModel.loginSimulated(userId)
                                        currentScreen = Screen.DASHBOARD
                                    },
                                    onNavigateToRegister = {
                                        currentScreen = Screen.REGISTER
                                    }
                                )
                            }
                            Screen.REGISTER -> {
                                RegisterScreen(
                                    onRegisterSuccess = { userId ->
                                        viewModel.loginSimulated(userId)
                                        currentScreen = Screen.DASHBOARD
                                    },
                                    onNavigateToLogin = {
                                        currentScreen = Screen.LOGIN
                                    }
                                )
                            }
                            Screen.DASHBOARD -> {
                                DashboardScreen(viewModel = viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
