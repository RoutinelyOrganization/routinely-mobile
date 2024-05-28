package com.routinely.routinely.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.api.AuthApi
import com.routinely.routinely.data.auth.model.ValidateCodeRequest
import com.routinely.routinely.data.auth.model.ValidateCodeResult
import com.routinely.routinely.util.validators.CodeInputValid
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VerificationCodeViewModel(
    private val authApi: AuthApi,
) : ViewModel() {

    private val _validateCodeResult = MutableStateFlow<ValidateCodeResult>(ValidateCodeResult.Empty)
    val validateCodeResult = _validateCodeResult.asStateFlow()

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

    fun codeVerification(validateCodeRequest: ValidateCodeRequest) {
        viewModelScope.launch {
            _validateCodeResult.value = ValidateCodeResult.Loading
            try{
                _validateCodeResult.value = authApi.validateCode(validateCodeRequest)
            } catch (e: Exception) {
                _validateCodeResult.value = ValidateCodeResult.DefaultError
            }
        }
    }
}