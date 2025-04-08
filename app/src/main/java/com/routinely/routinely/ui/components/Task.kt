package com.routinely.routinely.ui.components

import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.TaskType
import java.time.LocalDate

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val category: TaskCategory,
    val isSelected: Boolean,
    val date: LocalDate,
    val type: TaskType
) {
    companion object {
        private fun create(
            id: Int,
            title: String,
            description: String,
            category: TaskCategory,
            date: LocalDate,
            type: TaskType = TaskType.Task,
            isSelected: Boolean = false
        ): Task {
            return Task(
                id = id,
                title = title,
                description = description,
                category = category,
                date = date,
                type = type,
                isSelected = isSelected
            )
        }

        fun fromApi(
            id: Int,
            title: String,
            description: String,
            category: String,
            date: LocalDate,
            type: String,
            isSelected: Boolean = false
        ): Task {
            return create(
                id = id,
                title = title,
                description = description,
                category = TaskCategory.fromString(category),
                date = date,
                type = TaskType.fromString(type),
                isSelected = isSelected
            )
        }
    }
}
