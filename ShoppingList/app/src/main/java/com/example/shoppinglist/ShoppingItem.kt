package com.example.shoppinglist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quantity: Int,
    val unit: String,
    val price: Double
)