package com.routinely.routinely.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordRequest(
    val email: String,
)
