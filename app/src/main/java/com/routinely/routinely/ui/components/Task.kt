package com.routinely.routinely.ui.components

import java.time.LocalDate

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    var category: Int,
    var isSelected: Boolean = false,
    val date: LocalDate
)