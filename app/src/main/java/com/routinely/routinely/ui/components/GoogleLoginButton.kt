package com.routinely.routinely.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely

@Composable
fun GoogleLoginButton() {
    Button(
        onClick = {
            // Ação executada quando o botão é clicado
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 68.dp, top = 24.dp)
        ,
        colors = ButtonDefaults.buttonColors(Color.White),
        border = BorderStroke(width = 1.dp, PurpleRoutinely),
        shape = MaterialTheme.shapes.small,
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_google),
            contentDescription = "Vector",
            modifier = Modifier
                .padding(end = 22.dp)
                .height(24.dp)
                .width(24.dp)
        )
        Text(
            text = stringResource(id = R.string.login_with_google),
            color = Color.DarkGray,
        )
    }
}