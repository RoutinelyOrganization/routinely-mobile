package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class DateTimeInputValid : Parcelable {
    data object Valid: DateTimeInputValid()
    data class Error(val messageId: Int): DateTimeInputValid()
    data object Empty: DateTimeInputValid()
}
