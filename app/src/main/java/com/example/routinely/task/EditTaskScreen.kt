package com.example.routinely.task

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.R
import com.example.routinely.ui.theme.BlueRoutinely
import com.example.routinely.ui.theme.RoutinelyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen() {
    // Cria uma estrutura básica para a tela
    Scaffold(
        modifier = Modifier.padding(PaddingValues(all = 0.dp)),
        topBar = {
            // Adiciona uma barra no topo
            TopAppBar(title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center, // Centraliza horizontalmente
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(modifier = Modifier.size(56.dp))
                    Image(
                        painter = painterResource(R.drawable.topbar_horizontal),
                        contentDescription = "topbar_horizontal",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }, navigationIcon = {
                Image(
                    painter = painterResource(R.drawable.voltar),
                    contentDescription = "Voltar",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 24.dp)
                )
            }, actions = {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(R.drawable.notifications),
                        contentDescription = "Ícone à Direita",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    // Ícone de menu à direita
                    Image(
                        painter = painterResource(R.drawable.menu),
                        contentDescription = "Ícone à Direita",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = BlueRoutinely,
            )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = BlueRoutinely,
            ) {
                Button(colors = ButtonDefaults.buttonColors(BlueRoutinely),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(),
                    onClick = { }) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(70.dp)
            ) {
                // O conteúdo da tela
                Text(
                    text = "Este é o conteúdo da tela", style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                )
            }
        },
    )
}
@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    RoutinelyTheme {
        EditTaskScreen()
    }
}
