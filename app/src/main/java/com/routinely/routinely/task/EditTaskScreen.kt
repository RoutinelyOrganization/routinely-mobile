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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.ConfirmTaskAlertDialog
import com.routinely.routinely.ui.components.DatePickerDialogRoutinely
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownActivityFilter
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
import com.routinely.routinely.util.TaskCategoryNew
import com.routinely.routinely.util.TaskItem
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
    initialTask: TaskItem,
    onSaveChanges: (Int, TaskRequest) -> Unit,
    onDeleteTask: (taskId: Int) -> Unit,
    onDuplicateTask: (taskId: Int) -> Boolean
) {
    val bottomBarItems = listOf(BottomNavItems.Home)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDuplicateDialog by rememberSaveable { mutableStateOf(false) }
    var showLimitDuplicateDialog by rememberSaveable { mutableStateOf(false) }
    var showConfirmChangesDialog by rememberSaveable { mutableStateOf(false) }

    var taskName by rememberSaveable { mutableStateOf(initialTask.name) }
    var taskNameState by rememberSaveable { mutableStateOf<TaskNameInputValid>(TaskNameInputValid.Empty) }

    var taskDate by rememberSaveable { mutableStateOf(initialTask.date) }
    var taskDateState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }

    var taskTime by rememberSaveable { mutableStateOf("") }
    var taskTimeState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }

    var dropdownPriorityState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }

    val dropdownTags by rememberSaveable { mutableStateOf(initialTask.type) }
    var dropdownTagsState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }

    var dropdownCategory by rememberSaveable { mutableStateOf(initialTask.category) }
    var dropdownCategoryState by rememberSaveable {
        mutableStateOf<DropdownInputValid>(
            DropdownInputValid.Empty
        )
    }

    var taskDescription by rememberSaveable { mutableStateOf(initialTask.description ?: "") }
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

    initialTask.let {

        taskNameState = taskNameStateValidation(it.name)
        taskDateState = taskDateStateValidation(it.date)

        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val dateTime = LocalDateTime.parse(initialTask.date, dateTimeFormatter)

        val hour = dateTime.hour.toString()
        var minute = dateTime.minute.toString()
        if(minute.length == 1) {
            minute = "0$minute"
        }
        taskTime = "${hour}:${minute}"
        taskTimeState = taskTimeStateValidation("${hour}:${minute}")

        dropdownPriorityState = DropdownInputValid.Valid

        dropdownTagsState = DropdownInputValid.Valid

        dropdownCategoryState = DropdownInputValid.Valid

        taskDescriptionState = taskDescriptionStateValidation(it.description ?: "")

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
                        value = taskDate
                    )
                    TimePickerDialog(
                        onValueChange = { newTaskTime: String ->
                            taskTimeState = taskTimeStateValidation(newTaskTime)
                            taskTime = newTaskTime
                            hasChanges = true
                        },
                        labelRes = stringResource(id = R.string.label_time_picker),
                        error = taskTimeState,
                        modifier = Modifier.weight(1f),
                        time = taskTime,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownActivityFilter(
                            labelRes = R.string.label_category_dropdown,
                            onValueChange = { stringId ->
                                dropdownCategory = TaskCategory.fromId(stringId).toString()
                                dropdownCategoryState = DropdownInputValid.Valid
                                hasChanges = true
                            },
                            list = TaskCategory.toTaskFieldsList(),
                            modifier = Modifier.weight(1f),
                            option = TaskCategory.fromString(dropdownCategory).id
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
                            showConfirmChangesDialog = true
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

                        if (showConfirmChangesDialog) {
                            TaskAlertDialog(
                                textRes = R.string.save_changes_confirm,
                                onConfirm = {
                                    showConfirmChangesDialog = false
                                    onSaveChanges(
                                        taskId,
                                        TaskRequest(
                                            name = taskName,
                                            description = taskDescription,
                                            date = "$taskDate $taskTime",
                                            category = dropdownCategory,
                                            finallyDate = "$taskDate $taskTime",
                                            weekDays = listOf("Monday"),
                                            type = dropdownTags,
                                        )
                                    )
                                },
                                onCancel = {
                                    showConfirmChangesDialog = false
                                },
                                onDismissRequest = {
                                    showConfirmChangesDialog = false
                                }
                            )
                        }

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
                                    if(!onDuplicateTask(initialTask.id)){
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditTaskScreenPreview() {
    val initialTask = TaskItem(
        id = 1,
        name = "Tarefa de Exemplo",
        date = "2025-04-01 14:30",
        type = "habit",
        category = TaskCategoryNew.Career.toString(),
        description = "Descrição da tarefa",
        finallyDate = "2024-04-01 15:30",
        weekDays = listOf("Monday", "Wednesday")
    )

    EditTaskScreen(
        onBackButtonPressed = {  },
        onNotificationClicked = {  },
        onHomeButtonPressed = {  },
        taskNameStateValidation = { TaskNameInputValid.Valid },
        taskDateStateValidation = { DateTimeInputValid.Valid },
        taskTimeStateValidation = { DateTimeInputValid.Valid },
        taskDescriptionStateValidation = { DescriptionInputValid.Valid },
        menuItems = listOf(
            MenuItem("Opção 1", { /* Nada a fazer */ }),
            MenuItem("Opção 2", { /* Nada a fazer */ })
        ),
        editTaskResult = ApiResponse.Empty,
        initialTask = initialTask,
        onSaveChanges = { _, _ -> /* Nada a fazer */ },
        onDeleteTask = { /* Nada a fazer */ },
        onDuplicateTask = { true }
    )
}

