package com.example.routinely.task

import TaskNameTextField
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.ui.components.AddTaskButton
import com.example.routinely.ui.components.DatePickerDiag
import com.example.routinely.ui.components.DescriptionTextField
import com.example.routinely.ui.components.DropdownRoutinely
import com.example.routinely.ui.components.TimePickerDiag
import com.example.routinely.ui.theme.PurpleRoutinely
import com.example.routinely.ui.theme.RoutinelyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(navController: NavHostController) {
    var expanded by remember { mutableStateOf(true) }
    // Cria uma estrutura básica para a tela
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
                    containerColor = PurpleRoutinely,
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = PurpleRoutinely,
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(PurpleRoutinely),
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp, end = 30.dp, bottom = 80.dp, top = 80.dp)
            ) {
                // O conteúdo da tela
                Text(
                    color = PurpleRoutinely,
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
                    onDescriptionChange ={}
                )
                AddTaskButton(
                    onAddTaskClick = {}
                )
            }
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
