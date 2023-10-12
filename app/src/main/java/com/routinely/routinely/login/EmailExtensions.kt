package com.routinely.routinely.login

import android.util.Patterns

fun isValidEmailFormat(email :String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}