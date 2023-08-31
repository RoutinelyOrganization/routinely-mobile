package com.example.routinely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.routinely.changepassword.CreateNewPasswordScreen
import com.example.routinely.changepassword.ForgotPasswordScreen
import com.example.routinely.login.CreateAccountScreen
import com.example.routinely.login.LoginScreen
import com.example.routinely.ui.theme.RoutinelyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Defina o tema aqui, substituindo RoutinelyTheme pelo seu tema real
            RoutinelyTheme {
                // Configure a navegação aqui, se estiver usando a biblioteca de navegação
                val navController = rememberNavController()
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
                        CreateNewPasswordScreen(navController)
                    }
                    // Defina outras rotas aqui, se necessário
                }
            }
        }
    }
}
