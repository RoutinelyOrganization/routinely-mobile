package com.routinely.routinely.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.ForgotPasswordRequest
import com.routinely.routinely.data.auth.model.ForgotPasswordResult
import com.routinely.routinely.login.isValidEmailFormat
import com.routinely.routinely.util.validators.EmailInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authApi: AuthApi,
) : ViewModel() {

    private val _forgotPasswordResult = MutableStateFlow<ForgotPasswordResult>(ForgotPasswordResult.Empty)
    val forgotPasswordResult = _forgotPasswordResult.asStateFlow()

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

    fun sendEmail(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            _forgotPasswordResult.value = ForgotPasswordResult.Loading
            try{
                _forgotPasswordResult.value = authApi.forgotPassword(forgotPasswordRequest)
            } catch (e: Exception) {
                _forgotPasswordResult.value = ForgotPasswordResult.DefaultError
            }
        }
    }
}