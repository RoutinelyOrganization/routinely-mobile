package com.routinely.routinely.changepassword

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.util.validators.CodeInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationCodeViewModel(
    //verificationCodeApi: VerificationCodeRequest
) : ViewModel() {

    private val _apiErrorMessage = MutableStateFlow(listOf<String>())
    val apiErrorMessage = _apiErrorMessage.asStateFlow()

    var shouldGoToNextScreen = mutableStateOf(false)

    private val pattern = Regex("^[0-9]*\$")
    fun codeState(code: String) : CodeInputValid {
        return when {
            code.isEmpty() -> {
                CodeInputValid.Error(R.string.empty_field)
            }
            code.length == 6 && code.matches(pattern) -> {
                CodeInputValid.Valid
            }
            else -> {
                CodeInputValid.Error(R.string.invalid_email)
            }
        }
    }

    fun verifyAllConditions(code: String) :CodeInputValid {
        viewModelScope.launch {
            if(codeState(code) == CodeInputValid.Valid){
                shouldGoToNextScreen.value = true
            }
        }
        return codeState(code)
    }
}