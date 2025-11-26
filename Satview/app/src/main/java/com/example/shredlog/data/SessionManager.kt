package com.example.shredlog.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.shredlog.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "sessions")

class SessionManager(private val context: Context) {

    private val SESSIONS_KEY = stringPreferencesKey("sessions_list")

    val sessionsFlow: Flow<List<Session>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[SESSIONS_KEY] ?: "[]"
            try {
                Json.decodeFromString<List<Session>>(json)
            } catch (e: Exception) {
                emptyList()
            }
        }

    suspend fun addSession(session: Session) {
        context.dataStore.edit { preferences ->
            val currentJson = preferences[SESSIONS_KEY] ?: "[]"
            val sessions = try {
                Json.decodeFromString<List<Session>>(currentJson).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }
            sessions.add(0, session) // Add to beginning
            preferences[SESSIONS_KEY] = Json.encodeToString(sessions)
        }
    }

    suspend fun deleteSession(sessionId: Long) {
        context.dataStore.edit { preferences ->
            val currentJson = preferences[SESSIONS_KEY] ?: "[]"
            val sessions = try {
                Json.decodeFromString<List<Session>>(currentJson)
            } catch (e: Exception) {
                emptyList()
            }
            val filtered = sessions.filter { it.id != sessionId }
            preferences[SESSIONS_KEY] = Json.encodeToString(filtered)
        }
    }

    suspend fun getSessionById(id: Long): Session? {
        var result: Session? = null
        context.dataStore.data.map { preferences ->
            val json = preferences[SESSIONS_KEY] ?: "[]"
            val sessions = try {
                Json.decodeFromString<List<Session>>(json)
            } catch (e: Exception) {
                emptyList()
            }
            result = sessions.find { it.id == id }
        }
        return result
    }
}