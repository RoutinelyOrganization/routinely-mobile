package com.example.routinely.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun LoginHeaderText() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append("Gerencie") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append(" suas tarefas,") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append(" organize ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append("sua rotina e") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append(" aproveite ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append("seu tempo com o que desejar.") }
        }
    )
}