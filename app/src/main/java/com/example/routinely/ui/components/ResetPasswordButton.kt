package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.routinely.ui.theme.PurpleRoutinely
@Composable
fun ResetPasswordButton(
    onLoginClick: () -> Unit,
    emailPreenchido: Boolean,
    isEmailValid: Boolean,
) {
    Button(
        enabled = emailPreenchido && isEmailValid,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(PurpleRoutinely),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = "Redefinir senha", color = Color.White
        )
    }
}