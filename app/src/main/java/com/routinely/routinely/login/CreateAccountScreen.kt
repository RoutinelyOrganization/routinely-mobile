package com.routinely.routinely.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.CreateAccountButton
import com.routinely.routinely.ui.components.CreateBottomText
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.NameTextField
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.TermsCheckbox
import com.routinely.routinely.ui.components.isPasswordValid
import kotlinx.coroutines.runBlocking

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel,
    onCreateAccountClicked: () -> Unit,
    onAlreadyHaveAnAccountClicked: () -> Unit
) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isNameFilled by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var nameText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var repeatedPassword by remember { mutableStateOf("") }
    var arePasswordsMatching by remember { mutableStateOf(true) }
    var checkboxTermsState = remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(16.dp))
            NameTextField(onNameChange = {name ->
                nameText = name
                isNameFilled = name.isNotBlank() && name.length >= 3})

            LoginTextField(onEmailChange = { email ->
                isEmailFilled = email.isNotBlank()
                isEmailValid = isValidEmailFormat(email)
                emailText = email
            })

            PasswordTextField(
                onPasswordChange = { newPassword ->
                    password = newPassword
                    isPasswordFilled = password.isNotBlank()
                    isPasswordValid = isPasswordValid(password)
                    arePasswordsMatching = password == repeatedPassword
                },
                label = "Senha",
                passwordMatch = arePasswordsMatching
            )

            PasswordTextField(
                onPasswordChange = { newRepeatedPassword ->
                    repeatedPassword = newRepeatedPassword
                    arePasswordsMatching = password == repeatedPassword
                },
                label = "Repetir senha",
                passwordMatch = arePasswordsMatching
            )
            TermsCheckbox(checkboxTermsState)
            if (showError){
                Text(
                    text = "Erro"
                )
            }
        }
        //Espaço no final
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(0.15f)
        ) {
            CreateAccountButton(
                onCreateAccountClick = {
                    val params = RegisterRequest(
                        name = nameText,
                        email = emailText,
                        password = password,
                        acceptedTerms = checkboxTermsState.value
                    )
                    val response = runBlocking { viewModel.createNewAccount(params) }
                    Log.d("CreateAccountScreen", "CreateAccountScreen: ${response.message}")
                    if(response.statusCode == null){
                        onCreateAccountClicked()
                    }else{
                        showError = true
                    }

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
//
//@Preview(showBackground = true)
//@Composable
//fun CreateScreenPreview() {
//    RoutinelyTheme {
//        CreateAccountScreen(
//            onCreateAccountClicked = {},
//            onAlreadyHaveAnAccountClicked = {}
//        )
//    }
//}