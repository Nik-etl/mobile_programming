package com.example.shoppinglist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

class ShoppingViewModel(private val database: ShoppingDatabase) : ViewModel() {

    private val dao = database.shoppingDao()
    val items: Flow<List<ShoppingItem>> = dao.getAllItems()

    suspend fun addItem(name: String, quantity: Int, unit: String, price: Double) {
        dao.insertItem(ShoppingItem(name = name, quantity = quantity, unit = unit, price = price))
    }

    suspend fun deleteItem(item: ShoppingItem) {
        dao.deleteItem(item)
    }

    suspend fun updateItem(item: ShoppingItem) {
        dao.updateItem(item)
    }
}