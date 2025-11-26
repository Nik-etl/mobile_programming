package com.example.shredlog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shredlog.ui.theme.SatviewTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferencesManager = remember { PreferencesManager(this) }
            val darkMode by preferencesManager.darkModeFlow.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()

            SatviewTheme(darkTheme = darkMode) {
                val navController = rememberNavController()
                val sessionViewModel: SessionViewModel = viewModel()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            AddSessionScreen(
                                viewModel = sessionViewModel,
                                onNavigateToList = {
                                    navController.navigate("sessions")
                                },
                                onNavigateToSettings = {
                                    navController.navigate("settings")
                                }
                            )
                        }

                        composable("sessions") {
                            SessionListScreen(
                                viewModel = sessionViewModel,
                                onNavigateBack = {
                                    navController.navigateUp()
                                }
                            )
                        }

                        composable("settings") {
                            SettingsScreen(
                                isDarkMode = darkMode,
                                onDarkModeChange = { enabled ->
                                    coroutineScope.launch {
                                        preferencesManager.setDarkMode(enabled)
                                    }
                                },
                                onBack = { navController.navigateUp() }
                            )
                        }
                    }
                }
            }
        }
    }
}