package com.routinely.routinely.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.ResetPasswordButton
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.util.validators.EmailInputValid
import kotlinx.coroutines.delay

@Composable
fun ForgotPasswordScreen(
    navigateToCodeVerificationScreen: () -> Unit,
    onResetPasswordClicked: (String) -> Unit,
    emailStateValidation: (email: String) -> EmailInputValid,
    shouldGoToNextScreen: Boolean,
    apiErrorMessage: List<String>,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var emailState by rememberSaveable { mutableStateOf<EmailInputValid>(EmailInputValid.Empty) }

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
                contentDescription = stringResource(R.string.desc_vertical_logo),
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
                text = stringResource(R.string.forgot_your_password),
                color = Color.Black,
                fontSize = 25.sp
            )
            Text(
                text = stringResource(R.string.type_your_email),
                fontSize = 14.sp
            )
            LoginTextField(
                onValueChange = { newEmail ->
                    email = newEmail
                    emailState = emailStateValidation(email)
                },
                labelRes = stringResource(R.string.email),
                value = email,
                error = emailState,
            )
            Text(
                text = stringResource(R.string.you_will_receive_the_code),
                fontSize = 12.sp
            )
            apiErrorMessage.forEach {
                LabelError(
                    labelRes = it
                )
            }
        }
        //Espaço no final
        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            ResetPasswordButton(
                onResetPasswordClick = {
                    onResetPasswordClicked(email)
                },
                isEmailValid = emailState == EmailInputValid.Valid,
            )
        }
    }
    LaunchedEffect(key1 = shouldGoToNextScreen) {
        if(shouldGoToNextScreen) {
            delay(2000)
            navigateToCodeVerificationScreen()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    RoutinelyTheme {
        ForgotPasswordScreen(
            emailStateValidation = {
                EmailInputValid.Empty
            },
            shouldGoToNextScreen = false,
            navigateToCodeVerificationScreen = {},
            onResetPasswordClicked = {},
            apiErrorMessage = listOf(
//            "Email inválido"
            )
        )
    }
}