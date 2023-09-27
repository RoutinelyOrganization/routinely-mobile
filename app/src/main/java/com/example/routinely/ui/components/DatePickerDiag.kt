package com.example.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager

@ExperimentalMaterial3Api
@Composable
fun DatePickerDiag(
        label: String,
        modifier: Modifier = Modifier
    ) {
    var isSupportingText by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }
    val datePickerState = rememberDatePickerState(yearRange = 2013..2023)
    var selectedDate by remember {
        mutableStateOf("")
    }
    val confirmEnabled by remember { derivedStateOf { datePickerState.selectedDateMillis != null } }
    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState
                            .selectedDateMillis?.let { millis ->
                                selectedDate = millis.toBrazilianDateFormat()
                            }
                        showDatePickerDialog = false
                    },
                    enabled = confirmEnabled
                ) {
                    Text(text = "Confirma")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDatePickerDialog = false }) {
                    Text(text = "Cancela")
                }
            },
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
        }
    }

    OutlinedTextField(
        onValueChange = { },
        value = selectedDate.takeIf { it.isNotEmpty() } ?: "__/__/____",
        label = { Text(text = label) },
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
        modifier = modifier
            .onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        readOnly = true,
    )
}
