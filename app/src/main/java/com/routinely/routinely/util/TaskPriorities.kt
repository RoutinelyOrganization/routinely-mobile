package com.routinely.routinely.util

import androidx.compose.ui.graphics.Color
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.HighPriority
import com.routinely.routinely.ui.theme.LowPriority
import com.routinely.routinely.ui.theme.MediumPriority
import com.routinely.routinely.ui.theme.UrgentPriority
import kotlinx.parcelize.Parcelize


sealed class TaskPriorities (
    stringId: Int,
    apiString: String,
    val textColor: Color,
    var icon: Int,
    var contentDescription: Int,
    val orderLevel: Int,
) : TaskFields(stringId, apiString) {
    @Parcelize
    data object Urgent: TaskPriorities(
        stringId = R.string.urgent_priority,
        apiString = "urgent",
        textColor = UrgentPriority,
        icon = R.drawable.ic_urgent_priority,
        contentDescription = R.string.desc_urgent_priority,
        orderLevel = 1,
    )



    @Parcelize
    data object High : TaskPriorities(
        stringId = R.string.high_priority,
        apiString = "high",
        textColor = HighPriority,
        icon = R.drawable.ic_high_priority,
        contentDescription = R.string.desc_high_priority,
        orderLevel = 2,
    )
    @Parcelize
    data object Medium : TaskPriorities(
        stringId = R.string.medium_priority,
        apiString = "medium",
        textColor = MediumPriority,
        icon = R.drawable.ic_medium_priority,
        contentDescription = R.string.desc_medium_priority,
        orderLevel = 3,
    )
    @Parcelize
    data object Low : TaskPriorities(
        stringId = R.string.low_priority,
        apiString = "low",
        textColor = LowPriority,
        icon = R.drawable.ic_low_priority,
        contentDescription = R.string.desc_low_priority,
        orderLevel = 4,
    )

    companion object {
        fun getAllTaskPriorities(): List<TaskPriorities> {
            return TaskPriorities::class.sealedSubclasses
                .map { it.objectInstance as TaskPriorities }
                .sortedBy { it.orderLevel }
        }

        fun getAllStringWithColor(): Map<Int, Color> {
            return TaskPriorities::class.sealedSubclasses
                .map { it.objectInstance as TaskPriorities }
                .sortedBy { it.orderLevel }
                .associateBy { it.stringId }
                .mapValues { it.value.textColor }
        }
    }
}
