package com.routinely.routinely.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.MenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarRoutinely(
    onNotificationClick: () -> Unit,
    onMenuClick: () -> Unit,
    onDismissMenu: () -> Unit,
    expanded: Boolean,
    showBackButton: Boolean,
    onBackButtonClicked: () -> Unit,
) {

    val menuItems: List<MenuItem> = listOf(
        MenuItem(
            text = stringResource(R.string.menu_configuration),
            onItemClick = { }
        ),
        MenuItem(
            text = stringResource(R.string.menu_goal),
            onItemClick = { }
        ),
        MenuItem(
            text = stringResource(R.string.menu_notification),
            onItemClick = { }
        ),
        MenuItem(
            text = stringResource(R.string.menu_logout),
            onItemClick = { }
        ),

    )

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
            Box(

            ) {
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
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { onDismissMenu() },
                    modifier = Modifier
                        .width(156.dp)
                        .background(color = Color.White)
                ) {
                    menuItems.forEach {
                        DropdownMenuItem(
                            text = { Text(it.text, color = PurpleRoutinely) },
                            onClick = { it.text },
                            contentPadding = PaddingValues(start = 8.dp)
                        )
                    }
                }
            }

        }
    )
}