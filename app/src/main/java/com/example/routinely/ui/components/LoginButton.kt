package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.routinely.ui.theme.BlueRoutinely

@Composable
fun LoginButton(
    onLoginClick: () -> Unit,
    emailPreenchido: Boolean,
    senhaPreenchida: Boolean,
    isEmailValid: Boolean,
    isPasswordValid: Boolean
) {
    val isButtonEnabled = senhaPreenchida && emailPreenchido && isEmailValid && isPasswordValid;
    Button(
        enabled = isButtonEnabled,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = if(isButtonEnabled)
                    ButtonDefaults.buttonColors(BlueRoutinely)
                else ButtonDefaults.buttonColors(Color.Blue),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = "Fazer login", color = Color.White
        )
    }
}