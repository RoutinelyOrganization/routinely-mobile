package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.routinely.routinely.ui.theme.Gray80
import com.routinely.routinely.ui.theme.GrayRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.validators.EmailInputValid

@Composable
fun LoginTextField(
    onValueChange: (String) -> Unit,
    labelRes: String,
    value: String,
    error: EmailInputValid,
    apiError: Boolean = false,
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= 35) onValueChange(newValue)
        },
        label = {
            Text(
                text = labelRes,
                style = TextStyle(color = Color.Black)
            )
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        isError = error is EmailInputValid.Error || apiError,
        supportingText = {
            if (error is EmailInputValid.Error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error.messageId),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
    )
}