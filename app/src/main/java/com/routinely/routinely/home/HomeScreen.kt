package com.routinely.routinely.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.CalendarRoutinely
import com.routinely.routinely.ui.components.CardTask
import com.routinely.routinely.ui.components.DropdownActivityFilter
import com.routinely.routinely.ui.components.Task
import com.routinely.routinely.ui.components.TaskAlertDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskItem
import java.time.LocalDate

fun <K, V> snapshotStateMapSaver() = Saver<SnapshotStateMap<K, V>, Any>(
    save = { map ->
        map.entries.map { it.key to it.value }
    },
    restore = { restored ->
        val restoredList = restored as List<Pair<K, V>>
        SnapshotStateMap<K, V>().apply {
            restoredList.forEach { (key, value) ->
                this[key] = value
            }
        }
    }
)

@Composable
fun HomeScreen(
    onNotificationClicked: () -> Unit,
    onNewTaskClicked: () -> Unit,
    onEditTaskClicked: (taskItem: TaskItem) -> Unit,
    onDeleteTaskClicked: (taskItem: TaskItem) -> Unit,
    menuItems: List<MenuItem>,
    menuTask: List<Task>,
    onSelectDayChange: (Int, Int, Int) -> Unit,
    getTasksResponse: ApiResponseWithData<List<TaskItem>>,
) {
    val bottomBarItems = listOf(BottomNavItems.NewTask)
    val weekCalendarState = rememberWeekCalendarState()

    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val temporaryDeleteId by remember { mutableStateOf<TaskItem?>(null) }
    var selectedActivityTag by remember { mutableIntStateOf(ActivityTag.Task.stringId) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val taskSelections by rememberSaveable(stateSaver = snapshotStateMapSaver()) {
        mutableStateOf(SnapshotStateMap<Int, Boolean>())
    }
    val originalCategories by rememberSaveable(stateSaver = snapshotStateMapSaver()) {
        mutableStateOf(SnapshotStateMap<Int, Int>())
    }

    val tasks = remember { mutableStateListOf<Task>() }

    LaunchedEffect(getTasksResponse) {
        if (getTasksResponse is ApiResponseWithData.Success) {
            tasks.clear()
            getTasksResponse.data?.let { taskItems ->
                tasks.addAll(taskItems.mapNotNull { taskItem ->
                    try {
                        Task.fromApi(
                            id = taskItem.id,
                            title = taskItem.name,
                            description = taskItem.description ?: "",
                            category = taskItem.category,
                            date = LocalDate.parse(taskItem.date.substring(0, 10)),
                            type = taskItem.type
                        )
                    } catch (e: Exception) {
                        null
                    }
                })
            }
        }
    }

    val filteredTasks = tasks.filter { task ->
        val matchesType = when (selectedActivityTag) {
            ActivityTag.Task.stringId -> task.type.value == "task"
            ActivityTag.Habit.stringId -> task.type.value == "habit"
            else -> true
        }
        val matchesDate = selectedDate?.let { task.date == it } ?: true
        matchesType && matchesDate
    }

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
                    .padding(initialPadding)
                    .padding(horizontal = 12.dp)
            ) {
                CalendarRoutinely(
                    state = weekCalendarState,
                    onDateSelected = { newDate ->
                        selectedDate = newDate
                        onSelectDayChange(
                            newDate.monthValue,
                            newDate.year,
                            newDate.dayOfMonth
                        )
                    }
                )
                DropdownActivityFilter(
                    modifier = Modifier.padding(top = 9.dp),
                    labelRes = R.string.label_tag_dropdown,
                    onValueChange = { stringId ->
                        selectedActivityTag = stringId
                    },
                    list = listOf(ActivityTag.Task, ActivityTag.Habit),
                    option = selectedActivityTag
                )

                LazyColumn(
                    modifier = Modifier

                ) {
                    items(filteredTasks) { task ->
                        val isSelected = taskSelections[task.id] ?: false
                        val taskType = when (task.type.value) {
                            "task" -> ActivityTag.Task.stringId
                            "habit" -> ActivityTag.Habit.stringId
                            else -> ActivityTag.Task.stringId
                        }
                        CardTask(
                            title = task.title,
                            description = task.description,
                            taskType = taskType,
                            category = task.category.id,
                            isSelected = isSelected,
                            onSelected = { selected ->
                                taskSelections[task.id] = selected
                            }
                        )
                    }
                }
            }

            if (showDeleteDialog) {
                TaskAlertDialog(
                    textRes = R.string.delete_task_confirmation,
                    onConfirm = {
                        showDeleteDialog = false
                        temporaryDeleteId?.let { onDeleteTaskClicked(it) }
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
    )
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
        menuTask = listOf(),
        onSelectDayChange = { _, _, _ -> },
        getTasksResponse = ApiResponseWithData.Default()
    )
}
