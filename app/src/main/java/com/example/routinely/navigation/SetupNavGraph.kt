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

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        loginRoute(
            navigateToHomeScreen = {
                navController.popBackStack()
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
//                navController.navigate()
            },
        )
        addTaskScreenRoute(

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
) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(
            onMenuClicked = { onMenuClicked() },
            onNotificationClicked = { onNotificationClicked() },
            onNewTaskClicked = { onNewTaskClicked() },
        )
    }
}

fun NavGraphBuilder.addTaskScreenRoute(
) {
    composable(route = Screen.AddTaskScreen.route) {
        // AddTask()
    }
}

@Composable
fun getCurrentDestination(navController: NavController): NavDestination? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    return navBackStackEntry?.destination
}
