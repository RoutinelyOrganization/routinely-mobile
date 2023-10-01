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

@Composable
fun DescriptionTextField() {
    var descriptionText by remember { mutableStateOf("") }
    var isDescriptionValid by remember { mutableStateOf( true ) }
    val maxLength = 1000

    OutlinedTextField(
        value = descriptionText,
        onValueChange = {
            if (it.length > maxLength)
                isDescriptionValid = false
            if (it.length <= maxLength) {
                descriptionText = it
                isDescriptionValid = true
            }
        },
        label = {
            Text(
                text = "Descrição",
                style = TextStyle(color = Color.Black)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
        isError = !isDescriptionValid,
        supportingText = {
            if(!isDescriptionValid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Quantidade de caracteres máximo, 1000!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (!isDescriptionValid) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier

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