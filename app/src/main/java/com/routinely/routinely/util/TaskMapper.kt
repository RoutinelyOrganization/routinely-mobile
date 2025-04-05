package com.routinely.routinely.util

import com.routinely.routinely.ui.components.Task
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskMapper {
    companion object {
        private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

        fun fromApi(taskItem: TaskItem): Task? {
            return try {
                Task.fromApi(
                    id = taskItem.id,
                    title = taskItem.name,
                    description = taskItem.description ?: "",
                    category = taskItem.category,
                    date = LocalDate.parse(taskItem.date, dateFormatter),
                    type = taskItem.type
                )
            } catch (e: Exception) {
                null
            }
        }
    }
} 