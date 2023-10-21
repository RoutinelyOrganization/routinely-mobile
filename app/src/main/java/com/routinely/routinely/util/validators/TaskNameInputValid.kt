package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class TaskNameInputValid : Parcelable {
    data object Valid: TaskNameInputValid()
    data class Error(val messageId: Int): TaskNameInputValid()
    data object Empty: TaskNameInputValid()
}
