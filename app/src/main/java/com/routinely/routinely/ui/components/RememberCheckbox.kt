package com.routinely.routinely.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R

@Composable
fun RememberCheckbox(
    isChecked: Boolean,
    onCheckChange : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable {
               onCheckChange()
            }
    )
    {
        Checkbox(
            checked = isChecked, onCheckedChange = null,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xff8f8ce7),
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White
            ),
        )
        Text(
            fontSize = 12.sp,
            text = stringResource(id = R.string.remember_login),
            color = Color.Black
        )
    }
}