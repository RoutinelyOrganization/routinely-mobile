package com.routinely.routinely.login

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.CreateAccountButton
import com.routinely.routinely.ui.components.CreateBottomText
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.LabelSuccess
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.NameTextField
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.TermsCheckbox
import com.routinely.routinely.ui.components.isPasswordValid
import com.routinely.routinely.ui.theme.RoutinelyTheme
import kotlinx.coroutines.delay

@Composable
fun CreateAccountScreen(
    onCreateAccountClicked: (RegisterRequest) -> ApiResponse,
    onAlreadyHaveAnAccountClicked: () -> Unit,
    shouldGoToNextScreen: Boolean,
    navigateToLoginScreen: () -> Unit,
) {
    var isPasswordFilled by rememberSaveable { mutableStateOf(false) }
    var isEmailFilled by rememberSaveable { mutableStateOf(false) }
    var isEmailValid by rememberSaveable { mutableStateOf(true) }
    var isNameFilled by rememberSaveable { mutableStateOf(false) }
    var isPasswordValid by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var nameText by rememberSaveable { mutableStateOf("") }
    var emailText by rememberSaveable { mutableStateOf("") }
    var repeatedPassword by rememberSaveable { mutableStateOf("") }
    var arePasswordsMatching by rememberSaveable { mutableStateOf(true) }
    var checkboxTermsState = rememberSaveable { mutableStateOf(false) }
    var labelMessage by rememberSaveable { mutableStateOf("") }
    var statusCode by rememberSaveable { mutableStateOf<Int?>(0) }


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
            if(statusCode != null){
                LabelError(labelMessage)
            }else {
                LabelSuccess(labelMessage)
            }
        }
        //Espaço no final
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(0.15f)
        ) {
            CreateAccountButton(
                {
                    val response = onCreateAccountClicked (
                        RegisterRequest(
                            name = nameText,
                            email = emailText,
                            password = password,
                            acceptedTerms = checkboxTermsState.value
                        )
                    )
                    labelMessage = response.message[0]
                    statusCode = response.statusCode
                },
                isPasswordValid && isEmailValid && isNameFilled && arePasswordsMatching && checkboxTermsState.value
            )

            CreateBottomText(onLoginClick = {
                onAlreadyHaveAnAccountClicked()
            })

        }
    }
    LaunchedEffect(key1 = shouldGoToNextScreen) {
        if(shouldGoToNextScreen) {
            delay(3000)
            navigateToLoginScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    RoutinelyTheme {
        CreateAccountScreen(
            onCreateAccountClicked = { ApiResponse(message = listOf(), error = "", statusCode = null) },
            onAlreadyHaveAnAccountClicked = {},
            shouldGoToNextScreen = false,
            navigateToLoginScreen = {}
        )
    }
}