package com.example.weatherapp

// Weather API response models
data class WeatherResponse(
    val name: String,
    val main: MainData
)

data class MainData(
    val temp: Double,
    val humidity: Int
)