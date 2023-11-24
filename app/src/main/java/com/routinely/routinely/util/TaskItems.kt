package com.routinely.routinely.util

data class TaskItems(
    val nameOfTask: String,
    val category: TaskCategory,
    val taskPriorities: TaskPriorities,
    val listOfActions: List<ActionItem>?,
)