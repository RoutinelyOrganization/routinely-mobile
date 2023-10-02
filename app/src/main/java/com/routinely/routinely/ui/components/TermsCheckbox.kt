package com.routinely.routinely.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TermsCheckbox(checkBoxState: MutableState<Boolean>) {
    //val checkboxState = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {checkBoxState.value = !checkBoxState.value }
    )
    {
        Checkbox(
            checked = checkBoxState.value, onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xff8f8ce7),
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White
            ),
        )
        Row() {
            Column() {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xff171a21),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) { append("Declaro que li e concordo com os ") }
                    }
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xff171a21),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ) { append("termos de uso e política de privacidade.") }
                    },
                    modifier = Modifier
                        .clickable { /* Ação ao clicar no link */ }
                        .drawBehind {
                            drawLine(
                                color = Color.Gray,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 2f
                            )
                        },
                )
            }
        }
    }
}