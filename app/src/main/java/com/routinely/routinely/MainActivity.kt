package com.routinely.routinely

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.navigation.Screen
import com.routinely.routinely.navigation.SetupNavGraph
import com.routinely.routinely.ui.theme.RoutinelyTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val session: Session by inject()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            RoutinelyTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    startDest = getStartDestination(),
                )
            }
        }
    }

    private fun getStartDestination(): Screen {
        return if(session.getRememberLogin()) Screen.HomeScreen else Screen.SplashScreen
    }
}
