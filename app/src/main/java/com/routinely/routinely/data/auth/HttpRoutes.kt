package com.routinely.routinely.data.auth

import com.routinely.routinely.BuildConfig

object HttpRoutes {
    const private val BASE_URL = BuildConfig.BASE_URL_DEBUG
    const val REGISTER = "/auth/register"
    const val TASK = "/tasks"
    const val LOGIN = "/auth"
    const val REFRESH_TOKEN = "$BASE_URL/auth/refresh"
    const val FORGOT_PASSWORD = "/auth/resetpassword"
    const val VALIDATE_CODE = "$BASE_URL/auth/validatecode"
    const val CHANGE_PASSWORD = "$BASE_URL/auth/changepassword"
}
