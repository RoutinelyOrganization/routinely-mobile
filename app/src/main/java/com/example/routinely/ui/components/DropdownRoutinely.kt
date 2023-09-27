package com.example.routinely.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.routinely.ui.theme.Gray80
import com.example.routinely.ui.theme.RoutinelyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownRoutinely(
    label: String,
    list: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val list = listOf(label) + list
    var selectedOptionText by remember { mutableStateOf(list[0]) }

    /*Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomEnd)
    ) {*/
        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                readOnly = true,
                value = selectedOptionText.takeIf { it.isNotEmpty() } ?: label,
                onValueChange = {},
                label = { Text(text = label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = Gray80,
                    unfocusedTextColor = Gray80,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray),
            )
            /*Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
            ) {*/
                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    list.forEachIndexed { index, selectionOption ->
                        val isGrayedOut = index == 0 // Primeiro item é cinza
                        DropdownMenuItem(
                            text = { Text(selectionOption, color = if (isGrayedOut) Color.Gray else Color.Black) },
                            onClick = {
                                if (!isGrayedOut) {
                                    selectedOptionText = selectionOption
                                }
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            enabled = !isGrayedOut, // Desativa o primeiro item
                        )
                    }
                }
            //}
        //}
    }
}
@Preview(showBackground = false)
@Composable
fun DropdownRoutinelyPreview() {
    RoutinelyTheme {
        DropdownRoutinely("Teste", listOf("Opção 1", "Opção 2"))
    }
}