package com.routinely.routinely.data.auth.model

data class TaskRequest(
    val name: String,
    val date: String,
    val hour: String,
    val description: String,
    val accountId: String,
    val priority: String,
    val tag: String,
    val category: String
)
