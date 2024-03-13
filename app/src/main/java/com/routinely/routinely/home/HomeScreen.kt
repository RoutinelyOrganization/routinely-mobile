package com.routinely.routinely.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerRoutinely
import com.routinely.routinely.ui.components.TaskAlertDialog
import com.routinely.routinely.ui.components.TasksViewerRoutinely
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.components.datePickerState
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskItem
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
    onEditTaskClicked: (taskItem: TaskItem) -> Unit,
    onDeleteTaskClicked: (taskItem: TaskItem) -> Unit,
    menuItems: List<MenuItem>,
    onSelectDayChange: (Int, Int, Int) -> Unit,
    getTasksResponse: ApiResponseWithData<List<TaskItem>>,
) {
    val bottomBarItems = listOf(BottomNavItems.NewTask)
    val datePickerState = datePickerState()

    var expanded by remember { mutableStateOf(false) }

    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    var temporaryDeleteId by remember { mutableStateOf<TaskItem?>(null) }

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
                DatePickerRoutinely(
                    datePickerState,
                )
                TasksViewerRoutinely(
                    getTasksResponse = getTasksResponse,
                    listOfConcludedTaskItems = emptyList(),
                    onEditButtonClicked = onEditTaskClicked,
                    onDeleteButtonClicked = {
                        showDeleteDialog = true
                        temporaryDeleteId = it
                    },
                )


                if (showDeleteDialog) {
                    TaskAlertDialog(
                        textRes = R.string.delete_task_confirmation,
                        onConfirm = {
                            showDeleteDialog = false
                            temporaryDeleteId?.let { onDeleteTaskClicked(it) }
                            datePickerState.selectedDateMillis = datePickerState.selectedDateMillis
                        },
                        onCancel = {
                            showDeleteDialog = false
                        },
                        onDismissRequest = {
                            showDeleteDialog = false
                        }
                    )
                }
            }
        }
    )

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis == null) return@LaunchedEffect

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = datePickerState.selectedDateMillis!!

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        onSelectDayChange(month, year, day)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onNotificationClicked = { },
        onNewTaskClicked = { },
        onEditTaskClicked = { },
        onDeleteTaskClicked = { },
        menuItems = listOf(),
        onSelectDayChange = { _, _, _ -> },
        getTasksResponse = ApiResponseWithData.Default()
    )
}