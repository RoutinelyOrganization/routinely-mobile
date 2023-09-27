package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.routinely.ui.theme.Gray80

@Composable
fun VerificationCodeTextField(onCodeChange: (String) -> Unit) {
    var code by rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = code,
        onValueChange = {
            code = it
            onCodeChange(it)},
        label = {
            Text(
                text = "Código de verificação",
                style = TextStyle(color = Color.Black) // Definindo a cor do texto como branco
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray,
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}