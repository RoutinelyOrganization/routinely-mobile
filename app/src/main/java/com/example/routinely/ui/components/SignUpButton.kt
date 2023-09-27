package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.routinely.ui.theme.BlueRoutinely
@Composable
fun SignUpButton(onLoginClick: () -> Unit) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        onClick = onLoginClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp),
        //.border(1.dp, Color.Gray), // Adicione a borda aqui
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = "Cadastre-se",
            color = BlueRoutinely
        )
    }
    /*
    OutlinedButton(
        onClick = {
            // Ação executada quando o botão é clicado
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.LightGray),
        contentPadding = PaddingValues(16.dp)
        //colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(
            text = "Cadastre-se", color = BlueRoutinely
        )
    }
    */
}