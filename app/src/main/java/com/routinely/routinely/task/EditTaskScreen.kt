package com.routinely.routinely.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.ConfirmTaskAlertDialog
import com.routinely.routinely.ui.components.DatePickerDialogRoutinely
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownRoutinely
import com.routinely.routinely.ui.components.DropdownRoutinelyPriorities
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.RoutinelyTaskButton
import com.routinely.routinely.ui.components.TaskAlertDialog
import com.routinely.routinely.ui.components.TaskNameTextField
import com.routinely.routinely.ui.components.TimePickerDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.RedRoutinely
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskFields
import com.routinely.routinely.util.TaskItem
import com.routinely.routinely.util.TaskPriorities
import com.routinely.routinely.util.TaskTag
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.DropdownInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    onBackButtonPressed: () -> Unit,
    onNotificationClicked: () -> Unit,
    onHomeButtonPressed: () -> Unit,
    taskNameStateValidation: (nameTask: String) -> TaskNameInputValid,
    taskDateStateValidation: (dateTask: String) -> DateTimeInputValid,
    taskTimeStateValidation: (timeTask: String) -> DateTimeInputValid,
    taskDescriptionStateValidation: (descriptionTask: String) -> DescriptionInputValid,
    menuItems: List<MenuItem>,
    editTaskResult: ApiResponse,
    task: TaskItem,
    onSaveChanges: (Int, TaskRequest) -> Unit,
    onDeleteTask: (taskId: Int) -> Unit,
    onDuplicateTask: (taskId: Int) -> Boolean
) {
    val bottomBarItems = listOf(BottomNavItems.Home)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDuplicateDialog by rememberSaveable { mutableStateOf(false) }
    var showLimitDuplicateDialog by rememberSaveable { mutableStateOf(false) }

    var taskName by rememberSaveable { mutableStateOf("") }
    var taskNameState by rememberSaveable { mutableStateOf<TaskNameInputValid>(TaskNameInputValid.Empty) }
    var taskDate by rememberSaveable { mutableStateOf("") }
    var taskDateState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var taskTime by rememberSaveable { mutableStateOf("") }
    var taskTimeState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var dropdownPriority by rememberSaveable { mutableStateOf<TaskPriorities?>(null) }
    var dropdownPriorityState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }
    var dropdownTags by rememberSaveable { mutableStateOf<TaskTag?>(null) }
    var dropdownTagsState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }
    var dropdownCategory by rememberSaveable { mutableStateOf<TaskCategory?>(null) }
    var dropdownCategoryState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    var taskDescriptionState by rememberSaveable {
        mutableStateOf<DescriptionInputValid>(
            DescriptionInputValid.Empty
        )
    }

    var expanded by remember { mutableStateOf(false) }
    var apiErrorMessage by rememberSaveable { mutableIntStateOf(0) }
    var showApiErrors by rememberSaveable { mutableStateOf(false) }
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var hasChanges by remember { mutableStateOf(false) }
    var taskId by remember { mutableStateOf(0) }

    val hourFormatter = DateTimeFormatter.ISO_DATE_TIME

    task.let {
        taskName = it.name
        taskNameState = taskNameStateValidation(it.name)

        taskDate = it.date
        taskDateState = taskDateStateValidation(it.date)

        val dateTime = LocalDateTime.parse(it.hour, hourFormatter)
        val hour = dateTime.hour.toString()
        var minute = dateTime.minute.toString()
        if(minute.length == 1) {
            minute = "0$minute"
        }
        taskTime = "${hour}:${minute}"
        taskTimeState = taskTimeStateValidation("${hour}:${minute}")

        dropdownPriority = it.priority
        dropdownPriorityState = DropdownInputValid.Valid

        dropdownTags = it.tag
        dropdownTagsState = DropdownInputValid.Valid

        dropdownCategory = it.category
        dropdownCategoryState = DropdownInputValid.Valid

        taskDescription = it.description
        taskDescriptionState = taskDescriptionStateValidation(it.description)

        taskId = it.id
    }




    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = { expanded = true },
                onNotificationClick = { onNotificationClicked() },
                showBackButton = true,
                onBackButtonClicked = { onBackButtonPressed() },
                onDismissMenu = { expanded = false },
                expanded = expanded,
                menuItems = menuItems,
            )
        },
        bottomBar = {
            BottomAppBarRoutinely(
                bottomBarItems = bottomBarItems,
                onClick = { onHomeButtonPressed() },
            )
        },
        content = { innerPadding ->
            val contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 32.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = innerPadding.calculateBottomPadding()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    color = PurpleRoutinely,
                    text = stringResource(id = R.string.title_edit_task),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TaskNameTextField(
                    value = taskName,
                    onValueChange = { newTaskName: String ->
                        taskName = newTaskName
                        taskNameState = taskNameStateValidation(taskName)
                        hasChanges = true
                    },
                    labelRes = stringResource(id = R.string.name),
                    error = taskNameState,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DatePickerDialogRoutinely(
                        onValueChange = { newTaskDate: String ->
                            taskDate = newTaskDate
                            taskDateState = taskDateStateValidation(taskDate)
                            hasChanges = true
                        },
                        labelRes = stringResource(id = R.string.label_date_picker),
                        error = taskDateState,
                        modifier = Modifier.weight(1f),
                        value = task.date
                    )
                    TimePickerDialog(
                        onValueChange = { newTaskTime: String ->
                            taskTime = newTaskTime
                            taskTimeState = taskTimeStateValidation(taskTime)
                            hasChanges = true
                        },
                        labelRes = stringResource(id = R.string.label_time_picker),
                        error = taskTimeState,
                        modifier = Modifier.weight(1f),
                        time = task.hour,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    DropdownRoutinelyPriorities(
                        labelRes = R.string.label_priority_dropdown,
                        onValueChange = { stringId ->
                            dropdownPriority =
                                TaskFields.getTaskFieldByStringId<TaskPriorities>(stringId = stringId)
                            dropdownPriorityState = DropdownInputValid.Valid
                            hasChanges = true
                        },
                        list = TaskPriorities.getAllTaskPriorities(),
                        option = task.priority.stringId
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownRoutinely(
                            labelRes = R.string.label_category_dropdown,
                            onValueChange = { stringId ->
                                dropdownCategory =
                                    TaskFields.getTaskFieldByStringId<TaskCategory>(stringId = stringId)
                                dropdownCategoryState = DropdownInputValid.Valid
                                hasChanges = true
                            },
                            list = TaskFields.getAllOptions<TaskCategory>(),
                            modifier = Modifier.weight(1f),
                            option = task.category.stringId
                        )
                        DropdownRoutinely(
                            labelRes = R.string.label_tag_dropdown,
                            onValueChange = { stringId ->
                                dropdownTags =
                                    TaskFields.getTaskFieldByStringId<TaskTag>(stringId = stringId)
                                dropdownTagsState = DropdownInputValid.Valid
                                hasChanges = true
                            },
                            list = TaskFields.getAllOptions<TaskTag>(),
                            modifier = Modifier.weight(1f),
                            option = task.tag.stringId
                        )
                    }
                    DescriptionTextField(
                        value = taskDescription,
                        onValueChange = { newTaskDescription: String ->
                            taskDescription = newTaskDescription
                            taskDescriptionState = taskDescriptionStateValidation(taskDescription)
                            hasChanges = true
                        },
                        labelRes = stringResource(id = R.string.label_task_description),
                        error = taskDescriptionState,
                    )
                    RoutinelyTaskButton(
                        textRes = R.string.save_changes,
                        textColor = Color.White,
                        buttonColor = ButtonDefaults.buttonColors(PurpleRoutinely),
                        onClick = {
                            onSaveChanges(
                                taskId,
                                TaskRequest(
                                    name = taskName,
                                    date = taskDate,
                                    priority = dropdownPriority!!.apiString,
                                    accountId = "",
                                    description = taskDescription,
                                    hour = taskTime,
                                    tag = dropdownTags!!.apiString,
                                    category = dropdownCategory!!.apiString
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        borderStroke = BorderStroke(1.dp, PurpleRoutinely),
                        enabled = taskNameState == TaskNameInputValid.Valid &&
                                taskDescriptionState == DescriptionInputValid.Valid &&
                                taskDateState == DateTimeInputValid.Valid &&
                                dropdownPriorityState == DropdownInputValid.Valid &&
                                dropdownCategoryState == DropdownInputValid.Valid &&
                                dropdownTagsState == DropdownInputValid.Valid &&
                                taskTimeState == DateTimeInputValid.Valid &&
                                hasChanges
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        RoutinelyTaskButton(
                            textRes = R.string.delete_task,
                            textColor = RedRoutinely,
                            buttonColor = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = { showDialog = true },
                            modifier = Modifier.weight(1f),
                            borderStroke = BorderStroke(1.dp, Color.Gray),
                            enabled = true
                        )

                        if (showDialog) {
                            TaskAlertDialog(
                                textRes = R.string.delete_task_confirmation,
                                onConfirm = {
                                    showDialog = false
                                    onDeleteTask(taskId)
                                },
                                onCancel = {
                                    showDialog = false
                                },
                                onDismissRequest = {
                                    showDialog = false
                                }
                            )
                        }

                        RoutinelyTaskButton(
                            textRes = R.string.duplicate_task,
                            textColor = PurpleRoutinely,
                            buttonColor = ButtonDefaults.outlinedButtonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = { showDuplicateDialog = true },
                            modifier = Modifier.weight(1f),
                            borderStroke = BorderStroke(1.dp, Color.Gray),
                            enabled = true
                        )

                        if (showDuplicateDialog) {
                            TaskAlertDialog(
                                textRes = R.string.duplicate_task_confirmation,
                                onConfirm = {
                                    showDuplicateDialog = false
                                    if(!onDuplicateTask(task.id)){
                                        showLimitDuplicateDialog = true
                                    }
                                },
                                onCancel = {
                                    showDuplicateDialog = false
                                },
                                onDismissRequest = {
                                    showDuplicateDialog = false
                                }
                            )
                        }

                        if(showLimitDuplicateDialog) {
                            ConfirmTaskAlertDialog(
                                textRes = R.string.duplicate_task_limit_reached,
                                onConfirm = {
                                    showLimitDuplicateDialog = false
                                }
                            )
                        }
                    }
                }
            }
        },
    )
    LaunchedEffect(key1 = editTaskResult) {
        when (editTaskResult) {
            is ApiResponse.Success -> {
                showApiErrors = false
                showLoading = false
                onHomeButtonPressed()
            }

            is ApiResponse.Error -> {
                apiErrorMessage = editTaskResult.message
                showApiErrors = true
                showLoading = false
            }

            is ApiResponse.DefaultError -> {
                apiErrorMessage = R.string.api_unexpected_error
                showApiErrors = true
                showLoading = false
            }

            is ApiResponse.Loading -> {
                showLoading = true
                showApiErrors = false
            }

            else -> Unit
        }
    }

    if (showLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            IndeterminateCircularIndicator()
        }
    }
}
