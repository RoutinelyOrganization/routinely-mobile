package com.routinely.routinely.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.CreateNewPasswordRequest
import com.routinely.routinely.data.auth.model.CreateNewPasswordResult
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNewPasswordViewModel(
    private val authApi: AuthApi,
) : ViewModel() {

    fun passwordState(password: String): PasswordInputValid {
        if (password.isBlank()) {
            return PasswordInputValid.Error(R.string.empty_field)
        }
        return if (isPasswordValid(password)) {
            PasswordInputValid.Valid
        } else {
            PasswordInputValid.Error(R.string.invalid_password)
        }
    }

    fun confirmPasswordState(password: String, confirmPassword: String): PasswordInputValid {
        if (confirmPassword.isBlank()) {
            return PasswordInputValid.Error(R.string.empty_field)
        }

        return if (confirmPassword != password) {
            PasswordInputValid.Error(R.string.passwords_must_be_identical)
        } else {
            PasswordInputValid.Valid
        }
    }

    private val _createNewPasswordResult =
        MutableStateFlow<CreateNewPasswordResult>(CreateNewPasswordResult.Empty)
    val createNewPasswordResult = _createNewPasswordResult.asStateFlow()
    fun createNewPassword(
        createNewPasswordRequest: CreateNewPasswordRequest,
        confirmPassword: String
    ) {
        viewModelScope.launch {
            _createNewPasswordResult.value = CreateNewPasswordResult.Loading
            if (confirmPasswordState(
                    createNewPasswordRequest.password,
                    confirmPassword
                ) == PasswordInputValid.Valid && passwordState(createNewPasswordRequest.password) == PasswordInputValid.Valid
            ) {
                try {
                    _createNewPasswordResult.value = authApi.createNewPassword(createNewPasswordRequest)
                } catch (e: Exception) {
                    _createNewPasswordResult.value = CreateNewPasswordResult.DefaultError
                }
            }
        }
    }
}