package com.routinely.routinely.data.auth.model

sealed class ForgotPasswordResult {
    data class Success(val accountId: String) : ForgotPasswordResult()
    data class Error(val message: Int): ForgotPasswordResult()
    data object DefaultError: ForgotPasswordResult()
    data object Loading: ForgotPasswordResult()
    data object Empty: ForgotPasswordResult()
}
