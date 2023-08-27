package com.example.routinely.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun CreateBottonText() {
    Row() {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff171a21),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) { append("Já possui uma conta? ") }
            }
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff171a21),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) { append("Entrar") }
            },
            modifier = Modifier
                .clickable { /* Ação ao clicar no link */ }
        )
    }
}

