package com.routinely.routinely.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarRoutinely(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    showBackButton: Boolean,
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = PurpleRoutinely,
        ),
        title = {
            Image(
                painter = painterResource(R.drawable.topbar_horizontal_2),
                contentDescription = stringResource(R.string.desc_top_bar),
                // TODO review svg we are using for top bar
                modifier = Modifier.size(94.dp, 36.dp)
            )
        },
        navigationIcon = {
            if(showBackButton) {
                IconButton(
                    onClick = {
                        onBackButtonClicked()
                    }
                ) {
                    Icon(
                        painterResource(R.drawable.voltar),
                        stringResource(R.string.desc_back_button),
                        tint = Color.White,
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onNotificationClick()
                }
            ) {
                Icon(
                    Icons.Outlined.Notifications,
                    stringResource(R.string.desc_notification_button),
                    tint = Color.White,
                )
            }
            IconButton(
                onClick = {
                    onMenuClick()
                }
            ) {
                Icon(
                    Icons.Outlined.Menu,
                    stringResource(R.string.desc_notification_button),
                    tint = Color.White
                )
            }
        }
    )
}