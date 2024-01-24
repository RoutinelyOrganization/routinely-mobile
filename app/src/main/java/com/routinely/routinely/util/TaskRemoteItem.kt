package com.routinely.routinely.util

data class TaskRemoteItem(
    val id: Int,
    val name: String,
    val date: String,
    val hour: String,
    val tag: String,
    val priority: String,
    val category: String,
    val description: String
)
