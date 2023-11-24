package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.Gray80
import com.routinely.routinely.ui.theme.GrayRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.util.TaskFields
import com.routinely.routinely.util.TaskPriorities
import com.routinely.routinely.util.TaskTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownRoutinely(
    labelRes: Int,
    onValueChange: (Int) -> Unit,
    list: List<TaskFields>,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    val labelResAsString = stringResource(id = labelRes)
    val mapStringToId : Map<String, Int> = list.associate { tag ->
        stringResource(id = tag.stringId) to tag.stringId
    }

    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {  },
            label = {
                Text(
                    text = labelResAsString,
                    style = TextStyle(color = Color.Black)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Gray80,
                unfocusedTextColor = Gray80,
                focusedBorderColor = PurpleRoutinely,
                unfocusedBorderColor = GrayRoutinely
            ),
            textStyle = TextStyle(color = Color.Black)
        )

        ExposedDropdownMenu(
            modifier = modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        labelResAsString,
                        color = Gray
                    )
                },
                onClick = { },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                enabled = false
            )

            mapStringToId.forEach { (string, id) ->

                DropdownMenuItem(
                    text = {
                        Text(
                            string,
                            color = DarkGray
                        )
                    },
                    onClick = {
                        selectedOptionText = string
                        onValueChange(id)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    enabled = true
                )
            }
        }
    }
}

/**
 * Dropdown for list of priorities, the text change color when selected
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownRoutinelyPriorities(
    labelRes: Int,
    onValueChange: (Int) -> Unit,
    list: List<TaskPriorities>,
    modifier: Modifier = Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val labelAsString = stringResource(id = labelRes)

    val mapStringToId : Map<String, Int> = list.associate { tag ->
        stringResource(id = tag.stringId) to tag.stringId
    }

    var selectedOptionText by remember { mutableStateOf("") }

    val stringColorMap: Map<String, Color> = list.associate {
        stringResource(it.stringId) to it.textColor
    }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {  },
            label = {
                Text(
                    text = labelAsString,
                    style = TextStyle(color = Color.Black)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Gray80,
                unfocusedTextColor = Gray80,
                focusedBorderColor = PurpleRoutinely,
                unfocusedBorderColor = GrayRoutinely
            ),
            textStyle = TextStyle(color = stringColorMap[selectedOptionText] ?: Color.Black)
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {

            DropdownMenuItem(
                text = {
                    Text(
                        labelAsString,
                        color = Gray
                    )
                },
                onClick = { },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                enabled = false
            )

            mapStringToId.forEach { (string, id) ->
                DropdownMenuItem(
                    text = {
                        Text(
                            string,
                            color = DarkGray
                        )
                    },
                    onClick = {
                        selectedOptionText = string
                        onValueChange(id)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    enabled = true
                )
            }
        }
    }
}


@Preview
@Composable
private fun DropdownRoutinelyPreview() {
    DropdownRoutinely(labelRes = R.string.label_tag_dropdown, onValueChange = {}, list = TaskFields.getAllOptions<TaskTag>())
}

@Preview
@Composable
private fun DropdownRoutinelyWithColorPreview() {
    DropdownRoutinelyPriorities(labelRes = R.string.label_priority_dropdown, onValueChange = {}, list = TaskPriorities.getAllTaskPriorities())
}