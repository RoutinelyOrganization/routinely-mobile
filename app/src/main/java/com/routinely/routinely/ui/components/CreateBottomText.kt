package com.routinely.routinely.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R

@Composable
fun CreateBottomText(onLoginClick: () -> Unit) {
    Row {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color(0xff171a21),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) { append(stringResource(id = R.string.already_have_account) + " ") }
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
                ) { append(stringResource(id = R.string.login)) }
            },
            modifier = Modifier.clickable { onLoginClick() }
        )
    }
}

