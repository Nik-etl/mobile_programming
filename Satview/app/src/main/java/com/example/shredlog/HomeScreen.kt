package com.example.shredlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import com.example.shredlog.model.Session

@Composable
fun AddSessionScreen(
    viewModel: SessionViewModel = viewModel(),
    onNavigateToList: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("ShredLog", style = MaterialTheme.typography.headlineMedium)
            Row {
                TextButton(onClick = onNavigateToList) {
                    Text("Sessions")
                }
                IconButton(onClick = onNavigateToSettings) {
                    Text("⚙️")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Activity selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = uiState.activity == "Surf",
                onClick = { viewModel.setActivity("Surf") },
                label = { Text("🏄 Surf") }
            )
            FilterChip(
                selected = uiState.activity == "Snowboard",
                onClick = { viewModel.setActivity("Snowboard") },
                label = { Text("🏂 Snowboard") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.location,
            onValueChange = { viewModel.updateField("location", it) },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.conditions,
            onValueChange = { viewModel.updateField("conditions", it) },
            label = { Text("Conditions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Rating
        Text("Rating: ${uiState.rating}/5")
        Slider(
            value = uiState.rating.toFloat(),
            onValueChange = { viewModel.setRating(it.toInt()) },
            valueRange = 1f..5f,
            steps = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = uiState.notes,
            onValueChange = { viewModel.updateField("notes", it) },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val session = Session(
                    date = LocalDate.now().toString(),
                    location = uiState.location,
                    activity = uiState.activity,
                    conditions = uiState.conditions,
                    rating = uiState.rating,
                    notes = uiState.notes
                )
                viewModel.addSession(session)
                viewModel.clearForm()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.location.isNotBlank()
        ) {
            Text("Save Session")
        }
    }
}