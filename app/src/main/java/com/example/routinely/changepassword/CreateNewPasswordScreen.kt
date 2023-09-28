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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.routinely.R
import com.example.routinely.ui.components.PasswordTextField
import com.example.routinely.ui.components.UpdatePasswordButton
import com.example.routinely.ui.components.isPasswordValid
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun CreateNewPasswordScreen(
    onUpdatePasswordClicked: () -> Unit
) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var repeatedPassword by remember { mutableStateOf("") }
    var arePasswordsMatching by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ){
        Column(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth()
        ){
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
        ){
            Text(
                text = "Criar nova senha",
                color = Color.Black, fontSize = 25.sp
            )
            Text(
                text = "Escolha uma nova senha abaixo ela precisa ser diferente da senha anterior",
                fontSize = 14.sp
            )
            PasswordTextField(
                onPasswordChange = { newPassword ->
                    password = newPassword
                    isPasswordFilled = password.isNotBlank()
                    isPasswordValid = isPasswordValid(password)
                    arePasswordsMatching = password == repeatedPassword
                },
                text = "Senha"
            )

            PasswordTextField(
                onPasswordChange = { newRepeatedPassword ->
                    repeatedPassword = newRepeatedPassword
                    arePasswordsMatching = password == repeatedPassword
                },
                text = "Repetir Senha"
            )
        }

        Column(
            modifier = Modifier
                .weight(0.15f)) {
            UpdatePasswordButton(
                onLoginClick = {
                    onUpdatePasswordClicked()
                },
                isPasswordFilled = isPasswordFilled,
                isPasswordValid = isPasswordValid,
                isPasswordsMatch = arePasswordsMatching
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {
    RoutinelyTheme {
        CreateNewPasswordScreen(onUpdatePasswordClicked = {})
    }
}