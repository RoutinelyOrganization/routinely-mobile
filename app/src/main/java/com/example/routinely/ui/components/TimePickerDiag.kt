package com.example.routinely.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.routinely.ui.theme.Gray80
import com.example.routinely.ui.theme.GrayRoutinely
import com.example.routinely.ui.theme.PurpleRoutinely

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    label: String,
    modifier: Modifier = Modifier
) {
    var selectedHour by remember {  mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }
    val timePickerState = rememberTimePickerState(
        initialHour = selectedHour,
        initialMinute = selectedMinute,
        is24Hour = true
    )
    val openAlertDialog = remember { mutableStateOf(false) }
    when {
        openAlertDialog.value -> {
            val clearFocus = LocalFocusManager.current.clearFocus();
            AlertDialog(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 12.dp)
                    ),
                onDismissRequest = {
                    openAlertDialog.value = false
                    clearFocus
                }
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = Color.LightGray.copy(alpha = 0.3f)
                        )
                        .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TimeInput(
                        state = timePickerState,
                    )
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { openAlertDialog.value = false }) {
                            Text(text = "Fechar")
                        }
                        TextButton(
                            onClick = {
                                openAlertDialog.value = false
                                selectedHour = timePickerState.hour
                                selectedMinute = timePickerState.minute
                            }
                        ) {
                            Text(text = "Selecionar")
                        }
                    }
                }
            }
        }
    }
    var isSupportingText by remember { mutableStateOf(false) }
    var timeText by remember { mutableStateOf("") }
    OutlinedTextField(
        value = timeFormatter(selectedHour.toString(), selectedMinute.toString()),
        label = {
            Text(
                text = label,
                style = TextStyle(color = Color.Black)
            )
        },
        onValueChange = {
            timeText = it
        },
        modifier = modifier
            .onFocusEvent {
                if (it.isFocused) {
                    openAlertDialog.value = true
                }
            }
            .fillMaxWidth(),
        isError = isSupportingText,
        supportingText = {
            if (isSupportingText) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Formato inválido!",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Gray80,
            unfocusedTextColor = Gray80,
            focusedBorderColor = PurpleRoutinely,
            unfocusedBorderColor = GrayRoutinely
        ),
        singleLine = true,
        readOnly = true,
    )
}
fun timeFormatter(hourParam: String, minuteParam: String): String {
    var hour = ""
    var minute = ""
    if (hourParam.length == 1) {
        hour = "0$hourParam"
    }else {
        hour = hourParam
    }
    if (minuteParam.length == 1) {
        minute = "0$minuteParam"
    }else{
        minute = minuteParam
    }
    return "$hour:$minute"
}
