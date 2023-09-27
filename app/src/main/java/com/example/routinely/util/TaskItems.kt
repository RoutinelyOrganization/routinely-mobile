package com.example.routinely.util

data class TaskItems(
    val nameOfTask: String,
    val categoryName: String,
    val taskPriority: TaskPriority,
    val listOfActions: List<ActionItem>?,
)