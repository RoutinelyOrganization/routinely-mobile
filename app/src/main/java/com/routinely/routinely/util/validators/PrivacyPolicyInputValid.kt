package com.routinely.routinely.util.validators

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class PrivacyPolicyInputValid : Parcelable {
    data object Valid: PrivacyPolicyInputValid()
    data object Error: PrivacyPolicyInputValid()
    data object Empty: PrivacyPolicyInputValid()
}