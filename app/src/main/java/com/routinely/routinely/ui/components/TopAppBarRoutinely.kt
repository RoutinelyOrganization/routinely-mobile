package com.routinely.routinely.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.routinely.routinely.R
import com.routinely.routinely.ui.crop
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
    menuItems: List<MenuItem>,
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
            Box{
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
                MaterialTheme(
                    shapes = MaterialTheme.shapes.copy(
                        extraSmall = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp)
                    )
                ) {
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { onDismissMenu() },
                        modifier = Modifier
                            .width(156.dp)
                            .background(color = Color.White)
                            .crop(0.dp, 8.dp),
//                        offset = DpOffset(10.dp, 20.dp)
                        offset = DpOffset(0.dp, 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_close),
                                    contentDescription = "Teste",
                                    tint = Color.Black
                                )
                            }
                        }
                        menuItems.forEach {
                            DropdownMenuItem(
                                text = { Text(it.text, color = PurpleRoutinely) },
                                onClick = { it.onItemClick() },
                                contentPadding = PaddingValues(start = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}
