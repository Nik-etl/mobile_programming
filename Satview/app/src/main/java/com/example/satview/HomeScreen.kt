package com.example.satview

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
    onSearch: (String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    //TODO add input handling lat/lon
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "SatView", style = MaterialTheme.typography.headlineMedium)
        Button(
            onClick = onNavigateToSettings,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("⚙️ Settings")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = uiState.query,
            onValueChange = { viewModel.updateQuery(it) },
            label = { Text("Enter coordinates") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = uiState.errorMessage != null,
            supportingText = {
                if (uiState.errorMessage != null) {
                    Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.searchSatelliteImage { imageUrl ->
                    onSearch(imageUrl)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            Text("Search")
        }
        if (uiState.isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        Text("You typed: ${uiState.query}")
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}