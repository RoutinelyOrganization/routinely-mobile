package com.routinely.routinely.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.ForgotPasswordText
import com.routinely.routinely.ui.components.LoginButton
import com.routinely.routinely.ui.components.LoginHeaderText
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.RememberCheckbox
import com.routinely.routinely.ui.components.SignUpButton
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.PasswordInputValid
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    authenticated: Boolean,
    navigateToHomeScreen: () -> Unit,
    navigateToCreateAccountScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
    emailStateValidation: (email: String) -> EmailInputValid,
    passwordStateValidation: (password: String) -> PasswordInputValid,
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }
    var emailState by rememberSaveable { mutableStateOf<EmailInputValid>(EmailInputValid.Empty) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.15f)
                .fillMaxWidth()
        ) {
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
                .weight(0.85f)
                .fillMaxWidth()
        ) {
            LoginHeaderText()
            Spacer(modifier = Modifier.height(42.dp))
            Text(
                text = "Acessar conta",
                color = Color.Black,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LoginTextField(
                onValueChange = { newEmail ->
                    email = newEmail
                    emailState = emailStateValidation(email)
                },
                labelRes = stringResource(R.string.email),
                value = email,
                error = emailState,
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
            ) {
                RememberCheckbox()
                ForgotPasswordText(
                    onLoginClick = {
                        navigateToForgotPasswordScreen()
                    }
                )
            }
            Spacer(modifier = Modifier .height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                LoginButton(
                    onLoginClick = {
                        coroutineScope.launch {
                            viewModel.loginWithEmailAndPassword(email = email, password = password)
                        }
                    },
                    areFieldValid = emailState == EmailInputValid.Valid &&
                            passwordState == PasswordInputValid.Valid
                )
                SignUpButton(onLoginClick = {
                    navigateToCreateAccountScreen()
                })
            }

        }

        LaunchedEffect(key1 = authenticated) {
            Log.d("LoginScreen", "Authenticated value changed")
            if(authenticated) {
                Log.d("LoginScreen", "Authenticated = true")
                navigateToHomeScreen()
            }
        }
    }
}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//    RoutinelyTheme {
//        LoginScreen(
//            viewModel = LoginViewModel(),
//            authenticated = false,
//            navigateToHomeScreen = {},
//            navigateToCreateAccountScreen = {},
//            navigateToForgotPasswordScreen = {},
//            )
//    }
//}
