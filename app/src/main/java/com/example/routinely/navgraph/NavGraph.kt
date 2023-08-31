package com.example.routinely.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.routinely.changepassword.ForgotPasswordScreen
import com.example.routinely.login.CreateAccountScreen
import com.example.routinely.login.LoginScreen

@Composable
//essa função ainda não é utilizada, toda a navegação será aprimorada em breve

fun NavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("createaccount") {
            CreateAccountScreen(navController)
        }
        composable("forgotpassword") {
            ForgotPasswordScreen(navController)
        }
        composable("createnewpassword") {
            ForgotPasswordScreen(navController)
        }
        composable("verificationcodescreen") {
            ForgotPasswordScreen(navController)
        }
    }
}