package com.example.routinely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.routinely.login.LoginScreen
import com.example.routinely.ui.theme.RoutinelyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoutinelyTheme {
                LoginScreen()
            }
        }
    }
}
