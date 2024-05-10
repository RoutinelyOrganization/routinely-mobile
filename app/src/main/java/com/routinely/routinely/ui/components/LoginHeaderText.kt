package com.routinely.routinely.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R

@Composable
fun LoginHeaderText() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append(stringResource(id = R.string.login_header_text_0)) }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append(" " + stringResource(id = R.string.login_header_text_1) + " ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append("" + stringResource(id = R.string.login_header_text_2) + " ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append(stringResource(id = R.string.login_header_text_3) + " ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            ) { append("" +stringResource(id = R.string.login_header_text_4) + " ") }
            withStyle(
                style = SpanStyle(
                    color = Color(0xff171a21), fontSize = 16.sp
                )
            ) { append(stringResource(id = R.string.login_header_text_5)) }
        }
    )
}
