package com.routinely.routinely.ui.components

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

fun validateTaskName(value: TextFieldValue, maxLength: Int): TextFieldValue {
    if (value.text.length >= maxLength) {
        return TextFieldValue(
            text = value.text.take(maxLength),
            selection = TextRange(maxLength)
        )
    }
    return value
}