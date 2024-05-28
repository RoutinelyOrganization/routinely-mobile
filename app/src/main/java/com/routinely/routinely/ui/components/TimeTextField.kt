package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.Gray80


/*
   This component is not being used for now
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeTextField(
    onTimeChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var textFieldState by remember { mutableStateOf(TextFieldValue("00:00")) }

    OutlinedTextField(
        value = textFieldState,
        onValueChange = { newValue ->
            val numericValue = newValue.text.filter { it.isDigit() }.take(4)
            val formattedValue = when (numericValue.length) {
                1 -> "0$numericValue"
                2 -> numericValue
                3 -> "${numericValue.take(2)}:${numericValue[2]}"
                4 -> "${numericValue.take(2)}:${numericValue.takeLast(2)}"
                else -> "00:00"
            }

            textFieldState = TextFieldValue(
                text = formattedValue,
                selection = TextRange(numericValue.length + 1) // Define o cursor após a formatação
            )
            onTimeChange(formattedValue)
        },
        label = {
            Text(
                text = stringResource(id = R.string.hour),
                style = TextStyle(color = Color.Black)
            )
        },
        isError = !isValidTime(textFieldState.text),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (!focusState.isFocused) {
                    textFieldState = TextFieldValue(
                        text = textFieldState.text.take(5),
                        selection = TextRange(5)
                    )
                }
            }
    )
}

fun isValidTime(text: String): Boolean {
    val regex = Regex("""^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$""")
    return regex.matches(text)
}