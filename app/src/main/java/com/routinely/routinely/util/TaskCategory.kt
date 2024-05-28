package com.routinely.routinely.util

import com.routinely.routinely.R
import kotlinx.parcelize.Parcelize

sealed class TaskCategory(stringId: Int, apiString: String) : TaskFields(stringId, apiString) {
    @Parcelize
    data object Career : TaskCategory(R.string.category_career_text, "career")
    @Parcelize
    data object Personal : TaskCategory(R.string.category_personal_text, "personal")
    @Parcelize
    data object Health : TaskCategory(R.string.category_health_text, "health")
    @Parcelize
    data object Finances : TaskCategory(R.string.category_finances_text, "finance")
    @Parcelize
    data object Studies : TaskCategory(R.string.category_studies_text, "study")
}