package com.example.routinely.task

import TaskNameTextField
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.routinely.R
import com.example.routinely.ui.components.BottomAppBarRoutinely
import com.example.routinely.ui.components.DatePickerDiag
import com.example.routinely.ui.components.DescriptionTextField
import com.example.routinely.ui.components.DropdownRoutinely
import com.example.routinely.ui.components.RoutinelyTaskButton
import com.example.routinely.ui.components.TaskAlertDialog
import com.example.routinely.ui.components.TimePickerDiag
import com.example.routinely.ui.components.TopAppBarRoutinely
import com.example.routinely.ui.theme.PurpleRoutinely
import com.example.routinely.ui.theme.RedRoutinely
import com.example.routinely.ui.theme.RoutinelyTheme
import com.example.routinely.util.BottomNavItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navController: NavHostController,
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val bottomBarItems = listOf(BottomNavItems.Home)
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDuplicateDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = { onMenuClicked() },
                onNotificationClick = { onNotificationClicked() },
                showBackButton = true,
                onBackButtonClicked = { }
            )
        },
        bottomBar = {
            BottomAppBarRoutinely(
                bottomBarItems = bottomBarItems,
                onClick = { navigateToHome() },
            )
        },
        content = { padding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, end = 30.dp, bottom = 80.dp, top = 80.dp)
            ) {
                // O conteúdo da tela
                Text(
                    color = PurpleRoutinely,
                    text = "Editar tarefa",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                TaskNameTextField(
                    onTaskNameChange = {

                    }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DatePickerDiag(
                        label = "Data",
                        modifier = Modifier.weight(1f)
                    )
                    TimePickerDiag(
                        label = "Hora",
                        modifier = Modifier.weight(1f)
                    )
                }
                DropdownRoutinely(
                    label = "Prioridade",
                    list = listOf("Baixa", "Alta", "Muito alta")
                )
                DropdownRoutinely(
                    label = "Categorias",
                    list = listOf("Baixa", "Alta", "Muito alta")
                )
                DropdownRoutinely(
                    label = "Tags",
                    list = listOf("Baixa", "Alta", "Muito alta")
                )
                DescriptionTextField(
                    onDescriptionChange = {}
                )
                RoutinelyTaskButton(
                    textRes = R.string.save_changes,
                    textColor = Color.White,
                    buttonColor = ButtonDefaults.buttonColors(PurpleRoutinely),
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth(),
                    borderStroke = BorderStroke(1.dp, PurpleRoutinely)
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
                        modifier = Modifier.width(160.dp),
                        borderStroke = BorderStroke(1.dp, Color.Gray)
                    )

                    if (showDialog) {
                        TaskAlertDialog(
                            textRes = R.string.delete_task_confirmation,
                            onConfirm = {

                                showDialog = false
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
                        modifier = Modifier.width(160.dp),
                        borderStroke = BorderStroke(1.dp, Color.Gray)
                    )

                    if (showDuplicateDialog) {
                        TaskAlertDialog(
                            textRes = R.string.duplicate_task_confirmation,
                            onConfirm = {

                                showDuplicateDialog = false
                            },
                            onCancel = {
                                showDuplicateDialog = false
                            },
                            onDismissRequest = {
                                showDuplicateDialog = false
                            }
                        )
                    }
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    RoutinelyTheme {
        EditTaskScreen(
            navController = NavHostController(LocalContext.current),
            onMenuClicked = { },
            onNotificationClicked = { },
            navigateToHome = { },
        )
    }
}
