package com.example.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ShoppingViewModelFactory(private val database: ShoppingDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShoppingViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

