package com.example.routinely.ui.components

import android.app.TimePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import java.util.Locale


@ExperimentalMaterial3Api
@Composable
fun TimePickerDiag(
    label: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

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
            timePickerDialogListener,
            selectedHour,
            selectedMinute,
            true
        ).show()
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