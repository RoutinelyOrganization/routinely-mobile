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
import com.example.routinely.ui.theme.Gray80
import com.example.routinely.ui.theme.GrayRoutinely
import com.example.routinely.ui.theme.PurpleRoutinely

@Composable
fun NameTextField(
    onNameChange: (String) -> Unit
) {
    var nameText by rememberSaveable { mutableStateOf("") }
    var isNameFilled by remember { mutableStateOf(true) }
    val pattern = remember { Regex("^[\\p{L}\\s]*$") }
    val minLength = 3
    OutlinedTextField(
        value = nameText,
        onValueChange = {
            if(it.matches(pattern)) {
                nameText = it
            }
            isNameFilled = it.length > minLength
            onNameChange(it)
        },
        label = {
            Text(
                text = "Nome",
                style = TextStyle(color = Color.Black) // Definindo a cor do texto como branco
            )
        },
        isError = !isNameFilled,
        supportingText = {
            if(!isNameFilled) {
                Text(
                    text = "Nome deve conter ao menos $minLength caracteres",
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}