package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
@Composable
fun SignUpButton(onLoginClick: () -> Unit) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = stringResource(id = R.string.sign_up_text),
            color = PurpleRoutinely
        )
    }
}
