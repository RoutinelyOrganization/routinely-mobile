package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.routinely.ui.theme.Gray80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionTextField(onDescriptionChange: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            onDescriptionChange(it)},
        label = {
            Text(
                text = "Descrição",
                style = TextStyle(color = Color.Black) // Definindo a cor do texto como branco
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = false,
        minLines = 3,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth() //Preencher toda a largura disponível no Row
    )
}