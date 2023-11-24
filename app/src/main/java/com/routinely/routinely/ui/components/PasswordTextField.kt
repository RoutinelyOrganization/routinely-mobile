package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.ui.theme.Gray80
import com.routinely.routinely.ui.theme.GrayRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.validators.PasswordInputValid

@Composable
fun PasswordTextField(
    onValueChange: (String) -> Unit,
    labelRes: String,
    value: String,
    error: PasswordInputValid,
    apiError: Boolean = false,
) {
    val isPasswordValid by rememberSaveable { mutableStateOf(true) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = labelRes,
                style = TextStyle(color = Color.Black)
            )
        },
        isError = error is PasswordInputValid.ErrorList || error is PasswordInputValid.Error || apiError,
        supportingText = {
            Column {
                if (error is PasswordInputValid.Error) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(error.messageId),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
            ) {
                TextButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Text(
                        text = if (isPasswordVisible) "ESCONDER" else "EXIBIR",
                        color = Color.Black,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
                if (!isPasswordValid) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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