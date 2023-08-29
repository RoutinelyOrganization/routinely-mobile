package com.example.routinely.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.login.isValidEmailFormat
import com.example.routinely.ui.components.LoginTextField
import com.example.routinely.ui.components.ResetPasswordButton
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun ForgotPasswordScreen(navController: NavHostController) {
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    Column(modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
        content = {
            Column(modifier = Modifier
                .weight(0.20f)
                .fillMaxWidth()
                .fillMaxHeight(), content = {
                Image(
                    painter = painterResource(R.drawable.logo_horizontal),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(height = 148.dp, width = 136.dp)
                        .align(Alignment.CenterHorizontally)
                )
            })
            Column(modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Transparent)
                .weight(0.70f)
                .fillMaxWidth()
                .fillMaxHeight(),
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 1.dp)
                    ) {
                        Text(
                            text = "Esqueceu sua senha?", color = Color.Black, fontSize = 25.sp
                        )
                        Text(
                            text = "Digite o e-mail cadastrado na sua conta Routinely",
                            fontSize = 14.sp
                        )
                        LoginTextField(onEmailChange = { email ->
                            isEmailFilled = email.isNotBlank()
                            isEmailValid = isValidEmailFormat(email)
                        })
                        Text(
                            text = "Você receberá um código de verificação no seu email",
                            fontSize = 12.sp
                        )
                    }
                })
            //Espaço no final
            Column(modifier = Modifier.weight(0.10f)) {
                ResetPasswordButton(
                    onLoginClick = {
                        navController.navigate("createnewpassword")
                    },
                    emailPreenchido = isEmailFilled,
                    isEmailValid = isEmailValid,
                )
            }
        })
}
@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    RoutinelyTheme {
        ForgotPasswordScreen(rememberNavController())
    }
}