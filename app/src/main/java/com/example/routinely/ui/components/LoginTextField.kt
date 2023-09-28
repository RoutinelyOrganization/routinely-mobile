package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.routinely.login.isValidEmailFormat
import com.example.routinely.ui.theme.Gray80

@Composable
fun LoginTextField(onEmailChange: (String) -> Unit) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    var isEmailValid by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            isEmailValid = isValidEmailFormat(text.text)
            onEmailChange(text.text)},
        label = {
            Text(
                text = "E-mail",
                style = TextStyle(color = Color.Black) // Definindo a cor do texto como branco
            )
        },
        isError = !isEmailValid,
        supportingText = {
            if (!isEmailValid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "E-mail inválido!",
                    color = MaterialTheme.colorScheme.error
                )
            }
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

@Preview(showBackground = true)
@Composable
fun LoginTextFieldPreview() {
    LoginTextField(
        onEmailChange = {}
    )
}