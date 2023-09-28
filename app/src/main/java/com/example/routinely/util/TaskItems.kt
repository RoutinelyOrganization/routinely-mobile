package com.example.routinely.util

data class TaskItems(
    val nameOfTask: String,
    val category: Categories,
    val taskPriority: TaskPriority,
    val listOfActions: List<ActionItem>?,
)