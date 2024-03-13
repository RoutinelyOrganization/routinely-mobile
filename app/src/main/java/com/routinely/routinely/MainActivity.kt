package com.routinely.routinely

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.compose.rememberNavController
import com.routinely.routinely.data.core.Session
import com.routinely.routinely.navigation.Screen
import com.routinely.routinely.navigation.SetupNavGraph
import com.routinely.routinely.ui.theme.RoutinelyTheme
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext
import timber.log.Timber

class MainActivity : ComponentActivity() {
    private val session: Session by inject()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            RoutinelyTheme {
                KoinContext {
                    val navController = rememberNavController()
                    checkNotificationPolicyAccess(notificationManager, this)
                    SetupNavGraph(
                        navController = navController,
                        startDest = getStartDestination(),
                    )
                }
            }
        }
    }

    private fun getStartDestination(): Screen {
        return if(session.getRememberLogin()) Screen.HomeScreen else Screen.SplashScreen
    }
}

@Composable
fun checkNotificationPolicyAccess(
    notificationManager: NotificationManager,
    context: Context
): Boolean {
    if (notificationManager.areNotificationsEnabled() || Build.VERSION.SDK_INT < 32) {
        return true
    } else {
        PermissionDialog(context)
    }
    return false
}

@Composable
fun PermissionDialog(context: Context) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            ),
            title = { Text("Permissão necessária") },
            text = {
                Text("Para utilizar o Chucker no modo de Debug é necessário ativar as notificações do aplicativo no Android 13+")
            },
            onDismissRequest = { openDialog.value = true },

            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                ) {
                    Text(text = "Cancelar")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        data = uri
                    }
                    openDialog.value = false
                    startActivity(context, intent, null)
                }) { Text(text = "Confirmar") }
            },
        )
    }
}
