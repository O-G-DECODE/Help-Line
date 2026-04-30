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
import com.example.helplineapp.ui.MainViewModel
import com.example.helplineapp.ui.screens.LoginScreen
import com.example.helplineapp.ui.screens.RegisterScreen
import com.example.helplineapp.ui.screens.SplashScreen
import com.example.helplineapp.ui.theme.HelpLineAppTheme

enum class Screen {
    SPLASH,
    LOGIN,
    REGISTER,
    MAIN
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize pure UI ViewModel
        val viewModel = MainViewModel()

        enableEdgeToEdge()
        setContent {
            HelpLineAppTheme {
                var currentScreen by remember { mutableStateOf(Screen.SPLASH) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentScreen) {
                            Screen.SPLASH -> {
                                SplashScreen(onSplashComplete = {
                                    currentScreen = Screen.LOGIN
                                })
                            }
                            Screen.LOGIN -> {
                                LoginScreen(
                                    onLoginSuccess = { userId ->
                                        viewModel.loginSimulated(userId)
                                        currentScreen = Screen.MAIN
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
                                        currentScreen = Screen.MAIN
                                    },
                                    onNavigateToLogin = {
                                        currentScreen = Screen.LOGIN
                                    }
                                )
                            }
                            Screen.MAIN -> {
                                // For MAIN screen, we don't need the Box padding as MainAppScreen has its own Scaffold
                            }
                        }
                    }
                    
                    if (currentScreen == Screen.MAIN) {
                        // Place MainAppScreen outside the padded box to allow it to draw edge-to-edge correctly
                        com.example.helplineapp.ui.screens.MainAppScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}
