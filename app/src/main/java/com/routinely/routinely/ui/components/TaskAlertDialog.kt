package com.routinely.routinely.ui.components

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.containerBorder
import com.routinely.routinely.ui.theme.textGrayColor

@Composable
fun TaskAlertDialog(
    textRes: Int,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        modifier = Modifier.shadow(3.dp),
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(8.dp),
        title = { Text(text = stringResource(textRes),
            fontSize = 16.sp) },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.width(1.dp))

                    RoutinelyTaskButton(
                        onClick = onConfirm,
                        textRes = R.string.yes,
                        textColor = Color.White,
                        buttonColor = ButtonDefaults.buttonColors(PurpleRoutinely),
                        modifier = Modifier.width(104.dp).height(40.dp),
                        borderStroke = BorderStroke(1.dp, PurpleRoutinely),
                        enabled = true,
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    RoutinelyTaskButton(
                        onClick = onCancel,
                        textRes = R.string.no,
                        textColor = textGrayColor,
                        buttonColor = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent),
                        modifier = Modifier.width(104.dp).height(40.dp),
                        borderStroke = BorderStroke(1.dp, containerBorder),
                        enabled = true,
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {},
    )
}

@Composable
fun ConfirmTaskAlertDialog(
    textRes: Int,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        containerColor = Color.White,
        modifier = Modifier.shadow(3.dp),
        onDismissRequest = onConfirm,
        shape = RoundedCornerShape(8.dp),
        title = { Text(text = stringResource(textRes),
            fontSize = 16.sp) },
        text = {
            Column {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RoutinelyTaskButton(
                        onClick = onConfirm,
                        textRes = R.string.ok,
                        textColor = Color.White,
                        buttonColor = ButtonDefaults.buttonColors(PurpleRoutinely),
                        modifier = Modifier.width(104.dp).height(40.dp),
                        borderStroke = BorderStroke(1.dp, PurpleRoutinely),
                        enabled = true,
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
