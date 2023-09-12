package com.example.routinely.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun LoginScreen(
    viewModel: LoginViewModel,
    authenticated: Boolean,
    navigateToHomeScreen: () -> Unit,
    navigateToCreateAccountScreen: () -> Unit,
    navigateToForgotPasswordScreen: () -> Unit,
) {
    var isPasswordFilled by remember { mutableStateOf(false) }
    var isEmailFilled by remember { mutableStateOf(false) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(0.70f)
                .fillMaxWidth()
        ) {
            LoginHeaderText()
            Text(
                text = "Acessar conta",
                color = Color.Black,
                fontSize = 25.sp
            )

            LoginTextField(onEmailChange = { newEmail ->
                email = newEmail
                isEmailFilled = newEmail.isNotBlank()
                isEmailValid = isValidEmailFormat(newEmail)
            })

            PasswordTextField(
                onPasswordChange = { newPassword ->
                    password = newPassword
                    isPasswordFilled = password.isNotBlank()
                    isPasswordValid = isPasswordValid(password)
                },
                text = "Senha"
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
            ) {
                RememberCheckbox()
                ForgotPasswordText(onLoginClick = {
                    navigateToForgotPasswordScreen()
                })
            }

            LoginButton(
                onLoginClick = {
                    coroutineScope.launch {
                        viewModel.loginWithEmailAndPassword(email = email, password = password)
                    }
                },
                emailPreenchido = isEmailFilled,
                senhaPreenchida = isPasswordFilled,
                isEmailValid = isEmailValid,
                isPasswordValid = isPasswordValid
            )

            SignUpButton(onLoginClick = {
                navigateToCreateAccountScreen()
            })
        }

        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {

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
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    RoutinelyTheme {
        LoginScreen(
            viewModel = LoginViewModel(),
            authenticated = false,
            navigateToHomeScreen = {},
            navigateToCreateAccountScreen = {},
            navigateToForgotPasswordScreen = {},
            )
    }
}
