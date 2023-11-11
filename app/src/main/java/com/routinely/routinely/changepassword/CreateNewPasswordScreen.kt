package com.routinely.routinely.changepassword

import androidx.compose.foundation.Image
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
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.UpdatePasswordButton
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.delay

@Composable
fun CreateNewPasswordScreen(
    navigateToLoginScreen: () -> Unit,
    onUpdatePasswordClicked: (password: String, confirmPassword: String) -> PasswordInputValid,
    passwordStateValidation: (password: String) -> PasswordInputValid,
    confirmPasswordStateValidation: (password: String, confirmPassword: String) -> PasswordInputValid,
    shouldGoToNextScreen: Boolean,
    apiErrorMessage: List<String>,
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }
    var confirmPasswordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }

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
                contentDescription = "vertical logotype",
                modifier = Modifier
                    .size(168.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .weight(0.60f)
                .fillMaxWidth(),
        ){
            Text(
                text = stringResource(R.string.create_new_password),
                color = Color.Black, fontSize = 25.sp
            )
            Text(
                text = stringResource(R.string.chose_a_new_password),
                fontSize = 14.sp
            )
            PasswordTextField(
                onValueChange = { newPass: String ->
                    password = newPass
                    passwordState = passwordStateValidation(password)
                },
                labelRes = stringResource(id = R.string.password),
                value = password,
                error = passwordState,
            )

            PasswordTextField(
                onValueChange = { newPassConfirm ->
                    confirmPassword = newPassConfirm
                    confirmPasswordState = confirmPasswordStateValidation(password, confirmPassword)
                },
                labelRes = "Repetir Senha",
                value = confirmPassword,
                error = confirmPasswordState,
            )
            apiErrorMessage.forEach {
                LabelError(
                    labelRes = it
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.15f)) {
            UpdatePasswordButton(
                onLoginClick = {
                    onUpdatePasswordClicked(password, confirmPassword)
                },
            )
        }
    }
    LaunchedEffect(key1 = shouldGoToNextScreen) {
        if(shouldGoToNextScreen) {
            delay(2000)
            navigateToLoginScreen()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CreateNewPasswordScreenPreview() {
    RoutinelyTheme {
        CreateNewPasswordScreen(
            onUpdatePasswordClicked = { password, confirmPassword ->
                PasswordInputValid.Valid
            },
            passwordStateValidation = {
                PasswordInputValid.Valid
            },
            apiErrorMessage = listOf(
//            "Email inválido"
            ),
            shouldGoToNextScreen = false,
            navigateToLoginScreen = {},
            confirmPasswordStateValidation = { password, confirmPassword ->
                PasswordInputValid.Valid
            }
        )
    }
}