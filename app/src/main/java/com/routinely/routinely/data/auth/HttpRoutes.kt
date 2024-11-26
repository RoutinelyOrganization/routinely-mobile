package com.routinely.routinely.data.auth

object HttpRoutes {
    const val REGISTER = "/auth/register?callBackUrl=https://routinely-api-next.vercel.app"
    const val TASK = "/tasks"
    const val LOGIN = "/auth"
    const val REFRESH_TOKEN = "/auth/refresh"
    const val FORGOT_PASSWORD = "/auth/resetpassword"
    const val VALIDATE_CODE = "/auth/validatecode"
    const val CHANGE_PASSWORD = "/auth/changepassword"
}
