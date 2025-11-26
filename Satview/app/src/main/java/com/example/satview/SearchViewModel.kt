package com.example.satview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val imageUrl: String? = null
)

class SearchViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun updateQuery(newQuery: String) {
        _uiState.value = _uiState.value.copy(
            query = newQuery,
            errorMessage = null
        )
    }

    fun searchSatelliteImage(onSuccess: (String) -> Unit) {
        if (_uiState.value.query.isBlank()) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Please enter coordinates"
            )
            return
        }

        // Parse coordinates (expect "lat, lon" format)
        val parts = _uiState.value.query.split(",")
        if (parts.size != 2) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Use format: latitude, longitude"
            )
            return
        }

        val lat = parts[0].trim().toDoubleOrNull()
        val lon = parts[1].trim().toDoubleOrNull()

        if (lat == null || lon == null) {
            _uiState.value = _uiState.value.copy(
                errorMessage = "Invalid coordinates"
            )
            return
        }

        // Make API call
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getEarthImagery(lon, lat)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    imageUrl = response.url
                )
                onSuccess(response.url)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to fetch image: ${e.message}"
                )
            }
        }
    }
}