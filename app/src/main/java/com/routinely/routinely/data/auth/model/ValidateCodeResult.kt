package com.routinely.routinely.data.auth.model

sealed class ValidateCodeResult {
    data class Success(val message: String) : ValidateCodeResult()
    data class Error(val message: Int): ValidateCodeResult()
    data object DefaultError: ValidateCodeResult()
    data object Loading: ValidateCodeResult()
    data object Empty: ValidateCodeResult()
}
