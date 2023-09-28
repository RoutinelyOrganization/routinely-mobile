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
fun UpdatePasswordButton(
    onLoginClick: () -> Unit,
    isPasswordFilled: Boolean,
    isPasswordValid: Boolean,
    isPasswordsMatch: Boolean,
) {
    Button(
        enabled = isPasswordFilled && isPasswordValid && isPasswordsMatch,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(PurpleRoutinely),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = "Atualizar senha", color = Color.White
        )
    }
}