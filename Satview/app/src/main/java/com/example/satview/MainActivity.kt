package com.example.satview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.satview.ui.theme.SatviewTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val preferencesManager = remember { PreferencesManager(this) }
            val darkMode by preferencesManager.darkModeFlow.collectAsState(initial = false)
            val coroutineScope = rememberCoroutineScope()
            SatviewTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") {
                            val searchViewModel: SearchViewModel = viewModel()
                            HomeScreen(
                                viewModel = searchViewModel,
                                onSearch = { imageUrl ->
                                    navController.navigate("results/$imageUrl")
                                },
                                onNavigateToSettings = {
                                    navController.navigate("settings")
                                }
                            )
                        }

                        composable("results/{imageUrl}") { backStackEntry ->
                            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
                            ResultsScreen(
                                imageUrl = imageUrl,
                                onBack = { navController.navigateUp() }
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SatviewTheme {
        Greeting("Android")
    }
}