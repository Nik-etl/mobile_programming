package com.example.shredlog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SessionListScreen(
    viewModel: SessionViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val sessions by viewModel.allSessions.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onNavigateBack) {
                Text("← Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("My Sessions", style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (sessions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No sessions yet. Add your first one!")
            }
        } else {
            LazyColumn {
                items(sessions) { session ->
                    SessionCard(session = session)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun SessionCard(session: com.example.shredlog.model.Session) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${if (session.activity == "Surf") "🏄" else "🏂"} ${session.location}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("⭐ ${session.rating}/5")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(session.date, style = MaterialTheme.typography.bodySmall)
            Text(session.conditions, style = MaterialTheme.typography.bodyMedium)
            if (session.notes.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(session.notes, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}