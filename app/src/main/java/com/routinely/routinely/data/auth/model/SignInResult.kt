package com.routinely.routinely.data.auth.model

sealed class SignInResult {
    data class Success(val token: String, val refreshToken: String): SignInResult()
    data class Error(val message: Int): SignInResult()
    data object DefaultError: SignInResult()
    data object Loading: SignInResult()
    data object Empty: SignInResult()
}