package com.routinely.routinely.util

import com.routinely.routinely.R
import kotlinx.parcelize.Parcelize

sealed class ActivityTag(stringId: Int, apiString: String) : TaskFields(stringId, apiString) {

    @Parcelize
    data object Task : ActivityTag(R.string.text_tag_activity, "Activity")

    @Parcelize
    data object Habit : ActivityTag(R.string.text_tag_habit, "Habit")

    @Parcelize
    data object Project : ActivityTag(R.string.text_tag_project, "Project")

    @Parcelize
    data object AllActivity : ActivityTag(R.string.text_tag_activities, "All_Activity")

    @Parcelize
    data object Completed : ActivityTag(R.string.text_tag_completed, "Completed")
}