package com.routinely.routinely

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.routinely.routinely.navigation.SetupNavGraph
import com.routinely.routinely.ui.theme.RoutinelyTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Define a orientação como retrato
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setContent {
            RoutinelyTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                )
            }
        }
    }
}
