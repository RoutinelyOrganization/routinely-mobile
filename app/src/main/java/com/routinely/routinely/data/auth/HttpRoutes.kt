package com.routinely.routinely.data.auth

object HttpRoutes {
    private const val BASE_URL = "xxxxxxxxxxxxxx.com"
    const val REGISTER = "$BASE_URL/auth/register"
    const val TASK = "$BASE_URL/tasks"
    const val LOGIN = "$BASE_URL/auth"
    const val FORGOT_PASSWORD = "$BASE_URL/auth/resetpassword"
    const val VALIDATE_CODE = "$BASE_URL/auth/validatecode"
    const val CHANGE_PASSWORD = "$BASE_URL/auth/changepassword"
}
