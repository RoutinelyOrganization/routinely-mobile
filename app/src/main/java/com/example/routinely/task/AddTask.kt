package com.example.routinely.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.login.showToast
import com.example.routinely.ui.components.AddTaskButton
import com.example.routinely.ui.components.BottomAppBarRoutinely
import com.example.routinely.ui.components.DatePickerDiag
import com.example.routinely.ui.components.DescriptionTextField
import com.example.routinely.ui.components.DropdownRoutinely
import com.example.routinely.ui.components.TaskNameTextField
import com.example.routinely.ui.components.TimeTextField
import com.example.routinely.ui.components.TopAppBarRoutinely
import com.example.routinely.ui.theme.BlueRoutinely
import com.example.routinely.ui.theme.HighPriority
import com.example.routinely.ui.theme.LowPriority
import com.example.routinely.ui.theme.MediumPriority
import com.example.routinely.ui.theme.RoutinelyTheme
import com.example.routinely.ui.theme.UrgentPriority
import com.example.routinely.util.BottomNavItems
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    //var selectedHour by remember { mutableStateOf(0) }
    //var selectedMinute by remember { mutableStateOf(0) }
    //var isDialogOpen by remember { mutableStateOf(false) }

    // Função para abrir o diálogo
    //val openDialog = { isDialogOpen = true }
    //val focusManager = LocalFocusManager.current
    // Função para fechar o diálogo
    //val closeDialog = { isDialogOpen = false }
    //var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    // Variáveis para rastrear a hora e os minutos selecionados

    // Função para atualizar o valor do campo de texto
    // val updateTextFieldValue: () -> Unit = {
    //     textFieldValue = TextFieldValue(
    //         text = String.format("%02d:%02d", selectedHour, selectedMinute)
    //     )
    // }
    val bottomBarItems = listOf(BottomNavItems.Home)


    Scaffold(
        topBar = {
            TopAppBarRoutinely(
                onMenuClick = {  },
                onNotificationClick = {  },
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
        content = { padding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, end = 30.dp, bottom = 80.dp, top = 80.dp)
            ) {
                // O conteúdo da tela
                Text(
                    color = BlueRoutinely,
                    text = "Adicionar tarefa",
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
                    TimeTextField(
                        onTimeChange = {},
                        modifier = Modifier.weight(1f)
                    )/*
                        TimePickerDiag(
                            label = "Hora",
                            modifier = Modifier.weight(1f)
                        )*/
                }


                /*
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = {
                            // Aplicar a validação antes de atualizar o valor do campo
                            textFieldValue = it
                        },
                        label = {
                            Text(
                                text = "Hora CustomDatePicker",
                                style = TextStyle(color = Color.Black)
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedTextColor = Gray80,
                            unfocusedTextColor = Gray80,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusEvent { state ->
                                if (state.isFocused) {
                                    openDialog()
                                    focusManager.clearFocus()
                                }
                            },
                    )

                    if (isDialogOpen) {
                        TimePickerDialog(
                            selectedHour = selectedHour,
                            selectedMinute = selectedMinute,
                            onTimeSelected = { hour, minute ->
                                selectedHour = hour
                                selectedMinute = minute
                                updateTextFieldValue() // Atualizar o valor do campo de texto
                            },
                            onDismiss = {
                                closeDialog()
                                updateTextFieldValue() // Atualizar o valor do campo de texto quando o diálogo for fechado
                            }
                        )
                    }*/

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
                    DropdownRoutinely(
                        label = "Categorias",
                        list = listOf("Pessoal", "Estudos", "Finanças", "Carreira", "Saúde")
                    )
                    DropdownRoutinely(
                        label = "Tags",
                        list = listOf("Candidatura", "Conta", "Exercicio", "Beleza", "Literatura")
                    )
                    DescriptionTextField(
                        onDescriptionChange = {}
                    )
                    AddTaskButton(
                        onAddTaskClick = {
                            coroutineScope.launch {
                                showToast(context, "Falta adicionar integração com API")
                            }
                        }
                    )
                }
            }
            //}
        },
    )
}
@Preview(showBackground = false)
@Composable
fun AddTaskPreview() {
    RoutinelyTheme {
        AddTask(onBackButtonPressed = { }, onHomeButtonPressed = { })
    }
}
