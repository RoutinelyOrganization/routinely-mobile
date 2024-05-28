package com.routinely.routinely.data.auth.model

data class LoginRequest(
    val email: String,
    val password: String,
    val remember: Boolean = false
)
