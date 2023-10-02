package com.routinely.routinely.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerDiag
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownRoutinely
import com.routinely.routinely.ui.components.RoutinelyTaskButton
import com.routinely.routinely.ui.components.TaskAlertDialog
import com.routinely.routinely.ui.components.TaskNameTextField
import com.routinely.routinely.ui.components.TimePickerDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.theme.HighPriority
import com.routinely.routinely.ui.theme.LowPriority
import com.routinely.routinely.ui.theme.MediumPriority
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.RedRoutinely
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.ui.theme.UrgentPriority
import com.routinely.routinely.util.BottomNavItems


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    onBackButtonPressed: () -> Unit,
    onMenuClicked: () -> Unit,
    onNotificationClicked: () -> Unit,
    onHomeButtonPressed: () -> Unit,
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
                onBackButtonClicked = { onBackButtonPressed() }
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
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
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
                TaskNameTextField()
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DatePickerDiag(
                        label = "Data",
                        modifier = Modifier.weight(1f)
                    )
                    TimePickerDialog(
                        label = "Hora",
                        modifier = Modifier.weight(1f)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    DropdownRoutinely(
                        label = "Prioridade",
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownRoutinely(
                            label = "Categorias",
                            list = listOf("Pessoal", "Estudos", "Finanças", "Carreira", "Saúde"),
                            modifier = Modifier.weight(1f)
                        )
                        DropdownRoutinely(
                            label = "Tags",
                            list = listOf("Candidatura", "Conta", "Exercicio", "Beleza", "Literatura"),
                            modifier = Modifier.weight(1f)
                        )
                    }
                    DescriptionTextField( )
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
                            modifier = Modifier.weight(1f),
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
                            modifier = Modifier.weight(1f),
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
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    RoutinelyTheme {
        EditTaskScreen(
            onMenuClicked = { },
            onNotificationClicked = { },
            onHomeButtonPressed = { },
            onBackButtonPressed = { }
        )
    }
}
