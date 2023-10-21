package com.routinely.routinely.task

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.AddTaskRequest
import com.routinely.routinely.ui.components.AddTaskButton
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerDiag
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownRoutinely
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.TaskNameTextField
import com.routinely.routinely.ui.components.TimePickerDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.theme.HighPriority
import com.routinely.routinely.ui.theme.LowPriority
import com.routinely.routinely.ui.theme.MediumPriority
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.UrgentPriority
import com.routinely.routinely.util.BottomNavItems
import com.routinely.routinely.util.MenuItem
import com.routinely.routinely.util.validators.DateTimeInputValid
import com.routinely.routinely.util.validators.DescriptionInputValid
import com.routinely.routinely.util.validators.DropdownInputValid
import com.routinely.routinely.util.validators.TaskNameInputValid
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit,
    shouldGoToNextScreen: Boolean,
    taskNameStateValidation: (nameTask: String) -> TaskNameInputValid,
    taskDateStateValidation: (dateTask: String) -> DateTimeInputValid,
    taskTimeStateValidation: (timeTask: String) -> DateTimeInputValid,
    taskDropdownPriorityStateValidation: (priorityTask: String) -> DropdownInputValid,
    taskDropdownTagsStateValidation: (tagTask: String) -> DropdownInputValid,
    taskDropdownCategoryStateValidation: (categoryTask: String) -> DropdownInputValid,
    taskDescriptionStateValidation: (descriptionTask: String) -> DescriptionInputValid,
    apiErrorMessage: List<String>,
    navigateToHomeScreen: () -> Unit,
    onAddTaskClick: (AddTaskRequest) -> Unit,
    menuItems: List<MenuItem>,
) {
    var taskName by rememberSaveable { mutableStateOf("") }
    var taskNameState by rememberSaveable { mutableStateOf<TaskNameInputValid>(TaskNameInputValid.Empty) }
    var taskDate by rememberSaveable { mutableStateOf("") }
    var taskDateState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var taskTime by rememberSaveable { mutableStateOf("") }
    var taskTimeState by rememberSaveable { mutableStateOf<DateTimeInputValid>(DateTimeInputValid.Empty) }
    var dropdownPriority by rememberSaveable { mutableStateOf("") }
    var dropdownPriorityState by rememberSaveable { mutableStateOf<DropdownInputValid>(DropdownInputValid.Empty) }
    var dropdownTags by rememberSaveable { mutableStateOf("") }
    var dropdownTagsState by rememberSaveable { mutableStateOf<DropdownInputValid>(DropdownInputValid.Empty) }
    var dropdownCategory by rememberSaveable { mutableStateOf("") }
    var dropdownCategoryState by rememberSaveable { mutableStateOf<DropdownInputValid>(DropdownInputValid.Empty) }
    var taskDescription by rememberSaveable { mutableStateOf("") }
    var taskDescriptionState by rememberSaveable { mutableStateOf<DescriptionInputValid>(DescriptionInputValid.Empty) }

    val bottomBarItems = listOf(BottomNavItems.Home)
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = { expanded = true },
                onNotificationClick = {  },
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
                top = innerPadding.calculateTopPadding() + 32.dp, // Adicione padding na parte superior
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
                    text = "Adicionar tarefa",
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
                    //modifier = Modifier.fillMaxWidth()
                ) {
                    DatePickerDiag(
                        onValueChange = { newTaskDate: String ->
                            taskDate = newTaskDate
                            taskDateState = taskDateStateValidation(taskDate)
                        },
                        labelRes = "Date",
                        error = taskDateState,
                        modifier = Modifier.weight(1f)
                    )
                    TimePickerDialog(
                        onValueChange = { newTaskTime: String ->
                            taskTime = newTaskTime
                            taskTimeState = taskTimeStateValidation(taskTime)
                        },
                        labelRes = "Hora",
                        error = taskTimeState,
                        modifier = Modifier.weight(1f)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DropdownRoutinely(
                        labelRes = "Prioridade",
                        onValueChange = { newDropDown: String ->
                            dropdownPriority = newDropDown
                            dropdownPriorityState = taskDropdownPriorityStateValidation(dropdownPriority)
                        },
                        list = listOf("Urgente", "Alta", "Média", "Baixa"),
                        optionColors = mapOf(
                            "Baixa" to LowPriority,
                            "Média" to MediumPriority,
                            "Alta" to HighPriority,
                            "Urgente" to UrgentPriority
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        //modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownRoutinely(
                            labelRes = "Categorias",
                            onValueChange = { newDropDown: String ->
                                dropdownCategory = newDropDown
                                dropdownCategoryState = taskDropdownCategoryStateValidation(dropdownCategory)
                            },
                            list = listOf("Pessoal", "Estudos", "Finanças", "Carreira", "Saúde"),
                            modifier = Modifier.weight(1f)
                        )
                        DropdownRoutinely(
                            labelRes = "Tags",
                            onValueChange = { newDropDown: String ->
                                dropdownTags = newDropDown
                                dropdownTagsState = taskDropdownTagsStateValidation(dropdownTags)
                            },
                            list = listOf(
                                "Candidatura",
                                "Conta",
                                "Exercicio",
                                "Beleza",
                                "Literatura"
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    DescriptionTextField(
                        value = taskDescription,
                        onValueChange = { newTaskDescription: String ->
                            taskDescription = newTaskDescription
                            taskDescriptionState = taskDescriptionStateValidation(taskDescription)
                        },
                        labelRes = "Descrição",
                        error = taskDescriptionState,
                    )
                    apiErrorMessage.forEach {
                        LabelError(
                            labelRes = it
                        )
                    }
                    AddTaskButton (
                        {
                            onAddTaskClick(
                                AddTaskRequest(
                                    name = taskName,
                                    date = taskDate,
                                    priority = dropdownPriority,
                                    accountId = "token",
                                    description = taskDescription,
                                    hour = taskTime,
                                    tag = dropdownTags,
                                    category = dropdownCategory
                                )
                            )
                        },
                        areFieldsValid = taskNameState == TaskNameInputValid.Valid &&
                                taskDescriptionState == DescriptionInputValid.Valid &&
                                taskDateState == DateTimeInputValid.Valid &&
                                dropdownPriorityState == DropdownInputValid.Valid &&
                                dropdownCategoryState == DropdownInputValid.Valid &&
                                dropdownTagsState == DropdownInputValid.Valid &&
                                taskTimeState == DateTimeInputValid.Valid
                    )
                }
            }
        },
    )

    LaunchedEffect(key1 = shouldGoToNextScreen) {
        if(shouldGoToNextScreen) {
            delay(3000)
            navigateToHomeScreen()
        }
    }
}
//@Preview(showBackground = false)
//@Composable
//fun AddTaskPreview() {
//    RoutinelyTheme {
//        AddTaskScreen(
//            onBackButtonPressed = { },
//            onHomeButtonPressed = { },
//        )
//    }
//}
