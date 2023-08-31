package com.example.routinely.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.ui.components.ForgotPasswordText
import com.example.routinely.ui.components.LoginButton
import com.example.routinely.ui.components.LoginHeaderText
import com.example.routinely.ui.components.LoginTextField
import com.example.routinely.ui.components.PasswordTextField
import com.example.routinely.ui.components.RememberCheckbox
import com.example.routinely.ui.components.SignUpButton
import com.example.routinely.ui.components.isPasswordValid
import com.example.routinely.ui.theme.RoutinelyTheme
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
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

                LoginHeaderText()
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
                            text = "Acessar conta", color = Color.Black, fontSize = 25.sp
                        )

                        LoginTextField(onEmailChange = { email ->
                            isEmailFilled = email.isNotBlank()
                            isEmailValid = isValidEmailFormat(email)
                        })

                        PasswordTextField(
                            onPasswordChange = { newPassword ->
                                password = newPassword
                                isPasswordFilled = password.isNotBlank()
                                isPasswordValid = isPasswordValid(password)
                            },
                            text = "Senha"
                        )

                        Row{
                            RememberCheckbox()
                            ForgotPasswordText(onLoginClick = {
                                navController.navigate("forgotpassword")
                            })
                        }

                        LoginButton(
                            onLoginClick = {
                                coroutineScope.launch {
                                    showToast(context, "Login ainda não funciona :)")
                                }
                            },
                            emailPreenchido = isEmailFilled,
                            senhaPreenchida = isPasswordFilled,
                            isEmailValid = isEmailValid,
                            isPasswordValid = isPasswordValid
                        )
                        SignUpButton( onLoginClick = {
                            navController.navigate("createaccount")
                        })
                    }
                })
            Column(modifier = Modifier.weight(0.10f)) {}
        })
}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RoutinelyTheme {
        LoginScreen(rememberNavController()) // Não passe um NavHostController aqui
    }
}
