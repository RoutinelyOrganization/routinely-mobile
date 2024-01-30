package com.routinely.routinely.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("login_screen")
    data object CreateAccount: Screen("create_account_screen")
    data object NewPasswordScreen: Screen("new_password_screen")
    data object ForgotPasswordScreen: Screen("forgot_password_screen")
    data object VerificationCodeScreen: Screen("verification_code_screen")
    data object HomeScreen: Screen("home_screen")
    data object AddTaskScreen: Screen("add_task_screen")
    data object SplashScreen: Screen("splash_screen")
    data object EditTaskScreen: Screen("edit_task_screen/{taskId}/{month}/{year}") {
        fun withArgs(taskId: Int, month: Int, year: Int): String {
            return "edit_task_screen/$taskId/$month/$year"
        }
    }
}