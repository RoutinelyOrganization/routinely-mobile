package com.routinely.routinely.changepassword

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.CreateNewPasswordResult
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.UpdatePasswordButton
import com.routinely.routinely.util.validators.PasswordInputValid

@Composable
fun CreateNewPasswordScreen(
    navigateToLoginScreen: () -> Unit,
    onUpdatePasswordClicked: (password: String, confirmPassword: String) -> Unit,
    passwordStateValidation: (password: String) -> PasswordInputValid,
    confirmPasswordStateValidation: (password: String, confirmPassword: String) -> PasswordInputValid,
    createNewPasswordResult: CreateNewPasswordResult
) {
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }
    var confirmPasswordState by rememberSaveable {
        mutableStateOf<PasswordInputValid>(
            PasswordInputValid.Empty
        )
    }
    var showApiErrors by rememberSaveable { mutableStateOf(false) }
    var showFieldError by rememberSaveable { mutableStateOf(false) }
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var apiErrorMessage by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .weight(0.25f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.logo_vertical),
                contentDescription = stringResource(R.string.desc_vertical_logo),
                modifier = Modifier
                    .size(168.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Column(
            modifier = Modifier
                .weight(0.60f)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.create_new_password),
                color = Color.Black, fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
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
                labelRes = stringResource(R.string.label_repeat_password),
                value = confirmPassword,
                error = confirmPasswordState,
            )
            if (showApiErrors) {
                LabelError(labelRes = stringResource(id = apiErrorMessage))
            }
        }

        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            UpdatePasswordButton(
                onLoginClick = {
                    onUpdatePasswordClicked(password, confirmPassword)
                },
                enable = !showLoading
                        && passwordState == PasswordInputValid.Valid
                        && confirmPasswordState == PasswordInputValid.Valid
            )
        }
    }
    val ctx = LocalContext.current
    val confirmationMessage = rememberSaveable{ mutableStateOf("") }
    if (confirmationMessage.value != "") Toast.makeText(ctx, confirmationMessage.value, Toast.LENGTH_SHORT).show()

    LaunchedEffect(key1 = createNewPasswordResult) {
        when (createNewPasswordResult) {
            is CreateNewPasswordResult.Success -> {
                showApiErrors = false
                showLoading = false
                showFieldError = false
                confirmationMessage.value = createNewPasswordResult.message
                navigateToLoginScreen()
            }

            is CreateNewPasswordResult.Error -> {
                apiErrorMessage = createNewPasswordResult.message
                showApiErrors = true
                showLoading = false
                showFieldError = true
            }

            is CreateNewPasswordResult.DefaultError -> {
                apiErrorMessage = R.string.api_unexpected_error
                showApiErrors = true
                showLoading = false
                showFieldError = false
            }

            is CreateNewPasswordResult.Loading -> {
                showLoading = true
                showApiErrors = false
                showFieldError = false
            }
            else -> Unit
        }
    }

    if (showLoading) {
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
//fun CreateNewPasswordScreenPreview() {
//    RoutinelyTheme {
//        CreateNewPasswordScreen(
//            onUpdatePasswordClicked = { password, confirmPassword ->
//                PasswordInputValid.Valid
//            },
//            passwordStateValidation = {
//                PasswordInputValid.Valid
//            },
//            navigateToLoginScreen = {},
//            confirmPasswordStateValidation = { password, confirmPassword ->
//                PasswordInputValid.Valid
//            },
//            code= "",
//            accountId = "",
//            createNewPasswordResult = CreateNewPasswordResult.DefaultError
//        )
//    }
//}