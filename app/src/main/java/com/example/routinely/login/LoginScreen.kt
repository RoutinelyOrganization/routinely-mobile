package com.example.routinely.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
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
import com.example.routinely.R
import com.example.routinely.ui.components.GoogleLoginButton
import com.example.routinely.ui.components.LoginButton
import com.example.routinely.ui.components.LoginHeaderText
import com.example.routinely.ui.components.LoginTextField
import com.example.routinely.ui.components.PasswordTextField
import com.example.routinely.ui.components.RememberCheckbox
import com.example.routinely.ui.components.SignUpButton
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun LoginScreen() {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    Column(modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .fillMaxHeight(), content = {
        Column(modifier = Modifier
            .weight(0.3f)
            .fillMaxWidth()
            .fillMaxHeight(), content = {
            Image(
                painter = painterResource(R.drawable.logo_horizontal),
                contentDescription = "Image",
                modifier = Modifier
                    .size(height = 150.dp, width = 250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            /** Alteração dos nomes das funções para o padrão CamelCase e em inglês */

            // Alteração do nome da função textoCabecalho para LoginHeaderText
            LoginHeaderText()
        })
        Column(modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .background(Color(color = 0xff8f8ce7))
            .weight(0.65f)
            .fillMaxWidth()
            .fillMaxHeight(),
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Acessar conta", color = Color.White, fontSize = 25.sp
                    )

                    // Alteração do nome da função txtLogin para LoginTextField
                    LoginTextField(onEmailChange = { email ->
                        isEmailFilled = email.isNotBlank()
                        isEmailValid = isValidEmailFormat(email)
                    })

                    // Alteração do nome da função txtSenha para PasswordTextField
                    PasswordTextField(onPasswordChange = { password ->
                        isPasswordFilled = password.isNotBlank()
                    })

                    // Alteração do nome da função chkLembrar para RememberCheckbox
                    RememberCheckbox()

                    // Alteração do nome da função btnFazerLogin para LoginButton
                    LoginButton(
                        onLoginClick = {
                            // Ação executada quando o botão de login é clicado
                            // Você pode verificar o estado do campo de senha aqui novamente, se necessário
                        },
                        emailPreenchido = isEmailFilled,
                        senhaPreenchida = isPasswordFilled,
                        isEmailValid = isEmailValid,
                    )

                    // Alteração do nome da função btnCadastro para SignUpButton
                    SignUpButton()
                    Divider()

                    // Alteração do nome da função btnGoogle para GoogleLoginButton
                    GoogleLoginButton()
                }
            })
        //Espaço no final
        Column(modifier = Modifier.weight(0.05f)) {

        }
    })
}


// Regex será movido para um arquivo separado
fun isValidEmailFormat(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RoutinelyTheme {
        LoginScreen()
    }
}