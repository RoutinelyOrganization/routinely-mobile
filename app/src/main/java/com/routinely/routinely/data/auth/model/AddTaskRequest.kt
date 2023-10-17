package com.routinely.routinely.data.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class AddTaskRequest(
    val name: String,
    val date: String,
    val hour: String,
    val description: String,
    val accountId: String,
    val priority: String,
    val tag: String,
)