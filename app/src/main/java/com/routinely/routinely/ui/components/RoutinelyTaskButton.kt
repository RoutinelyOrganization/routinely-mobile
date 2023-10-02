package com.routinely.routinely.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun RoutinelyTaskButton(
    onClick: () -> Unit,
    modifier: Modifier,
    buttonColor: ButtonColors,
    textRes: Int,
    textColor: Color,
    borderStroke: BorderStroke

    ) {
    val isButtonEnabled = true
    OutlinedButton(
        enabled = isButtonEnabled,
        onClick = onClick,
        modifier = modifier,
        colors = buttonColor,
        shape = MaterialTheme.shapes.small,
        border = borderStroke
    ) {
        Text(
            text = stringResource(textRes), color = textColor
        )
    }
}