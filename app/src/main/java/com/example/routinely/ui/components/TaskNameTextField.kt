package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.example.routinely.ui.theme.Gray80
import com.example.routinely.ui.theme.GrayRoutinely
import com.example.routinely.ui.theme.PurpleRoutinely

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskNameTextField() {
    var nameTaskText by remember { mutableStateOf("") }
    var isNameValid by remember { mutableStateOf( true ) }
    val maxLength = 50

    OutlinedTextField(
        value = nameTaskText,
        onValueChange = {
            if (it.length > maxLength)
                isNameValid = false
            if (it.length <= maxLength) {
                nameTaskText = it
                isNameValid = true
            }
        },
        label = {
            Text(
                text = "Nome da tarefa",
                style = TextStyle(color = Color.Black)
            )
        },
        isError = !isNameValid,
        supportingText = {
            if(!isNameValid) {
                Text(
                    text = "Quantidade de caracteres máximo, $maxLength!",
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
        trailingIcon = {
            if (!isNameValid) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier

                )
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}
