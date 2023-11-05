package com.routinely.routinely.data.auth.model

sealed class CreateAccountResult {
    data object Success : CreateAccountResult()
    data class Error(val message: Int): CreateAccountResult()
    data object Loading: CreateAccountResult()
    data object Empty: CreateAccountResult()
}