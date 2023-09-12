package com.example.routinely.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.routinely.changepassword.ForgotPasswordScreen
import com.example.routinely.home.HomeScreen
import com.example.routinely.login.CreateAccountScreen
import com.example.routinely.login.LoginScreen
import com.example.routinely.login.LoginViewModel

@Composable
fun NavGraph(
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

) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(

        )
    }
}
