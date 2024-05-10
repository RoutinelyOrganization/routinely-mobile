package com.routinely.routinely.data.auth.model

sealed class CreateNewPasswordResult {
    data class Success(val message: String) : CreateNewPasswordResult()
    data class Error(val message: Int): CreateNewPasswordResult()
    data object DefaultError: CreateNewPasswordResult()
    data object Loading: CreateNewPasswordResult()
    data object Empty: CreateNewPasswordResult()
}
