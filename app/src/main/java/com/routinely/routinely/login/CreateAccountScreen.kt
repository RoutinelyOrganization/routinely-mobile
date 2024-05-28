package com.routinely.routinely.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ApiResponse
import com.routinely.routinely.data.auth.model.CreateAccountResult
import com.routinely.routinely.data.auth.model.RegisterRequest
import com.routinely.routinely.ui.components.CreateAccountButton
import com.routinely.routinely.ui.components.CreateBottomText
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.LoginTextField
import com.routinely.routinely.ui.components.NameTextField
import com.routinely.routinely.ui.components.PasswordTextField
import com.routinely.routinely.ui.components.TermsCheckbox
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.util.validators.EmailInputValid
import com.routinely.routinely.util.validators.NameInputValid
import com.routinely.routinely.util.validators.PasswordInputValid
import com.routinely.routinely.util.validators.PrivacyPolicyInputValid

@Composable
fun CreateAccountScreen(
    onCreateAccountClicked: (RegisterRequest) -> Unit,
    onAlreadyHaveAnAccountClicked: () -> Unit,
    createAccountResult: CreateAccountResult,
    navigateToLoginScreen: () -> Unit,
    nameStateValidation: (name: String) -> NameInputValid,
    emailStateValidation: (email: String) -> EmailInputValid,
    passwordStateValidation: (password: String) -> PasswordInputValid,
    confirmPasswordStateValidation: (password: String, confirmPassword: String) -> PasswordInputValid,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameState by rememberSaveable { mutableStateOf<NameInputValid>(NameInputValid.Empty) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailState by rememberSaveable { mutableStateOf<EmailInputValid>(EmailInputValid.Empty) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPasswordState by rememberSaveable { mutableStateOf<PasswordInputValid>(PasswordInputValid.Empty) }
    var privacy by rememberSaveable { mutableStateOf(false) }
    var privacyState by rememberSaveable { mutableStateOf<PrivacyPolicyInputValid>(PrivacyPolicyInputValid.Empty) }
    var apiErrorMessage by rememberSaveable { mutableIntStateOf(0) }
    var showApiErrors by rememberSaveable { mutableStateOf(false) }
    var showLoading by rememberSaveable { mutableStateOf(false) }
    var showFieldError by rememberSaveable { mutableStateOf(false) }

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
                contentDescription = stringResource(R.string.desc_horizontal_logo),
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
                text = stringResource(R.string.title_create_account), color = Color.Black, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(16.dp))
            NameTextField(
                value = name,
                onValueChange = { newName: String ->
                    name = newName
                    nameState = nameStateValidation(name)
                },
                labelRes = stringResource(id = R.string.name),
                error = nameState,
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
                apiError = showFieldError
            )

            PasswordTextField(
                onValueChange = { newPass: String ->
                    password = newPass
                    passwordState = passwordStateValidation(password)
                    if(showFieldError) showFieldError = false
                },
                labelRes = stringResource(id = R.string.password),
                value = password,
                error = passwordState,
            )

            PasswordTextField(
                onValueChange = { newPassConfirm ->
                    confirmPassword = newPassConfirm
                    confirmPasswordState = confirmPasswordStateValidation(password, confirmPassword)
                    if(showFieldError) showFieldError = false
                },
                labelRes = stringResource(R.string.label_repeat_password),
                value = confirmPassword,
                error = confirmPasswordState,
            )

            TermsCheckbox(
                isChecked = privacy,
            ) {
                privacy = !privacy
                privacyState =
                    if (it) PrivacyPolicyInputValid.Error else PrivacyPolicyInputValid.Valid
            }
            if(showApiErrors) {
                LabelError(
                    labelRes = stringResource(apiErrorMessage)
                )
            }
        }
        //Espaço no final
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(0.15f)
        ) {
            CreateAccountButton (
                {
                    onCreateAccountClicked(
                        RegisterRequest(
                            name = name,
                            email = email,
                            password = password,
                            acceptedTerms = privacy
                        )
                    )
                },
                nameState == NameInputValid.Valid
                        && emailState == EmailInputValid.Valid
                        && passwordState == PasswordInputValid.Valid
                        && confirmPasswordState == PasswordInputValid.Valid
                        && privacyState == PrivacyPolicyInputValid.Valid
                        && !showLoading
            )

            CreateBottomText(onLoginClick = {
                onAlreadyHaveAnAccountClicked()
            })
        }
    }

    LaunchedEffect(key1 = createAccountResult) {
        when(createAccountResult) {
            is CreateAccountResult.Success -> {
                showApiErrors = false
                showLoading = false
                showFieldError = false
                navigateToLoginScreen()
            }
            is CreateAccountResult.Error -> {
                apiErrorMessage = createAccountResult.message
                showApiErrors = true
                showLoading = false
                showFieldError = true
            }
            is CreateAccountResult.DefaultError -> {
                apiErrorMessage = R.string.api_unexpected_error
                showApiErrors = true
                showLoading = false
            }
            is CreateAccountResult.Loading -> {
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

@Preview(showBackground = true)
@Composable
fun CreateScreenPreview() {
    RoutinelyTheme {
        CreateAccountScreen(
            onCreateAccountClicked = { ApiResponse.DefaultError },
            onAlreadyHaveAnAccountClicked = {},
            createAccountResult = CreateAccountResult.Empty,
            navigateToLoginScreen = {},
            nameStateValidation = {
                NameInputValid.Error(R.string.invalid_name)
            },
            emailStateValidation = {
                EmailInputValid.Valid
            },
            passwordStateValidation = {
                PasswordInputValid.Valid
            },
            confirmPasswordStateValidation = { password, confirmPassword ->
                PasswordInputValid.Valid
            },
        )
    }
}