package com.routinely.routinely.changepassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ForgotPasswordRequest
import com.routinely.routinely.data.auth.model.ForgotPasswordResult
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.ResetPasswordButton
import com.routinely.routinely.util.validators.EmailInputValid

@Composable
fun ForgotPasswordScreen(
    navigateToCodeVerificationScreen: (accountId: String) -> Unit,
    onResetPasswordClicked: (ForgotPasswordRequest) -> Unit,
    emailStateValidation: (email: String) -> EmailInputValid,
    forgotPasswordResult: ForgotPasswordResult,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var emailState by rememberSaveable { mutableStateOf<EmailInputValid>(EmailInputValid.Empty) }
    var showApiErrors by rememberSaveable { mutableStateOf(false) }
    var showFieldError by rememberSaveable { mutableStateOf(false) }
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var apiErrorMessage by rememberSaveable { mutableIntStateOf(0) }

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
                fontWeight = FontWeight.Bold,
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
                    if(showFieldError) showFieldError = false
                },
                labelRes = stringResource(R.string.email),
                value = email,
                error = emailState,
                apiError = showFieldError,
            )
            Text(
                text = stringResource(R.string.you_will_receive_the_code),
                fontSize = 12.sp
            )
            if(showApiErrors) {
                LabelError(stringResource(apiErrorMessage))
            }
        }
        //Espaço no final
        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            ResetPasswordButton(
                onResetPasswordClick = {
                    onResetPasswordClicked(ForgotPasswordRequest(email = email))
                },
                isEmailValid = emailState == EmailInputValid.Valid && !showLoading,
            )
        }
    }

    LaunchedEffect(key1 = forgotPasswordResult) {
        when(forgotPasswordResult) {
            is ForgotPasswordResult.Success -> {
                showApiErrors = false
                showLoading = false
                showFieldError = false
                navigateToCodeVerificationScreen(forgotPasswordResult.accountId)
            }
            is ForgotPasswordResult.Error -> {
                apiErrorMessage = forgotPasswordResult.message
                showApiErrors = true
                showLoading = false
                showFieldError = true
            }
            is ForgotPasswordResult.DefaultError -> {
                apiErrorMessage = R.string.api_unexpected_error
                showApiErrors = true
                showLoading = false
                showFieldError = false
            }
            is ForgotPasswordResult.Loading -> {
                showLoading = true
                showApiErrors = false
                showFieldError = false
            }
            else -> Unit
        }
    }

    if(showLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            IndeterminateCircularIndicator()
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun ForgotPasswordScreenPreview() {
//    RoutinelyTheme {
//        ForgotPasswordScreen(
//            emailStateValidation = {
//                EmailInputValid.Empty
//            },
//            shouldGoToNextScreen = false,
//            navigateToCodeVerificationScreen = {},
//            onResetPasswordClicked = {},
//            apiErrorMessage = listOf(
//            "Email inválido"
//            )
//        )
//    }
//}