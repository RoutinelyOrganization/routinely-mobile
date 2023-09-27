package com.example.routinely.task

import com.example.routinely.ui.components.TaskNameTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.login.showToast
import com.example.routinely.ui.components.AddTaskButton
import com.example.routinely.ui.components.CustomTimePicker
import com.example.routinely.ui.components.DatePickerDiag
import com.example.routinely.ui.components.DescriptionTextField
import com.example.routinely.ui.components.DropdownRoutinely
import com.example.routinely.ui.components.TimePickerDiag
import com.example.routinely.ui.components.TimePickerDialog
import com.example.routinely.ui.components.TimeTextField
import com.example.routinely.ui.theme.BlueRoutinely
import com.example.routinely.ui.theme.Gray80
import com.example.routinely.ui.theme.RoutinelyTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }
    var isDialogOpen by remember { mutableStateOf(false) }

    // Função para abrir o diálogo
    val openDialog = { isDialogOpen = true }
    val focusManager = LocalFocusManager.current
    // Função para fechar o diálogo
    val closeDialog = { isDialogOpen = false }
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    // Variáveis para rastrear a hora e os minutos selecionados

    // Função para atualizar o valor do campo de texto
    val updateTextFieldValue: () -> Unit = {
        textFieldValue = TextFieldValue(
            text = String.format("%02d:%02d", selectedHour, selectedMinute)
        )
    }
    Scaffold(
        modifier = Modifier.padding(PaddingValues(all = 0.dp)),
        topBar = {
            // Adiciona uma barra no topo
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center, // Centraliza horizontalmente
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.topbar_horizontal),
                            contentDescription = "topbar_horizontal",
                            modifier = Modifier
                                .size(height = 26.dp, width = 72.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.menu),
                            contentDescription = "Ícone à Direita",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.voltar),
                        contentDescription = "Voltar",
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // Ícone de menu à direita
                        Image(
                            painter = painterResource(R.drawable.menu),
                            contentDescription = "Ícone à Direita",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = BlueRoutinely,
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueRoutinely,
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(BlueRoutinely),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(),
                    onClick = { }
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                            painter = painterResource(R.drawable.home),
                            contentDescription = "Home",
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text("Início")
                    }
                }
            }
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
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
                        list = listOf("Urgente", "Alta", "Média", "Baixa")
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
        AddTask(rememberNavController())
    }
}
