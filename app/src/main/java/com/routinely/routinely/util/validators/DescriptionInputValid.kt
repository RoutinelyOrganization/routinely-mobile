package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class DescriptionInputValid : Parcelable {
    data object Valid: DescriptionInputValid()
    data class Error(val messageId: Int): DescriptionInputValid()
    data object Empty: DescriptionInputValid()
}
