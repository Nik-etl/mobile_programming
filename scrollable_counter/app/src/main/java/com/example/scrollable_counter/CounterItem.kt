package com.example.scrollable_counter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CounterItem(
    counter: Counter,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Counter name
            Text(
                text = counter.name,
                style = MaterialTheme.typography.titleMedium
            )

            // Controls: decrement, value, increment
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onDecrement) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrement")
                }

                Text(
                    text = counter.value.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.widthIn(min = 40.dp),
                )

                IconButton(onClick = onIncrement) {
                    Icon(Icons.Default.Add, contentDescription = "Increment")
                }

                // Delete button
                IconButton(onClick = onRemove) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Remove counter",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}