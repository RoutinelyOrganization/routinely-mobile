package com.example.routinely.login

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
import com.example.routinely.ui.components.CreateAccountButton
import com.example.routinely.ui.components.CreateBottomText
import com.example.routinely.ui.components.LoginTextField
import com.example.routinely.ui.components.NameTextField
import com.example.routinely.ui.components.PasswordTextField
import com.example.routinely.ui.components.TermsCheckbox
import com.example.routinely.ui.components.isPasswordValid
import com.example.routinely.ui.theme.RoutinelyTheme

@Composable
fun CreateAccountScreen(
    onCreateAccountClicked: () -> Unit,
    onAlreadyHaveAnAccountClicked: () -> Unit
) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isNameFilled by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var repeatedPassword by remember { mutableStateOf("") }
    var arePasswordsMatching by remember { mutableStateOf(false) }
    val checkboxTermsState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ){
        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth()
        ){
            Image(
                painter = painterResource(R.drawable.logo_horizontal),
                contentDescription = "Image",
                modifier = Modifier
                    .size(224.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column(
            //verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(0.70f)
                .fillMaxWidth(),
        ){
            Text(
                text = "Criar conta", color = Color.Black, fontSize = 25.sp
            )
            NameTextField(onNameChange = {name ->
                isNameFilled = name.isNotBlank() && name.length >= 3})

            LoginTextField(onEmailChange = { email ->
                isEmailFilled = email.isNotBlank()
                isEmailValid = isValidEmailFormat(email)
            })

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
                text = "Repetir senha"
            )

            TermsCheckbox(checkboxTermsState)

        }
        //Espaço no final
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(0.15f)
        ) {
            CreateAccountButton(
                onCreateAccountClick = {
                    onCreateAccountClicked()
                },
                isEmailFilled = isEmailFilled,
                isPasswordFilled = isPasswordFilled,
                isEmailValid = isEmailValid,
                isNameFilled = isNameFilled,
                isPasswordValid = isPasswordValid,
                isPasswordsMatch = arePasswordsMatching,
                isCheckBoxChecked = checkboxTermsState.value
            )
            CreateBottomText(onLoginClick = {
                onAlreadyHaveAnAccountClicked()
            })
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    RoutinelyTheme {
        CreateAccountScreen(
            onCreateAccountClicked = {},
            onAlreadyHaveAnAccountClicked = {}
        )
    }
}