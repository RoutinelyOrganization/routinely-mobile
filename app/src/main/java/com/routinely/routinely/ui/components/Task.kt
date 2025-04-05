package com.routinely.routinely.ui.components

import java.time.LocalDate

data class Task (
    val id: Int,
    val activityTag: Int,
    val description: String,
    var categoryTask: Int,
    var isSelected: Boolean = false,
    val date: LocalDate
)
