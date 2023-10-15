package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.routinely.routinely.ui.theme.PurpleRoutinely

@Composable
fun LoginButton(
    onLoginClick: () -> Unit,
    areFieldValid: Boolean
) {
    Button(
        enabled = areFieldValid,
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(PurpleRoutinely),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = "Fazer login", color = Color.White
        )
    }
}