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
fun TaskNameTextField(onTaskNameChange: (String) -> Unit) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }

    // Função de validação
    fun validateTaskName(value: TextFieldValue): TextFieldValue {
        val maxLength = 50
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
            // Aplicar a validação antes de atualizar o valor do campo
            textFieldValue = validateTaskName(it)
            onTaskNameChange(textFieldValue.text)
        },
        label = {
            Text(
                text = "Nome da tarefa",
                style = TextStyle(color = Color.Black)
            )
        },
        isError = textFieldValue.text.length >= 50,
        supportingText = {
            if (textFieldValue.text.length >= 50) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Quantidade de caracteres máximo, 50!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}