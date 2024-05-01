package com.routinely.routinely.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateNewPasswordRequest(
    val password: String,
    val code: String,
    val accountId: String,
)
