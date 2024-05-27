package com.routinely.routinely.ui.components

data class Task (
    val id: Int,
    val title: String,
    val description: String,
    val category: Int,
    var isSelected: Boolean = false
)