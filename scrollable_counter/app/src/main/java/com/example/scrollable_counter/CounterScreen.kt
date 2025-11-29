package com.example.scrollable_counter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(viewModel: CounterViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Multi Counter") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.addCounter() }) {
                Icon(Icons.Default.Add, contentDescription = "Add counter")
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(
                items = viewModel.counters,
                key = { it.id }  // Critical for efficient recomposition
            ) { counter ->
                CounterItem(
                    counter = counter,
                    onIncrement = { viewModel.increment(counter.id) },
                    onDecrement = { viewModel.decrement(counter.id) },
                    onRemove = { viewModel.removeCounter(counter.id) }
                )
            }
        }
    }
}