package com.example.weatherapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val apiKey = "fbc8450cc544c6714911b2315ff852e2"

    private val _weatherList = mutableStateListOf<WeatherResponse>()
    val weatherList: List<WeatherResponse> get() = _weatherList

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(
                    city = city,
                    apiKey = apiKey
                )
                _weatherList.add(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}