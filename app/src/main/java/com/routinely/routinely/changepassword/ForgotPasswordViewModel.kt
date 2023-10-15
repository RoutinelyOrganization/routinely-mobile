package com.routinely.routinely.changepassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.login.isValidEmailFormat
import com.routinely.routinely.util.validators.EmailInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    //forgotPasswordApi: ForgotPasswordRequest
) : ViewModel() {

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()

    var shouldGoToNextScreen = mutableStateOf(false)

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

    fun verifyAllConditions(email: String) {
        viewModelScope.launch {
            /* TODO something before */
            shouldGoToNextScreen.value = true
        }
    }
}