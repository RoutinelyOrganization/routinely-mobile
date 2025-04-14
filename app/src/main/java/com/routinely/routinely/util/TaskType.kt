package com.routinely.routinely.util

sealed class TaskType(val value: String) {
    data object Task : TaskType("task")
    data object Habit : TaskType("habit")
    data object Project : TaskType("project")

    companion object {
        fun fromString(value: String): TaskType {
            return when (value.lowercase()) {
                "task" -> Task
                "habit" -> Habit
                "project" -> Project
                else -> Task
            }
        }
    }
} 