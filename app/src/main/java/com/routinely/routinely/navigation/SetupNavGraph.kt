package com.routinely.routinely.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.routinely.routinely.changepassword.CreateNewPasswordScreen
import com.routinely.routinely.changepassword.ForgotPasswordScreen
import com.routinely.routinely.changepassword.VerificationCodeScreen
import com.routinely.routinely.home.HomeScreen
import com.routinely.routinely.login.CreateAccountScreen
import com.routinely.routinely.login.LoginScreen
import com.routinely.routinely.login.LoginViewModel
import com.routinely.routinely.splash_screen.SplashScreen
import com.routinely.routinely.task.AddTaskScreen
import com.routinely.routinely.task.EditTaskScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        loginRoute(
            navigateToHomeScreen = {
                navController.popBackStack(route = Screen.SplashScreen.route, inclusive = true)
                navController.navigate(Screen.HomeScreen.route)
            },
            navigateToCreateAccountScreen = {
                navController.navigate(Screen.CreateAccount.route)
            },
            navigateToForgotPasswordScreen = {
                navController.navigate(Screen.ForgotPasswordScreen.route)
            },
        )
        homeScreenRoute(
            onNotificationClicked = {
//                navController.navigate()
            },
            onMenuClicked = {
//                navController.navigate()
            },
            onNewTaskClicked = {
                navController.navigate(Screen.AddTaskScreen.route)
            },
            onEditTaskClicked = {
                navController.navigate(Screen.EditTaskScreen.route)
            }
        )
        addTaskScreenRoute(
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onHomeButtonPressed = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            }
        )
        splashScreenRoute(
            onEmailLoginClicked = {
                navController.navigate(Screen.Login.route)
            }
        )
        createAccountRoute(
            onAlreadyHaveAnAccountClicked = {
                navController.navigate(Screen.Login.route)
            },
            onCreateAccountClicked = {
                navController.navigate(Screen.Login.route)
            }
        )
        forgotPasswordRoute(
            onResetPasswordClicked = {
                navController.navigate(Screen.VerificationCodeScreen.route)
            }
        )
        verificationCodeRoute(
            onConfirmResetPasswordClicked = {
                navController.navigate(Screen.NewPasswordScreen.route)
            }
        )
        newPasswordRoute(
            onUpdatePasswordClicked = {
                navController.navigate(Screen.Login.route)
            }
        )

        editTaskScreenRoute(
            onBackButtonPressed = {
                navController.popBackStack()
            },
            onHomeButtonPressed = {
                navController.popBackStack()
                navController.navigate(Screen.HomeScreen.route)
            },
            onMenuClicked = {},
            onNotificationClicked = {}
        )
    }
}

fun NavGraphBuilder.loginRoute(
    navigateToHomeScreen: () -> Unit,
    navigateToCreateAccountScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
) {
    composable(route = Screen.Login.route) {
        val viewModel: LoginViewModel = viewModel()
        val authenticated by viewModel.authenticated
        LoginScreen(
            viewModel = viewModel,
            authenticated = authenticated,
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToCreateAccountScreen = navigateToCreateAccountScreen,
            navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
        )
    }
}
fun NavGraphBuilder.createAccountRoute(
    onCreateAccountClicked: () -> Unit,
    onAlreadyHaveAnAccountClicked: () -> Unit,
) {
    composable(route = Screen.CreateAccount.route) {
        CreateAccountScreen(
            onCreateAccountClicked = onCreateAccountClicked,
            onAlreadyHaveAnAccountClicked = onAlreadyHaveAnAccountClicked ,
        )
    }
}
fun NavGraphBuilder.newPasswordRoute(
    onUpdatePasswordClicked: () -> Unit
) {
    composable(route = Screen.NewPasswordScreen.route) {
        CreateNewPasswordScreen(
            onUpdatePasswordClicked = onUpdatePasswordClicked,
        )
    }
}
fun NavGraphBuilder.forgotPasswordRoute(
    onResetPasswordClicked: () -> Unit
) {
    composable(route = Screen.ForgotPasswordScreen.route) {
        ForgotPasswordScreen(
            onResetPasswordClicked = onResetPasswordClicked
        )
    }
}
fun NavGraphBuilder.verificationCodeRoute(
    onConfirmResetPasswordClicked: () -> Unit
) {
    composable(route = Screen.VerificationCodeScreen.route) {
        VerificationCodeScreen(
            onConfirmResetPasswordClicked = onConfirmResetPasswordClicked
        )
    }
}

fun NavGraphBuilder.homeScreenRoute(
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
    onEditTaskClicked: () -> Unit,
) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(
            onMenuClicked = { onMenuClicked() },
            onNotificationClicked = { onNotificationClicked() },
            onNewTaskClicked = { onNewTaskClicked() },
            onEditTaskClicked = { onEditTaskClicked() }
        )
    }
}

fun NavGraphBuilder.addTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit
) {
    composable(route = Screen.AddTaskScreen.route) {
        AddTaskScreen(
            onBackButtonPressed = onBackButtonPressed,
            onHomeButtonPressed = onHomeButtonPressed,
        )
    }
}

fun NavGraphBuilder.editTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onHomeButtonPressed: () -> Unit,
) {
    composable(route = Screen.EditTaskScreen.route) {
        EditTaskScreen(
            onBackButtonPressed = { onBackButtonPressed() },
            onHomeButtonPressed = { onHomeButtonPressed() },
            onMenuClicked = { onMenuClicked() },
            onNotificationClicked = { onNotificationClicked() },
        )
    }
}

fun NavGraphBuilder.splashScreenRoute(
    onEmailLoginClicked: () -> Unit,
) {
    composable(route = Screen.SplashScreen.route) {
        SplashScreen(
            onEmailLoginClicked = { onEmailLoginClicked() }
        )
    }
}

@Composable
fun getCurrentDestination(navController: NavController): NavDestination? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    return navBackStackEntry?.destination
}
