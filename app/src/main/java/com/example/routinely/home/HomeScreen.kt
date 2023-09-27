package com.example.routinely.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.routinely.R
import com.example.routinely.ui.components.BottomAppBarRoutinely
import com.example.routinely.ui.components.DatePickerRoutinely
import com.example.routinely.ui.components.TasksViewerRoutinely
import com.example.routinely.ui.components.TopAppBarRoutinely
import com.example.routinely.ui.components.datePickerState
import com.example.routinely.util.ActionItem
import com.example.routinely.util.BottomNavItems
import com.example.routinely.util.TaskItems
import com.example.routinely.util.TaskPriority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
) {
    val bottomBarItems = listOf(BottomNavItems.NewTask)
    val datePickerState = datePickerState()

    val listOfTasks = taskItemsDumb()
    val listOfConcludedTasks = concludedTaskItemsDumb()

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
                    .verticalScroll(rememberScrollState())
            ) {
                /**
                 * To use de date of date picker use this
                 * val dateSelected = Date(datePickerState.selectedDateMillis!!)
                 */
                DatePickerRoutinely(datePickerState)

                TasksViewerRoutinely(listOfTaskItems = listOfTasks, listOfConcludedTaskItems = listOfConcludedTasks)
            }
        })
}

@Composable
private fun taskItemsDumb(): List<TaskItems> {
    return listOf(
        TaskItems(
            nameOfTask = "Enviar email cv para Jean",
            categoryName = "Carreira",
            taskPriority = TaskPriority.Urgent,
            listOfActions = listOf(
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_edit,
                    contentDescriptionId = R.string.desc_high_priority,
                ),
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_trash,
                    contentDescriptionId = R.string.desc_high_priority,
                )
            )
        ),
        TaskItems(
            nameOfTask = "Ir ao médico",
            categoryName = "Saúde",
            taskPriority = TaskPriority.Low,
            listOfActions = listOf(
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_edit,
                    contentDescriptionId = R.string.desc_high_priority,
                ),
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_trash,
                    contentDescriptionId = R.string.desc_high_priority,
                )
            )
        ),
        TaskItems(
            nameOfTask = "Pagar luz",
            categoryName = "Finanças",
            taskPriority = TaskPriority.High,
            listOfActions = listOf(
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_edit,
                    contentDescriptionId = R.string.desc_high_priority,
                ),
                ActionItem(
                    onClick = { },
                    imageVectorId = R.drawable.ic_trash,
                    contentDescriptionId = R.string.desc_high_priority,
                )
            )
        )
    )
}

fun concludedTaskItemsDumb(): List<TaskItems> {
    return listOf(
        TaskItems(
            nameOfTask = "Enviar email cv para Jean",
            categoryName = "Carreira",
            taskPriority = TaskPriority.Urgent,
            listOfActions = emptyList()
        ),
        TaskItems(
            nameOfTask = "Ir ao médico",
            categoryName = "Saúde",
            taskPriority = TaskPriority.Low,
            listOfActions = emptyList()
        ),
        TaskItems(
            nameOfTask = "Pagar luz",
            categoryName = "Finanças",
            taskPriority = TaskPriority.High,
            listOfActions = emptyList()
        )
    )
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