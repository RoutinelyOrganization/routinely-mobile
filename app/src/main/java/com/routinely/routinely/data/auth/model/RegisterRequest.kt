package com.routinely.routinely.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val acceptedTerms: Boolean,
)
