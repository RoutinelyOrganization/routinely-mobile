package com.routinely.routinely.util

import com.routinely.routinely.R
import kotlinx.parcelize.Parcelize


sealed class TaskTag(stringId: Int, apiString: String) : TaskFields(stringId, apiString) {
    @Parcelize
    data object Candidacy : TaskTag(R.string.text_tag_candidacy, "application")
    @Parcelize
    data object Bill : TaskTag(R.string.text_tag_bill, "account")
    @Parcelize
    data object Exercise : TaskTag(R.string.text_tag_exercise, "exercise")
    @Parcelize
    data object Beauty : TaskTag(R.string.text_tag_beauty, "beauty")
    @Parcelize
    data object Literature : TaskTag(R.string.text_tag_literature, "literature")
}