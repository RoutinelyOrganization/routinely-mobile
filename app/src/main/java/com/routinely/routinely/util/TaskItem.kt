package com.routinely.routinely.util

data class TaskItem(
    val id: Int,
    val name: String,
    val date: String,
    val hour: String,
    val tag: TaskTag,
    val priority: TaskPriorities,
    val category: TaskCategory,
    val description: String
)

data class TaskItemRemote(
    val id: Int,
    val name: String,
    val date: String,
    val hour: String,
    val tag: String,
    val priority: String,
    val category: String,
    val description: String
)