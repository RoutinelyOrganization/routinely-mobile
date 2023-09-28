package com.example.routinely.navigation

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
import com.example.routinely.changepassword.CreateNewPasswordScreen
import com.example.routinely.changepassword.ForgotPasswordScreen
import com.example.routinely.changepassword.VerificationCodeScreen
import com.example.routinely.home.HomeScreen
import com.example.routinely.login.CreateAccountScreen
import com.example.routinely.login.LoginScreen
import com.example.routinely.login.LoginViewModel
import com.example.routinely.splash_screen.SplashScreen
import com.example.routinely.task.AddTask

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
) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(
            onMenuClicked = { onMenuClicked() },
            onNotificationClicked = onNotificationClicked,
            onNewTaskClicked = onNewTaskClicked,
        )
    }
}

fun NavGraphBuilder.addTaskScreenRoute(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit
) {
    composable(route = Screen.AddTaskScreen.route) {
        AddTask(
            onBackButtonPressed = onBackButtonPressed,
            onHomeButtonPressed = onHomeButtonPressed,
        )
        AddTask(
            onBackButtonPressed = onBackButtonPressed,
            onHomeButtonPressed = onHomeButtonPressed,
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
