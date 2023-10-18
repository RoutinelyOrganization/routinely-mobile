package com.routinely.routinely.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerRoutinely
import com.routinely.routinely.ui.components.TasksViewerRoutinely
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.components.datePickerState
import com.routinely.routinely.util.ActionItem
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.Categories
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskItems
import com.routinely.routinely.util.TaskPriority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
    onEditTaskClicked: () -> Unit,
    menuItems: List<MenuItem>,
) {
    val bottomBarItems = listOf(BottomNavItems.NewTask)
    val datePickerState = datePickerState()

    val listOfTasks = taskItemsDumb(onEditTaskClicked)
    val listOfConcludedTasks = concludedTaskItemsDumb()

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = { expanded = true },
                onNotificationClick = { onNotificationClicked() },
                showBackButton = false,
                onBackButtonClicked = { },
                expanded = expanded,
                onDismissMenu = { expanded = false },
                menuItems = menuItems,
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
                    .verticalScroll(rememberScrollState())
            ) {
                /**
                 * To use de date of date picker use this
                 * val dateSelected = Date(datePickerState.selectedDateMillis!!)
                 */
                DatePickerRoutinely(datePickerState)

                TasksViewerRoutinely(
                    listOfTaskItems = listOfTasks,
                    listOfConcludedTaskItems = listOfConcludedTasks
                )
            }
        })
}

@Composable
private fun taskItemsDumb(
    onEditTaskClicked: () -> Unit,
): List<TaskItems> {
    return listOf(
        TaskItems(
            nameOfTask = "Enviar email cv para Jean",
            category = Categories.Career,
            taskPriority = TaskPriority.Urgent,
            listOfActions = listOf(
                ActionItem.Edit(
                    onClick = {
                        onEditTaskClicked()
                    }
                ),
                ActionItem.Exclude(
                    onClick = {

                    }
                )
            )
        ),
        TaskItems(
            nameOfTask = "Ir ao médico",
            category = Categories.Health,
            taskPriority = TaskPriority.Low,
            listOfActions = listOf(
                ActionItem.Edit(
                    onClick = {
                        onEditTaskClicked()
                    }
                ),
                ActionItem.Exclude(
                    onClick = {

                    }
                )
            )
        ),
        TaskItems(
            nameOfTask = "Pagar luz",
            category = Categories.Finances,
            taskPriority = TaskPriority.High,
            listOfActions = listOf(
                ActionItem.Edit(
                    onClick = {
                        onEditTaskClicked()
                    }
                ),
                ActionItem.Exclude(
                    onClick = {

                    }
                )
            )
        ),
        TaskItems(
            nameOfTask = "Estudar Inglês",
            category = Categories.Studies,
            taskPriority = TaskPriority.High,
            listOfActions = listOf(
                ActionItem.Edit(
                    onClick = {
                        onEditTaskClicked()
                    }
                ),
                ActionItem.Exclude(
                    onClick = {

                    }
                )
            )
        )
    )
}

fun concludedTaskItemsDumb(): List<TaskItems> {
    return listOf(
        TaskItems(
            nameOfTask = "Enviar email cv para Jean",
            category = Categories.Career,
            taskPriority = TaskPriority.Urgent,
            listOfActions = emptyList()
        ),
        TaskItems(
            nameOfTask = "Ir ao médico",
            category = Categories.Health,
            taskPriority = TaskPriority.Low,
            listOfActions = emptyList()
        ),
        TaskItems(
            nameOfTask = "Pagar luz",
            category = Categories.Finances,
            taskPriority = TaskPriority.High,
            listOfActions = emptyList()
        )
    )
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onNotificationClicked = { },
        onNewTaskClicked = { },
        onEditTaskClicked = { },
        menuItems = listOf()
    )
}