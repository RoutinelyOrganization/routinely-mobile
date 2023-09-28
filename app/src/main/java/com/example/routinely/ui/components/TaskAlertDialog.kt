package com.example.routinely.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.routinely.R
import com.example.routinely.ui.theme.PurpleRoutinely
import com.example.routinely.ui.theme.RedRoutinely

@Composable
fun TaskAlertDialog(
    textRes: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(8.dp),
        title = { Text(text = stringResource(textRes)) },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RoutinelyTaskButton(
                        onClick = onDismissRequest,
                        textRes = R.string.cancel,
                        textColor = Color.Gray,
                        buttonColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                        modifier = Modifier.weight(2f),
                        borderStroke = BorderStroke(1.dp, Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RoutinelyTaskButton(
                        onClick = onCancel,
                        textRes = R.string.no,
                        textColor = RedRoutinely,
                        buttonColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                        modifier = Modifier,
                        borderStroke = BorderStroke(1.dp, Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RoutinelyTaskButton(
                        onClick = onConfirm,
                        textRes = R.string.yes,
                        textColor = Color.White,
                        buttonColor = ButtonDefaults.buttonColors(PurpleRoutinely),
                        modifier = Modifier,
                        borderStroke = BorderStroke(1.dp, PurpleRoutinely)
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TaskAlertDialogPreview() {
    TaskAlertDialog(
        textRes = R.string.delete_task_confirmation,
        onConfirm = {},
        onCancel = {},
        onDismissRequest = {}
    )
}
