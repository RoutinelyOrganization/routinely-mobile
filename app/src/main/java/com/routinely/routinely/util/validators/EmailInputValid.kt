package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class EmailInputValid : Parcelable {
    data object Valid: EmailInputValid()
    data class Error(val messageId: Int): EmailInputValid()
    data object Empty: EmailInputValid()
}