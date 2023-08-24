package com.example.routinely.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RememberCheckbox() {
    val checkboxState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .clickable {checkboxState.value = !checkboxState.value }
    )
    {
        Checkbox(
            checked = checkboxState.value, onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xff8f8ce7),
                uncheckedColor = Color.White,
                checkmarkColor = Color.White
            ),
        )
        Text(
            text = "Lembrar meu acesso",
            color = Color.White
        )
    }
}