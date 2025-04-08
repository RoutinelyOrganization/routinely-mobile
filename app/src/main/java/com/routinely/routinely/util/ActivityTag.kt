package com.routinely.routinely.util

import android.os.Parcelable
import com.routinely.routinely.R
import kotlinx.parcelize.Parcelize

sealed class ActivityTag(stringId: Int, apiString: String) : TaskFields(stringId, apiString) {

    @Parcelize
    data object Task : ActivityTag(R.string.text_tag_task, "Tarefa"), Parcelable

    @Parcelize
    data object Habit : ActivityTag(R.string.text_tag_habit, "Hábito"), Parcelable

    companion object {
        fun fromId(id: Int): ActivityTag? {
            return when (id) {
                Task.stringId -> Task
                Habit.stringId -> Habit
                else -> null
            }
        }
    }
}
