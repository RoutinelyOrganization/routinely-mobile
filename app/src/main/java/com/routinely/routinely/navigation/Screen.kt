package com.routinely.routinely.navigation

import com.routinely.routinely.navigation.Screen.AddTaskScreen.NEW_PASSWORD_SCREEN
import com.routinely.routinely.navigation.Screen.AddTaskScreen.VERIFICATION_CODE_SCREEN_ROUTE

sealed class Screen(val route: String) {
    data object Login: Screen("login_screen")
    data object CreateAccount: Screen("create_account_screen")

    val NEW_PASSWORD_SCREEN = "verification_code_screen"
    data object NewPasswordScreen: Screen("$NEW_PASSWORD_SCREEN/{accountId}/{code}"){
        fun withArgs(accountId: String, code: String): String {
            return "$NEW_PASSWORD_SCREEN/$accountId/$code"
        }
    }
    data object ForgotPasswordScreen: Screen("forgot_password_screen")

    val VERIFICATION_CODE_SCREEN_ROUTE = "verification_code_screen"
    data object VerificationCodeScreen: Screen("$VERIFICATION_CODE_SCREEN_ROUTE/{accountId}"){
        fun withArgs(accountId: String): String {
            return "$VERIFICATION_CODE_SCREEN_ROUTE/$accountId"
        }
    }
    data object HomeScreen: Screen("home_screen")
    data object AddTaskScreen: Screen("add_task_screen")
    data object SplashScreen: Screen("splash_screen")
    data object EditTaskScreen: Screen("edit_task_screen/{taskId}") {
        fun withArgs(taskId: Int): String {
            return "edit_task_screen/$taskId"
        }
    }
}