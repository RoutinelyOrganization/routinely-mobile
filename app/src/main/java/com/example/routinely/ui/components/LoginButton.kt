package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LoginButton(
    onLoginClick: () -> Unit,
    emailPreenchido: Boolean,
    senhaPreenchida: Boolean,
    isEmailValid: Boolean
) {
    Button(
        enabled = senhaPreenchida && emailPreenchido && isEmailValid,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White)

    ) {
        Text(
            text = "Fazer Login", color = Color(0xff8f8ce7)
        )
    }
}