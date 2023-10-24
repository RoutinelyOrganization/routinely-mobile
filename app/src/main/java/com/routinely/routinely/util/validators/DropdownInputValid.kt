package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class DropdownInputValid : Parcelable {
    data object Valid: DropdownInputValid()
    data class Error(val messageId: Int): DropdownInputValid()
    data object Empty: DropdownInputValid()
}
