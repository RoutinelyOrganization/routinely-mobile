package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
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
import com.routinely.routinely.util.validators.DescriptionInputValid

@Composable
fun DescriptionTextField(
    value : String,
    onValueChange: (String) -> Unit,
    labelRes: String,
    error: DescriptionInputValid,
) {

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = labelRes,
                style = TextStyle(color = Color.Black)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
        isError = error is DescriptionInputValid.Error,
        supportingText = {
            if(error is DescriptionInputValid.Error) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = error.messageId),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = {
            if (error is DescriptionInputValid.Error) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier

                )
            }
        },
        singleLine = false,
        minLines = 3,
        maxLines = 4,
        modifier = Modifier
            .fillMaxWidth()
    )
}