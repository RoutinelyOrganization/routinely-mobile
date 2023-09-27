package com.example.routinely.util

import com.example.routinely.R

sealed class ActionItem(
    val onClick: () -> Unit,
    val imageVectorId: Int,
    val contentDescriptionId: Int,
) {
    class Edit(onClick: () -> Unit) :
        ActionItem(
            onClick = onClick,
            imageVectorId = R.drawable.ic_edit,
            contentDescriptionId = R.string.desc_high_priority,
        )

    class Exclude(onClick: () -> Unit):
        ActionItem(
            onClick = onClick,
            imageVectorId = R.drawable.ic_trash,
            contentDescriptionId = R.string.desc_high_priority,
        )
}