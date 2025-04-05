package com.routinely.routinely.util

enum class TaskCategory(val id: Int) {
    CAREER(1),
    FINANCE(2),
    STUDIES(3),
    HEALTH(4),
    LEISURE(5),
    PRODUCTIVITY(6),
    SEVERAL(7);

    val apiName: String
        get() = name.lowercase().replaceFirstChar { it.uppercase() }

    companion object {
        fun fromString(value: String): TaskCategory {
            return try {
                valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                SEVERAL
            }
        }

        fun fromId(id: Int): TaskCategory {
            return entries.find { it.id == id } ?: SEVERAL
        }

        fun toTaskFieldsList(): List<TaskFields> {
            return entries.map { category ->
                object : TaskFields(category.id, category.apiName) {}
            }
        }
    }
}
