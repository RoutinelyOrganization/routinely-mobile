package com.routinely.routinely.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class Session(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val DATA = "Data"
        private const val IsLogin = "IsLogin"
        private const val TOKEN = "Token"
        private const val REMEMBER = "RememberLogin"
        val isLogin = booleanPreferencesKey(IsLogin)
        val token = stringPreferencesKey(TOKEN)
        val remember = booleanPreferencesKey(REMEMBER)
    }

    fun getToken(): Flow<String> {
        return dataStore.data.catch {
            emit(emptyPreferences())
        }.map { value: Preferences ->
            value[token] ?: ""
        }
    }

    suspend fun setToken(userToken: String) {
        dataStore.edit { preference ->
            preference[token] = userToken
        }
    }

    fun getRememberLogin(): Boolean {
        var response: Boolean
        runBlocking {
            val pref = dataStore.data.first()
            response = pref[remember] ?: false
        }
        return response
    }

    suspend fun setRememberLogin(shouldRemember: Boolean) {
        dataStore.edit { preference ->
            preference[remember] = shouldRemember
        }
    }
}