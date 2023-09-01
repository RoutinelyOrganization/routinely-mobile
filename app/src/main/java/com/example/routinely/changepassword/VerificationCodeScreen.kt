package com.example.routinely.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.ui.components.VerificationCodeButton
import com.example.routinely.ui.components.VerificationCodeTextField
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun VerificationCodeScreen(navController: NavHostController) {
    var isCodeFilled by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_vertical),
                contentDescription = "Image",
                modifier = Modifier
                    .size(168.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(0.60f)
                .fillMaxWidth(),
        ) {
            Text(
                text = "Redefinir senha", color = Color.Black, fontSize = 25.sp
            )
            Text(
                text = "Insira o código de verificação enviado no e-mail",
                fontSize = 14.sp
            )
            VerificationCodeTextField(onCodeChange = { code ->
                isCodeFilled = code.isNotBlank() && code.length >= 4
            })
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21), fontSize = 12.sp
                        )
                    ) { append("Não recebeu? ") }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(" Enviar novamente") }
                }
            )
        }
        //Espaço no final
        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            VerificationCodeButton(
                onLoginClick = {
                    navController.navigate("createnewpassword")
                },
                isCodeFilled = isCodeFilled,
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VerificationCodeScreen() {
    RoutinelyTheme {
        VerificationCodeScreen(rememberNavController())
    }
}