package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class CodeInputValid : Parcelable {
    data object Valid: CodeInputValid()
    data class Error(val messageId: Int): CodeInputValid()
    data object Empty: CodeInputValid()
}
