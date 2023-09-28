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
import com.example.routinely.home.HomeScreen
import com.example.routinely.login.LoginScreen
import com.example.routinely.login.LoginViewModel
import com.example.routinely.splash_screen.SplashScreen
import com.example.routinely.task.AddTaskScreen
import com.example.routinely.task.EditTaskScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.EditTaskScreen.route
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

) {
    composable(route = Screen.CreateAccount.route) {
//        CreateAccountScreen()
    }
}

fun NavGraphBuilder.newPasswordRoute(

) {

}

fun NavGraphBuilder.forgotPasswordRoute(

) {

}

fun NavGraphBuilder.verificationCodeRoute(

) {

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
            onBackButtonPressed = { onBackButtonPressed() },
            onHomeButtonPressed = { onHomeButtonPressed() },
        )
        AddTaskScreen(
            onBackButtonPressed = { onBackButtonPressed() },
            onHomeButtonPressed = { onHomeButtonPressed() },
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
