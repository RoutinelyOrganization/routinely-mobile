package com.routinely.routinely.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.LoginRequest
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authApi: AuthApi,
) : ViewModel() {
    private val _authenticated = MutableStateFlow(false)
    val authenticated = _authenticated.asStateFlow()

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()

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
            val response = authApi.loginUser(loginRequest)
            if(response.serverStatusCode.value < 300) {
                _authenticated.value = true
            } else {
                _apiErrorMessage.value = response.message
            }
        }
    }
}