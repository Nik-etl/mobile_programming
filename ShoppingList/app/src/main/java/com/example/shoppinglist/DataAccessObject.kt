package com.example.shoppinglist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("SELECT * FROM ShoppingItem ORDER BY id DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>
}