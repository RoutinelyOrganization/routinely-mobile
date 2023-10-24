package com.routinely.routinely.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
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