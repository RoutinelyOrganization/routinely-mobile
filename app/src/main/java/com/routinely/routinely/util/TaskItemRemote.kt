package com.routinely.routinely.util

import kotlinx.serialization.Serializable

@Serializable
data class TaskListResponse(
    val count: Int,
    val tasks: List<TaskItemRemote>
)

@Serializable
data class TaskItemRemote(
    val id: Int,
    val name: String,
    val description: String,
    val date: String,
    val category: String,
    val checked: Boolean,
    val finallyDate: String,
    val weekDays: List<String>,
    val type: String,
) 