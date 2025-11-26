package com.example.shredlog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shredlog.data.SessionManager
import com.example.shredlog.model.Session
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application)

    val allSessions = sessionManager.sessionsFlow

    private val _uiState = MutableStateFlow(SessionUiState())
    val uiState: StateFlow<SessionUiState> = _uiState.asStateFlow()

    fun addSession(session: Session) {
        viewModelScope.launch {
            sessionManager.addSession(session)
        }
    }

    fun deleteSession(sessionId: Long) {
        viewModelScope.launch {
            sessionManager.deleteSession(sessionId)
        }
    }

    suspend fun getSessionById(id: Long): Session? {
        return sessionManager.getSessionById(id)
    }

    fun updateField(field: String, value: String) {
        _uiState.value = when(field) {
            "location" -> _uiState.value.copy(location = value)
            "conditions" -> _uiState.value.copy(conditions = value)
            "notes" -> _uiState.value.copy(notes = value)
            else -> _uiState.value
        }
    }

    fun setActivity(activity: String) {
        _uiState.value = _uiState.value.copy(activity = activity)
    }

    fun setRating(rating: Int) {
        _uiState.value = _uiState.value.copy(rating = rating)
    }

    fun clearForm() {
        _uiState.value = SessionUiState()
    }
}

data class SessionUiState(
    val location: String = "",
    val activity: String = "Surf",
    val conditions: String = "",
    val rating: Int = 3,
    val notes: String = ""
)