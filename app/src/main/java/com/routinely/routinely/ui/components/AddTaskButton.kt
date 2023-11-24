package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely

@Composable
fun AddTaskButton(
    onAddTaskClick: () -> Unit,
    areFieldsValid: Boolean
) {
    Button(
        enabled = areFieldsValid,
        onClick = onAddTaskClick,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(PurpleRoutinely),
        shape = MaterialTheme.shapes.small,
    ) {
        Text(
            text = stringResource(id = R.string.title_add_task), color = Color.White
        )
    }
}