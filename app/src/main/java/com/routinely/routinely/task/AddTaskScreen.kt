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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.login.showToast
import com.routinely.routinely.ui.components.AddTaskButton
import com.routinely.routinely.ui.components.BottomAppBarRoutinely
import com.routinely.routinely.ui.components.DatePickerDiag
import com.routinely.routinely.ui.components.DescriptionTextField
import com.routinely.routinely.ui.components.DropdownRoutinely
import com.routinely.routinely.ui.components.TaskNameTextField
import com.routinely.routinely.ui.components.TimePickerDialog
import com.routinely.routinely.ui.components.TopAppBarRoutinely
import com.routinely.routinely.ui.theme.HighPriority
import com.routinely.routinely.ui.theme.LowPriority
import com.routinely.routinely.ui.theme.MediumPriority
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.ui.theme.UrgentPriority
import com.routinely.routinely.util.BottomNavItems
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onBackButtonPressed: () -> Unit,
    onHomeButtonPressed: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
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
                TaskNameTextField()

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    //modifier = Modifier.fillMaxWidth()
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
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        //modifier = Modifier.fillMaxWidth()
                    ) {
                        DropdownRoutinely(
                            label = "Categorias",
                            list = listOf("Pessoal", "Estudos", "Finanças", "Carreira", "Saúde"),
                            modifier = Modifier.weight(1f)
                        )
                        DropdownRoutinely(
                            label = "Tags",
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
                    DescriptionTextField()
                    AddTaskButton(
                        onAddTaskClick = {
                            coroutineScope.launch {
                                showToast(context, "Falta adicionar integração com API")
                            }
                        }
                    )
                }
            }
        },
    )
}
@Preview(showBackground = false)
@Composable
fun AddTaskPreview() {
    RoutinelyTheme {
        AddTaskScreen(
            onBackButtonPressed = { },
            onHomeButtonPressed = { },
        )
    }
}
