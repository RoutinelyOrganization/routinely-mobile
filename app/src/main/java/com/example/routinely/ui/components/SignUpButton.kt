package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SignUpButton() {
    Button(
        onClick = {
            // Ação executada quando o botão é clicado
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(Color.White)
    ) {
        Text(
            text = "Cadastre-se", color = Color.Gray
        )
    }
}