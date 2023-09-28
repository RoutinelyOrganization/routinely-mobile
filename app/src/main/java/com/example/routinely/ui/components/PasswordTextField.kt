package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.ui.theme.Gray80

@Composable
fun PasswordTextField(onPasswordChange: (String) -> Unit, text: String) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(true) }

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onPasswordChange(it)
            isPasswordValid = isPasswordValid(it) // Atualizamos isPasswordValid
        },
        label = {
            Text(
                text = text,
                style = TextStyle(color = Color.Black)
            )
        },
        isError = !isPasswordValid,
        supportingText = {
            if (!isPasswordValid) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Senha inválida!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            TextButton(
                onClick = { passwordVisible = !passwordVisible },
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                Text(
                    text = if (passwordVisible) "ESCONDER" else "EXIBIR",
                    color = Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
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
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

fun isPasswordValid(password: String): Boolean {
    val lengthRequirement = password.length >= 6
    val uppercaseRequirement = password.any { it.isUpperCase() }
    val lowercaseRequirement = password.any { it.isLowerCase() }
    val digitRequirement = password.any { it.isDigit() }
    val symbolRequirement = password.any { it in "!@#$%&*=" }

    return lengthRequirement && uppercaseRequirement && lowercaseRequirement && digitRequirement && symbolRequirement
}