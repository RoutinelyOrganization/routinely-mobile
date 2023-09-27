package com.example.routinely.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.routinely.ui.theme.BlueRoutinely

@Composable
fun ContinueWithAccountButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = ButtonDefaults.buttonColors(BlueRoutinely),
        shape = MaterialTheme.shapes.extraLarge,
    ) {
        Text(
            text = "Continuar com e-mail e senha",
            color = Color.White,
            modifier = Modifier.padding(vertical = 6.dp)
        )
    }
}