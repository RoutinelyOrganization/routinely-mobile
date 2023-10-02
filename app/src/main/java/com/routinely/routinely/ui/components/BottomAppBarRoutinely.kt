package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.BottomNavItems

@Composable
fun BottomAppBarRoutinely(
    bottomBarItems: List<BottomNavItems>,
    onClick: () -> Unit,
) {
    NavigationBar(
        containerColor = PurpleRoutinely,
    ){
        bottomBarItems.forEach { item ->
            AddItem(
                screen = item,
                onClick = onClick,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItems,
    onClick: () -> Unit,
) {
    NavigationBarItem(
        label = {
            Text(
                text = stringResource(screen.title),
                color = Color.White,
            )
        },
        icon = {
            Icon(
                painterResource(id = screen.icon),
                contentDescription = stringResource(screen.title),
                tint = Color.White,
            )
        },
        selected = true,
        alwaysShowLabel = true,
        onClick = { onClick() },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = PurpleRoutinely,
        ),
    )
}