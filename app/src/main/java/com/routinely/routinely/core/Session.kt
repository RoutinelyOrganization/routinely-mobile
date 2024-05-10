package com.routinely.routinely.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class Session(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DATA = "Data"
        private const val TOKEN = "Token"
        private const val REFRESH_TOKEN = "RefreshToken"
        val token = stringPreferencesKey(TOKEN)
        val refresh_token = stringPreferencesKey(REFRESH_TOKEN)
    }

    fun getToken(): String {
        var response: String
        runBlocking {
            val pref = dataStore.data.first()
            response = pref[token] ?: ""
        }
        return response
    }

    suspend fun setToken(userToken: String) {
        dataStore.edit { preference ->
            preference[token] = userToken
        }
    }

    fun getRefreshToken(): String {
        var response: String
        runBlocking {
            val pref = dataStore.data.first()
            response = pref[refresh_token] ?: ""
        }
        return response
    }

    suspend fun setRefreshToken(refreshToken: String) {
        dataStore.edit { preference ->
            preference[refresh_token] = refreshToken
        }
    }

    fun clearData() {
        runBlocking {
            dataStore.edit { preferences ->
                preferences.clear()
            }
        }
    }

    fun saveTokens(tokens: Pair<String, String>) {
        runBlocking {
            dataStore.edit { preferences ->
                preferences[token] = tokens.first
                preferences[refresh_token] = tokens.second
            }
        }
    }
}