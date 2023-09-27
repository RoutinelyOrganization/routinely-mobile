package com.example.routinely.ui.components

import android.app.TimePickerDialog
import android.graphics.drawable.Icon
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import java.util.Locale

//Não está sendo utilizado por enquanto
@ExperimentalMaterial3Api
@Composable
fun TimePickerDiag(
    label: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var selectedHour by remember { mutableIntStateOf(0) }
    var selectedMinute by remember { mutableIntStateOf(0) }

    var selectedTime by remember {
        mutableStateOf("")
    }

    var showTimePickerDialog by remember {
        mutableStateOf(false)
    }

    val timePickerDialogListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        selectedHour = hourOfDay
        selectedMinute = minute
        selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
        showTimePickerDialog = false
    }

    if (showTimePickerDialog) {
        TimePickerDialog(
            context,
            0,
            timePickerDialogListener,
            selectedHour,
            selectedMinute,
            true
        ).also { dialog ->
            dialog.setOnCancelListener {
                showTimePickerDialog = false
            }
            dialog.show()
        }
    }



    OutlinedTextField(
        onValueChange = { },
        value = selectedTime.takeIf { it.isNotEmpty() } ?: "__:__",
        label = { Text(text = label) },
        modifier = modifier
            .onFocusEvent { state ->
                if (state.isFocused) {
                    showTimePickerDialog = true
                    focusManager.clearFocus()
                }
            },
        readOnly = true,
    )
}

@Composable
fun CustomTimePicker(
    selectedHour: Int,
    selectedMinute: Int,
    onTimeSelected: (Int, Int) -> Unit
) {
    var hour by remember { mutableIntStateOf(selectedHour) }
    var minute by remember { mutableIntStateOf(selectedMinute) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Hora:")
            Text(text = String.format("%02d:%02d", hour, minute))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomNumberPicker(
                value = hour,
                onValueChange = { newHour ->
                    hour = newHour
                    onTimeSelected(hour, minute)
                },
                minValue = 0,
                maxValue = 23
            )

            CustomNumberPicker(
                value = minute,
                onValueChange = { newMinute ->
                    minute = newMinute
                    onTimeSelected(hour, minute)
                },
                minValue = 0,
                maxValue = 59
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Minuto:")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomNumberPicker(
                value = minute,
                onValueChange = { newHour ->
                    minute = newHour
                    onTimeSelected(hour, minute)
                },
                minValue = 0,
                maxValue = 59
            )

            CustomNumberPicker(
                value = minute,
                onValueChange = { newMinute ->
                    minute = newMinute
                    onTimeSelected(hour, minute)
                },
                minValue = 0,
                maxValue = 59
            )
        }
    }
}

@Composable
fun CustomNumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    minValue: Int,
    maxValue: Int
) {
    var currentNumber by remember { mutableStateOf(value) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = {
                if (currentNumber > minValue) {
                    currentNumber--
                    onValueChange(currentNumber)
                }
            }
        ) {
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
        }

        Text(
            text = currentNumber.toString(),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = TextStyle(fontSize = 24.sp)
        )

        IconButton(
            onClick = {
                if (currentNumber < maxValue) {
                    currentNumber++
                    onValueChange(currentNumber)
                }
            }
        ) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }
}
@Composable
fun TimePickerDialog(
    selectedHour: Int,
    selectedMinute: Int,
    onTimeSelected: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        content = {
            Surface(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    CustomTimePicker(
                        selectedHour = selectedHour,
                        selectedMinute = selectedMinute,
                        onTimeSelected = { hour, minute ->
                            onTimeSelected(hour, minute)
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(text = "Selecionar")
                    }
                }
            }
        }
    )
}