package com.routinely.routinely.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.CreateAccountResult
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.NameInputValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateAccountViewModel(
    private val authApi: AuthApi,
) : ViewModel() {

    private val _createAccountResult = MutableStateFlow<CreateAccountResult>(CreateAccountResult.Empty)
    val createAccountResult = _createAccountResult.asStateFlow()

    fun passwordState(password: String) : PasswordInputValid {
        if (password.isBlank()) {
            return PasswordInputValid.Error(R.string.empty_field)
        }
        return if(isPasswordValid(password)){
            PasswordInputValid.Valid
        }else{
            PasswordInputValid.Error(R.string.invalid_password)
        }
    }

    fun confirmPasswordState(password : String, confirmPassword: String) : PasswordInputValid {
        if(confirmPassword.isBlank()) {
            return PasswordInputValid.Error(R.string.empty_field)
        }

        return if(confirmPassword != password) {
            PasswordInputValid.Error(R.string.passwords_must_be_identical)
        } else {
            PasswordInputValid.Valid
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

    fun nameState(name: String) : NameInputValid {
        return when {
            name.isEmpty() -> {
                NameInputValid.Error(R.string.empty_field)
            }
            !name.matches(Regex("^[A-Z][a-zA-ZÀ-ÖØ-öø-ÿ ]+\$")) -> {
                NameInputValid.Error(R.string.invalid_name)
            }
            name.count { it.isLetter() } < 3 -> {
                NameInputValid.Error(R.string.at_least_three_letters)
            }
            name.contains("  ") -> {
                NameInputValid.Error(R.string.invalid_name)
            }
            else -> {
                NameInputValid.Valid
            }
        }
    }

    fun createAccount(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            _createAccountResult.value = CreateAccountResult.Loading
            try{
                _createAccountResult.value = authApi.registerUser(registerRequest)
            } catch (e: Exception) {
                _createAccountResult.value = CreateAccountResult.Error(R.string.api_unexpected_error)
            }
        }
    }
}