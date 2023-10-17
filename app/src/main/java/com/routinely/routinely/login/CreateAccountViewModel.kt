package com.routinely.routinely.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.NameInputValid
import com.routinely.routinely.util.validators.PasswordInputValid

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountViewModel(
    private val authApi: AuthApi,
) : ViewModel() {

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()
    private suspend fun createNewAccount(createAccount: RegisterRequest): ApiResponse {
        return withContext(Dispatchers.IO) {
            val response: ApiResponse

            val registerRequest = RegisterRequest(
                name = createAccount.name,
                email = createAccount.email,
                password = createAccount.password,
                acceptedTerms = createAccount.acceptedTerms
            )
            response = authApi.registerUser(registerRequest)
            return@withContext response
        }
    }

    var shouldGoToNextScreen = mutableStateOf(false)
        private set

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

    fun verifyAllConditions(createAccount: RegisterRequest) {
        viewModelScope.launch {
            val response = createNewAccount(createAccount)
            if(response.serverStatusCode.value < 300) {
                shouldGoToNextScreen.value = true
            } else {
                _apiErrorMessage.value = response.message
            }
        }
    }
}