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
import com.routinely.routinely.util.validators.NameInputValid

@Composable
fun NameTextField(
    value : String,
    onValueChange: (String) -> Unit,
    labelRes: String,
    error: NameInputValid,
) {

    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(
                text = labelRes,
                style = TextStyle(color = Color.Black)
            )
        },
        singleLine = true,
        isError = error is NameInputValid.Error,
        supportingText = {
            if(error is NameInputValid.Error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error.messageId),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
    )
}