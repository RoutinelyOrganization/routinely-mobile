package com.routinely.routinely.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.TaskRequest
import com.routinely.routinely.ui.components.AddTaskButton
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerDialogRoutinely
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownActivityFilter
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.TaskNameTextField
import com.routinely.routinely.ui.components.TimePickerDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.TaskCategory
import com.routinely.routinely.util.TaskFields
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.DropdownInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit,
    taskNameStateValidation: (nameTask: String) -> TaskNameInputValid,
    taskDateStateValidation: (dateTask: String) -> DateTimeInputValid,
    taskTimeStateValidation: (timeTask: String) -> DateTimeInputValid,
    taskDescriptionStateValidation: (descriptionTask: String) -> DescriptionInputValid,
    navigateToHomeScreen: () -> Unit,
    onAddTaskClick: (TaskRequest) -> Unit,
    menuItems: List<MenuItem>,
    addTaskResult: ApiResponse,
) {
    var taskName by rememberSaveable { mutableStateOf("") }
    var taskNameState by rememberSaveable { mutableStateOf<TaskNameInputValid>(TaskNameInputValid.Empty) }
    var taskDate by rememberSaveable { mutableStateOf("") }
    var taskDateState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var taskTime by rememberSaveable { mutableStateOf("") }
    var taskTimeState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var selectedWeekDays by rememberSaveable { mutableStateOf<List<String>>(emptyList()) }

    var dropdownTags by rememberSaveable { mutableStateOf<ActivityTag?>(null) }
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

    var apiErrorMessage by rememberSaveable { mutableIntStateOf(0) }
    var showApiErrors by rememberSaveable { mutableStateOf(false) }
    val bottomBarItems = listOf(BottomNavItems.Home)
    var expanded by remember { mutableStateOf(false) }
    var showLoading by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = { expanded = true },
                onNotificationClick = { },
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
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    color = PurpleRoutinely,
                    text = stringResource(R.string.title_add_task),
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
                    },
                    labelRes = stringResource(id = R.string.name),
                    error = taskNameState,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    DatePickerDialogRoutinely(
                        onValueChange = { newTaskDate: String ->
                            taskDate = newTaskDate
                            taskDateState = taskDateStateValidation(taskDate)
                        },
                        labelRes = stringResource(id = R.string.label_date_picker),
                        error = taskDateState,
                        modifier = Modifier.weight(1f)
                    )
                    TimePickerDialog(
                        onValueChange = { newTaskTime: String ->
                            taskTime = newTaskTime
                            taskTimeState = taskTimeStateValidation(taskTime)
                        },
                        labelRes = stringResource(id = R.string.label_time_picker),
                        error = taskTimeState,
                        modifier = Modifier.weight(1f)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        DropdownActivityFilter(
                            labelRes = R.string.label_category_dropdown,
                            onValueChange = { stringId ->
                                dropdownCategory = TaskCategory.fromId(stringId)
                                dropdownCategoryState = DropdownInputValid.Valid
                            },
                            list = TaskCategory.entries.map { category: TaskCategory ->
                                object : TaskFields(category.id, category.name) {}
                            },
                            modifier = Modifier.weight(1f),
                            option = dropdownCategory?.id
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        DropdownActivityFilter(
                            labelRes = R.string.label_tag_dropdown,
                            onValueChange = { stringId ->
                                dropdownTags = ActivityTag.fromId(stringId)
                                dropdownTagsState = DropdownInputValid.Valid
                            },
                            list = listOf(ActivityTag.Task, ActivityTag.Habit),
                            modifier = Modifier.weight(1f),
                            option = dropdownTags?.stringId
                        )
                    }

                    DescriptionTextField(
                        value = taskDescription,
                        onValueChange = { newTaskDescription: String ->
                            taskDescription = newTaskDescription
                            taskDescriptionState = taskDescriptionStateValidation(taskDescription)
                        },
                        labelRes = stringResource(id = R.string.label_task_description),
                        error = taskDescriptionState,
                    )

                    DropdownWeeklyFrequencyScreen(
                        onWeekDaysSelected = { days ->
                            selectedWeekDays = days
                        }
                    )

                    if (showApiErrors) {
                        LabelError(stringResource(apiErrorMessage))
                    }
                    AddTaskButton(
                        {
                            val formattedDate = if (taskDate.isNotEmpty() && taskTime.isNotEmpty()) {
                                "$taskDate $taskTime"
                            } else {
                                ""
                            }

                            val category = dropdownCategory?.apiName ?: "Several"
                            val type = dropdownTags?.apiString?.lowercase() ?: "task"
                            val weekDays = if (selectedWeekDays.isEmpty()) 
                                listOf("Monday") 
                            else 
                                selectedWeekDays.map { it.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } }

                            onAddTaskClick(
                                TaskRequest(
                                    name = taskName.trim(),
                                    description = taskDescription.trim(),
                                    date = formattedDate,
                                    category = category,
                                    finallyDate = formattedDate,
                                    weekDays = weekDays,
                                    type = type,
                                )
                            )
                        },
                        areFieldsValid = taskNameState == TaskNameInputValid.Valid &&
                                taskDescriptionState == DescriptionInputValid.Valid &&
                                taskDateState == DateTimeInputValid.Valid &&
                                taskTimeState == DateTimeInputValid.Valid &&
                                dropdownCategory != null &&
                                dropdownTags != null &&
                                selectedWeekDays.isNotEmpty()
                    )
                }
            }
        },
    )
    LaunchedEffect(key1 = addTaskResult) {
        when (addTaskResult) {
            is ApiResponse.Success -> {
                showApiErrors = false
                showLoading = false
                navigateToHomeScreen()
            }

            is ApiResponse.Error -> {
                apiErrorMessage = addTaskResult.message
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


@Preview(showBackground = true)
@Composable
fun PreviewAddTaskScreen() {
    AddTaskScreen(
        onBackButtonPressed = { },
        onHomeButtonPressed = { },
        taskNameStateValidation = { nameTask ->
            TaskNameInputValid.Valid
        },
        taskDateStateValidation = { dateTask ->
            DateTimeInputValid.Valid
        },
        taskTimeStateValidation = { timeTask ->
            DateTimeInputValid.Valid
        },
        taskDescriptionStateValidation = { descriptionTask ->
            DescriptionInputValid.Valid
        },
        navigateToHomeScreen = { },
        onAddTaskClick = { taskRequest ->
        },
        menuItems = listOf(
        ),
        addTaskResult = ApiResponse.Success
    )
}


