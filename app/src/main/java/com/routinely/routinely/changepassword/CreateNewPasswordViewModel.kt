package com.routinely.routinely.changepassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateNewPasswordViewModel(
    //private val newPasswordApi: SetNewPasswordRequest,
) : ViewModel() {

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()

    var shouldGoToNextScreen = mutableStateOf(false)

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

    fun verifyAllConditions(password: String, confirmPassword: String) : PasswordInputValid {
        viewModelScope.launch {
            if(confirmPasswordState(password, confirmPassword) == PasswordInputValid.Valid){
                shouldGoToNextScreen.value = true
            }
        }
        return confirmPasswordState(password, confirmPassword)
    }
}