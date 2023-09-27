package com.example.routinely.util

data class ActionItem(
    val onClick: () -> Unit,
    val imageVectorId: Int,
    val contentDescriptionId: Int,
)