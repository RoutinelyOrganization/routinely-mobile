package com.example.routinely.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.routinely.ui.components.BottomAppBarRoutinely
import com.example.routinely.ui.components.TopAppBarRoutinely
import com.example.routinely.util.BottomNavItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
) {
    val bottomBarItems = listOf(BottomNavItems.NewTask)
    Scaffold(
        topBar = {
              TopAppBarRoutinely(
                  onMenuClick = { onMenuClicked() },
                  onNotificationClick = { onNotificationClicked() },
                  showBackButton = false,
                  onBackButtonClicked = { }
              )
        },
        bottomBar = {
            BottomAppBarRoutinely(
                bottomBarItems = bottomBarItems,
                onClick = { onNewTaskClicked() },
            )
    },
    content = { initialPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(initialPadding)
        ) {
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val current = navBackStackEntry?.destination
            Text(
                text = "Este é o conteúdo da tela", style = TextStyle(
                    fontSize = 24.sp, fontWeight = FontWeight.Bold
                )
            )
        }
    })
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onMenuClicked = { },
        onNotificationClicked = { },
        onNewTaskClicked = { },
    )
}