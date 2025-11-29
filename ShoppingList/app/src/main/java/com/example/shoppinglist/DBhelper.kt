package com.example.shoppinglist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.room.Room

@Composable
fun rememberDatabase(): ShoppingDatabase {
    val context = LocalContext.current
    return remember {
        Room.databaseBuilder(
            context.applicationContext,
            ShoppingDatabase::class.java,
            "shopping-database"
        ).build()
    }
}