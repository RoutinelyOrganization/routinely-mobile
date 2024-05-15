package com.routinely.routinely.authentication

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.SignInResult
import kotlinx.coroutines.runBlocking


class AuthenticationData(
    context: Context,
    private val authApi: AuthApi
) {
    companion object {
        private const val PREFS_NAME = "authentication_data"
        private const val EMAIL = "authentication_email"
        private const val PASSWORD = "authentication_password"
    }

    private var masterKey: MasterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    private fun getEmail(): String? {
        return sharedPreferences.getString(EMAIL, null)
    }

    private fun getPassword(): String? {
       return sharedPreferences.getString(PASSWORD, null)
    }

    fun isUserAlreadyLoggedIn(): Boolean {
        return sharedPreferences.contains(EMAIL) && sharedPreferences.contains(PASSWORD)
    }

    fun saveUserCredentials(email: String, password: String) {
        editor.putString(EMAIL, email)
        editor.putString(PASSWORD, password)
        editor.apply()
    }

    /**
     * Try to login user using email and password.
     * If it fails will return null.
     * @return Pair<token, refreshToken>
     */
    fun loginUserAndReturnToken(): Pair<String, String>? = runBlocking {
        val email = getEmail()
        val password = getPassword()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()) {
            return@runBlocking null
        }

        val signInResult = authApi.loginUser(LoginRequest(
            email = getEmail()!!,
            password = getPassword()!!,
            remember = true)
        )

        when(signInResult) {
            is SignInResult.Success -> {
                return@runBlocking Pair(signInResult.token, signInResult.refreshToken)
            }
            else -> {
                return@runBlocking null
            }
        }
    }

    fun clearUserCredentials() {
        editor.clear()
        editor.apply()
    }
}