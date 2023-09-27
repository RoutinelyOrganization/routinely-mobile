package com.example.routinely.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.example.routinely.ui.theme.Gray80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeTextField(
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue("00:00")) }
    var hasBeenEdited by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            val numericValue = newValue.text.filter { it.isDigit() }.take(4)
            val formattedValue = when {
                numericValue.length >= 2 -> "${numericValue.substring(0, 2)}:${numericValue.substring(2)}"
                else -> numericValue
            }

            // Verifique a posição do cursor
            val cursorPosition = if (newValue.selection.start < 3) {
                // Posição do cursor antes dos dois pontos
                newValue.selection.start
            } else {
                // Posição do cursor após os dois pontos
                formattedValue.length
            }

            textFieldValue = TextFieldValue(
                text = formattedValue,
                selection = TextRange(cursorPosition)
            )
            onTimeChange(formattedValue)
            hasBeenEdited = true

        },
        label = {
            Text(
                text = "Hora",
                style = TextStyle(color = Color.Black)
            )
        },
        isError = !isValidTime(textFieldValue.text),
        supportingText = {
            if (!isValidTime(textFieldValue.text)) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Formato inválido! Ex.: (12:30)",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true,
        modifier = modifier
            .onFocusEvent { state ->
                if (state.isFocused && !hasBeenEdited) {
                    // Limpar o campo apenas se o usuário ainda não editou
                    textFieldValue = TextFieldValue("")
                }
            }
            .fillMaxWidth()
    )
}

fun isValidTime(text: String): Boolean {
    val regex = Regex("""^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$""")
    return regex.matches(text)
}