package com.routinely.routinely.util

data class MenuItem(
    val text: String,
    val onItemClick: () -> Unit,
)
