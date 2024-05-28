package com.routinely.routinely.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateCodeRequest(
    val code: String,
    val accountId: String,
)
