package com.routinely.routinely.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
import com.routinely.routinely.ui.theme.Gray80
import com.routinely.routinely.ui.theme.GrayRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely

@Composable
fun VerificationCodeTextField(onCodeChange: (String) -> Unit) {
    var codeText by rememberSaveable { mutableStateOf("") }
    var isCodeValid by remember { mutableStateOf( true) }
    val maxLength = 6
    val pattern = remember { Regex("^[0-9]*\$") }

    OutlinedTextField(
        value = codeText,
        onValueChange = {
            Log.d("TaskNameTextField", "TaskNameTextField: " + it.length)
            if (it.length > maxLength)
                isCodeValid = false
            if (it.matches(pattern) && it.length <= maxLength) {
                codeText = it
                isCodeValid = true
            }
            onCodeChange(it)
        },
        label = {
            Text(
                text = "Código de verificação",
                style = TextStyle(color = Color.Black) // Definindo a cor do texto como branco
            )
        },
        isError = !isCodeValid,
        supportingText = {
            if (!isCodeValid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Quantidade de números máximo, 6!",
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
            if (!isCodeValid) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier

                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}
