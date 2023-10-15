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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.VerificationCodeButton
import com.routinely.routinely.ui.components.VerificationCodeTextField
import com.routinely.routinely.ui.theme.RoutinelyTheme
import com.routinely.routinely.util.validators.CodeInputValid
import kotlinx.coroutines.delay

@Composable
fun VerificationCodeScreen(
    onConfirmResetPasswordClicked: (String) -> CodeInputValid,
    shouldGoToNextScreen: Boolean,
    navigateToSetNewPasswordScreen: () -> Unit,
    codeStateValidation: (code: String) -> CodeInputValid,
) {
    var code by rememberSaveable { mutableStateOf("") }
    var codeState by rememberSaveable { mutableStateOf<CodeInputValid>(CodeInputValid.Empty) }

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
                contentDescription = "Image",
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
                },
                labelRes = "Código",
                error = codeState,
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21), fontSize = 12.sp
                        )
                    ) { append(stringResource(R.string.didnt_receive)) }
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xff171a21),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(stringResource(R.string.send_again)) }
                }

            )
        }
        //Espaço no final
        Column(
            modifier = Modifier
                .weight(0.15f)
        ) {
            VerificationCodeButton(
                onConfirmCodeClick = {
                    onConfirmResetPasswordClicked(
                        code
                    )
                },
                isCodeValid = codeState == CodeInputValid.Valid,
            )
        }
    }
    LaunchedEffect(key1 = shouldGoToNextScreen) {
        if(shouldGoToNextScreen) {
            delay(2000)
            navigateToSetNewPasswordScreen()
        }
    }
}
@Preview(showBackground = true)
@Composable
fun VerificationCodeScreenPreview() {
    RoutinelyTheme {
        VerificationCodeScreen(
            onConfirmResetPasswordClicked = {
                CodeInputValid.Valid
            },
            navigateToSetNewPasswordScreen = {},
            shouldGoToNextScreen = false
        ) { code ->
            CodeInputValid.Valid
        }
    }
}