package com.example.routinely.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login_screen")
    object CreateAccount: Screen("create_account_screen")
    object NewPasswordScreen: Screen("new_password_screen")
    object ForgotPasswordScreen: Screen("forgot_password_screen")
    object VerificationCodeScreen: Screen("verification_code_screen")
    object HomeScreen: Screen("home_screen")
    object AddTaskScreen: Screen("add_task_screen")
}