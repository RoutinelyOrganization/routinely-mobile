package com.routinely.routinely.util

import com.routinely.routinely.R


sealed class TaskPriority (
    var icon: Int,
    var description: Int,
    ) {
    object Urgent:
            TaskPriority(
                icon = R.drawable.ic_urgent_priority,
                description = R.string.desc_urgent_priority
            )
    object High:
        TaskPriority(
            icon = R.drawable.ic_high_priority,
            description = R.string.desc_high_priority
        )
    object Medium:
        TaskPriority(
            icon = R.drawable.ic_medium_priority,
            description = R.string.desc_medium_priority
        )
    object Low:
        TaskPriority(
            icon = R.drawable.ic_low_priority,
            description = R.string.desc_low_priority
        )
}