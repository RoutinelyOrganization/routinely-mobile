package com.routinely.routinely.util

import com.routinely.routinely.R
import kotlinx.parcelize.Parcelize

sealed class ActivityTag(stringId: Int, apiString: String) : TaskFields(stringId, apiString) {

    @Parcelize
    data object Task : ActivityTag(R.string.text_tag_task, "Task")
    @Parcelize
    data object Habit : ActivityTag(R.string.text_tag_habit, "Habit")
    @Parcelize
    data object Project : ActivityTag(R.string.text_tag_project, "Project")

}

fun ActivityTag.toStringId(): Int {
    return this.stringId
}

fun fromStringId(stringId: Int): ActivityTag {
    return TaskFields.getTaskFieldByStringId<ActivityTag>(stringId)
}