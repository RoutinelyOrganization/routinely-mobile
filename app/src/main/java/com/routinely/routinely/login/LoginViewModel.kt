package com.routinely.routinely.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.data.auth.model.SignInResult
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authApi: AuthApi,
    private val session: Session,
) : ViewModel() {
    private val _authenticated = MutableStateFlow(false)
    val authenticated = _authenticated.asStateFlow()

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()

    private val _signInResult = MutableStateFlow<SignInResult>(SignInResult.Empty)
    val signInResult = _signInResult.asStateFlow()

    fun passwordState(password: String) : PasswordInputValid {
        if (password.isBlank()) {
            return PasswordInputValid.Error(R.string.empty_field)
        }
        return if(isPasswordValid(password)){
            PasswordInputValid.Valid
        }else{
            PasswordInputValid.Error(R.string.login_invalid_password)
        }
    }
    fun emailState(email: String) : EmailInputValid {
        return when {
            email.isEmpty() -> {
                EmailInputValid.Error(R.string.empty_field)
            }
            isValidEmailFormat(email) -> {
                EmailInputValid.Valid
            }
            else -> {
                EmailInputValid.Error(R.string.invalid_email)
            }
        }
    }

    fun loginWithEmailAndPassword(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _signInResult.value = SignInResult.Loading
            try{
                _signInResult.value = authApi.loginUser(loginRequest)
                Log.d("LoginViewModel", "loginWithEmailAndPassword: signInResult ${signInResult.value}")
            } catch (e: Exception) {
                _signInResult.value = SignInResult.Error.stringMessage(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun saveUser(token: String, remember: Boolean) {
        viewModelScope.launch {
            session.setUserLoggedIn(true)
            session.setToken(token)
            if(remember) session.setRememberLogin()
        }
    }
}