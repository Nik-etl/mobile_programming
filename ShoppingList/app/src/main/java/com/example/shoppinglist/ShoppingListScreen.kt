package com.example.shoppinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun ShoppingScreen() {
    val database = rememberDatabase()
    val viewModel: ShoppingViewModel = viewModel(factory = ShoppingViewModelFactory(database))
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val itemList by viewModel.items.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Add Shopping Item:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = unit,
                onValueChange = { unit = it },
                label = { Text("Unit") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                scope.launch {
                    viewModel.addItem(
                        name = name,
                        quantity = quantity.toIntOrNull() ?: 0,
                        unit = unit,
                        price = price.toDoubleOrNull() ?: 0.0
                    )
                    name = ""; quantity = ""; unit = ""; price = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Shopping List:", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(8.dp))

        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        ) {
            Text(text = "Name", modifier = Modifier.weight(2f), color = Color.White)
            Text(text = "Qty", modifier = Modifier.weight(1f), color = Color.White)
            Text(text = "Unit", modifier = Modifier.weight(1f), color = Color.White)
            Text(text = "Price", modifier = Modifier.weight(1f), color = Color.White)
        }

        LazyColumn {
            items(itemList) { item ->
                ShoppingItemRow(item)
            }
        }
    }
}

@Composable
fun ShoppingItemRow(item: ShoppingItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = item.name, modifier = Modifier.weight(2f))
        Text(text = item.quantity.toString(), modifier = Modifier.weight(1f))
        Text(text = item.unit, modifier = Modifier.weight(1f))
        Text(text = "€${item.price}", modifier = Modifier.weight(1f))
    }
}