package com.example.routinely.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.routinely.R
import com.example.routinely.ui.components.CreateBottonText
import com.example.routinely.ui.components.LoginButton
import com.example.routinely.ui.components.LoginTextField
import com.example.routinely.ui.components.NameTextField
import com.example.routinely.ui.components.PasswordTextField
import com.example.routinely.ui.components.TermsCheckbox
import com.example.routinely.ui.components.isPasswordValid
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun CreateScreen() {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isNameFilled by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .fillMaxWidth()
        .fillMaxHeight(),
        content = {
            Column(modifier = Modifier
                .weight(0.16f)
                .fillMaxWidth()
                .fillMaxHeight(), content = {
                Image(
                    painter = painterResource(R.drawable.logo_horizontal),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(height = 150.dp, width = 250.dp)
                        .align(Alignment.CenterHorizontally)
                )
            })
            Column(modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(Color.Transparent)
                .weight(0.65f)
                .fillMaxWidth()
                .fillMaxHeight(),
                content = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 1.dp)
                    ) {
                        Text(
                            text = "Criar conta", color = Color.Black, fontSize = 25.sp
                        )
                        NameTextField(onNameChange = {name ->
                            isNameFilled = name.isNotBlank()})

                        LoginTextField(onEmailChange = { email ->
                            isEmailFilled = email.isNotBlank()
                            isEmailValid = isValidEmailFormat(email)
                        })

                        PasswordTextField(onPasswordChange = { password ->
                            isPasswordFilled = password.isNotBlank()
                            isPasswordValid = isPasswordValid(password)
                        }, "Senha")

                        PasswordTextField(onPasswordChange = { password ->
                            isPasswordFilled = password.isNotBlank()
                            isPasswordValid = isPasswordValid(password)
                        }, "Repetir senha")

                        TermsCheckbox()

                    }
                })
            //Espaço no final
            Column(modifier = Modifier.weight(0.15f)) {
                LoginButton(
                    onLoginClick = {
                        // Ação executada quando o botão de login é clicado
                        // Você pode verificar o estado do campo de senha aqui novamente, se necessário
                    },
                    emailPreenchido = isEmailFilled,
                    senhaPreenchida = isPasswordFilled,
                    isEmailValid = isEmailValid,
                    isPasswordValid = isPasswordValid
                )
                Spacer(Modifier.size(10.dp))
                CreateBottonText()
            }
        })
}
@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    RoutinelyTheme {
        CreateScreen()
    }
}