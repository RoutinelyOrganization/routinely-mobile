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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.data.auth.model.ValidateCodeResult
import com.routinely.routinely.ui.components.IndeterminateCircularIndicator
import com.routinely.routinely.ui.components.LabelError
import com.routinely.routinely.ui.components.VerificationCodeButton
import com.routinely.routinely.ui.components.VerificationCodeTextField
import com.routinely.routinely.util.validators.CodeInputValid

@Composable
fun VerificationCodeScreen(
    onConfirmResetPasswordClicked: (code: String) -> Unit,
    navigateToSetNewPasswordScreen: (code: String) -> Unit,
    codeStateValidation: (code: String) -> CodeInputValid,
    validateCodeResult: ValidateCodeResult
) {

    var code by rememberSaveable { mutableStateOf("") }
    var codeState by rememberSaveable { mutableStateOf<CodeInputValid>(CodeInputValid.Empty) }
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(0.60f)
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.reset_password),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = stringResource(R.string.type_the_code),
                fontSize = 14.sp
            )
            VerificationCodeTextField(
                value = code,
                onValueChange = { newCode: String ->
                    code = newCode
                    codeState = codeStateValidation(code)
                    if(showFieldError) showFieldError = false
                },
                labelRes = stringResource(R.string.label_verification_code),
                error = codeState,
                apiError = showFieldError
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21), fontSize = 12.sp
                        )
                    ) { append(stringResource(R.string.didnt_receive)) }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(stringResource(R.string.send_again)) }
                }

            )
            if(showApiErrors){
                LabelError(labelRes = stringResource(id = apiErrorMessage))
            }
        }
        //Espaço no final
        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            VerificationCodeButton(
                onConfirmCodeClick = {
                    onConfirmResetPasswordClicked(code)
                },
                isCodeValid = codeState == CodeInputValid.Valid && !showLoading,
            )
        }
    }
    LaunchedEffect(key1 = validateCodeResult){
        when(validateCodeResult) {
            is ValidateCodeResult.Success -> {
                showApiErrors = false
                showLoading = false
                showFieldError = false
                navigateToSetNewPasswordScreen(code)
            }
            is ValidateCodeResult.Error -> {
                apiErrorMessage = validateCodeResult.message
                showApiErrors = true
                showLoading = false
                showFieldError = true
            }
            is ValidateCodeResult.DefaultError -> {
                apiErrorMessage = R.string.api_unexpected_error
                showApiErrors = true
                showLoading = false
                showFieldError = false
            }
            is ValidateCodeResult.Loading -> {
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
//fun VerificationCodeScreenPreview() {
//    RoutinelyTheme {
//        VerificationCodeScreen(
//            onConfirmResetPasswordClicked = {
//                CodeInputValid.Valid
//            },
//            navigateToSetNewPasswordScreen = {},
//            shouldGoToNextScreen = false,
//            apiErrorMessage = listOf(
////            "Email inválido"
//            ),
//        ) { code ->
//            CodeInputValid.Valid
//        }
//
//    }
//}