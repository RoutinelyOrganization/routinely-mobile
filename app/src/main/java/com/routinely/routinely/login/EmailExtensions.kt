package com.routinely.routinely.login

fun isValidEmailFormat(email: String): Boolean {
    val regexPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]{2,})+$".toRegex()
    return email.matches(regexPattern)
}