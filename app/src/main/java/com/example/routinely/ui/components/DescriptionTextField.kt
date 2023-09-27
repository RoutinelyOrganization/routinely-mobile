package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.routinely.ui.theme.Gray80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionTextField(onDescriptionChange: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    val maxLength = 1000
    fun validateDescription(value: TextFieldValue): TextFieldValue {
        if (value.text.length >= maxLength) {
            return TextFieldValue(
                text = value.text.take(maxLength),
                selection = TextRange(maxLength)
            )
        }
        return value
    }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = validateDescription(it)
            onDescriptionChange(textFieldValue.text)
        },
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
        isError = textFieldValue.text.length >= maxLength,
        supportingText = {
            if (textFieldValue.text.length >= maxLength) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Quantidade de caracteres máximo, 1000!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        singleLine = false,
        minLines = 3,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth() //Preencher toda a largura disponível no Row
    )
}