package com.example.shredlog.model

import kotlinx.serialization.Serializable

@Serializable
data class Session(
    val id: Long = System.currentTimeMillis(),
    val date: String,
    val location: String,
    val activity: String,
    val conditions: String,
    val rating: Int,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
)