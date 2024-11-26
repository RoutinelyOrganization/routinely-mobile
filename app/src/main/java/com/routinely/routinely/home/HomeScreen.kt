package com.routinely.routinely.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponseWithData
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.CalendarRoutinely
import com.routinely.routinely.ui.components.CardTask
import com.routinely.routinely.ui.components.DropdownTaskFilter
import com.routinely.routinely.ui.components.Task
import com.routinely.routinely.ui.components.TaskAlertDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskFields
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.toStringId

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
    viewModel: HomeViewModel = viewModel(),
    getTasksResponse: ApiResponseWithData<List<TaskItem>>,
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val selectedActivityTag by viewModel.selectedActivityTag.collectAsStateWithLifecycle()
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val taskSelections by viewModel.taskSelections.collectAsStateWithLifecycle()
    val originalCategories by viewModel.originalCategories.collectAsStateWithLifecycle()
    val activityTags = TaskFields.getAllOptions<ActivityTag>()

    val bottomBarItems = listOf(BottomNavItems.NewTask)
    val weekCalendarState = rememberWeekCalendarState()

    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    val temporaryDeleteId by remember { mutableStateOf<TaskItem?>(null) }
    /*var selectedActivityTag by remember { mutableIntStateOf(ActivityTag.AllActivity.stringId) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    val taskSelections by rememberSaveable(stateSaver = snapshotStateMapSaver()) {
        mutableStateOf(SnapshotStateMap<Int, Boolean>())
    }
    val originalCategories by rememberSaveable(stateSaver = snapshotStateMapSaver()) {
        mutableStateOf(SnapshotStateMap<Int, Int>())
    }

    val tasks = remember { mutableStateListOf<Task>().apply { addAll(menuTask) } }
    menuTask.forEach { task ->
        if (!taskSelections.containsKey(task.id)) {
            taskSelections[task.id] = task.isSelected
        }
        if (!originalCategories.containsKey(task.id)) {
            originalCategories[task.id] = task.category
        }
    }*/
    /*  val filteredTasks = menuTask.filter { task ->
        val matchesCategory = when (selectedActivityTag) {
            ActivityTag.AllActivity.stringId -> task.category != ActivityTag.Completed.stringId
            ActivityTag.Completed.stringId -> task.category == ActivityTag.Completed.stringId
            else -> task.category == selectedActivityTag
        }
        val matchesDate = selectedDate?.let { task.date == it } ?: true
        matchesCategory && matchesDate
    }*/

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
                bottomBarItems = listOf(BottomNavItems.NewTask),
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
                        viewModel.onDateSelected(newDate)
                    }
                )
                DropdownTaskFilter(
                    modifier = Modifier.padding(top = 9.dp),
                    labelRes = selectedActivityTag.toStringId(),
                    onValueChange = { newLabelRes ->
                        viewModel.onActivityTagChanged(newLabelRes)
                    },
                    list = activityTags,
                    option = selectedActivityTag.stringId
                )

                val filteredTasks = tasks.filter { task ->
                    val matchesCategory = when (selectedActivityTag) {
                        else -> task.categoryTask == selectedActivityTag.stringId
                    }
                    val matchesDate = selectedDate?.let { task.date == it } ?: true
                    matchesCategory && matchesDate
                }

                if (filteredTasks.isEmpty()) {
                    Text(
                        text = "Você ainda não tem atividades para hoje",
                        modifier = Modifier.padding(top = 32.dp),
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                LazyColumn(
                    modifier = Modifier
                ) {
                    items(filteredTasks) { task ->
                        val isSelected = taskSelections[task.id] ?: false
                        val originalCategory = originalCategories[task.id] ?: task.categoryTask

                        val shouldDisplay = when (selectedActivityTag) {

                            else -> task.categoryTask == selectedActivityTag.stringId
                        }

                        if (shouldDisplay) {
                            CardTask(
                                activityTag = ActivityTag.Task.stringId,
                                description = task.description,
                                categoryTask = task.categoryTask,
                                isSelected = isSelected,
                                onSelected = { selected ->
                                    viewModel.onTaskSelected(task.id, selected)
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
        getTasksResponse = ApiResponseWithData.Default(),
        viewModel = viewModel()
    )
}
