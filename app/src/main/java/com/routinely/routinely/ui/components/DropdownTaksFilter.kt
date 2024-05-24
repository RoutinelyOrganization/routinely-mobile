package com.routinely.routinely.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.ui.theme.GrayRoutinely
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.lightGray
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.TaskFields
import com.routinely.routinely.util.TaskTag

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownTaskFilter(
    labelRes: Int,
    onValueChange: (Int) -> Unit,
    list: List<TaskFields>,
    modifier: Modifier = Modifier,
    option: Int? = null
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val mapStringToId: Map<String, Int> = list.associate { tag ->
        context.getString(tag.stringId) to tag.stringId
    }

    val labelResAsString = stringResource(id = labelRes)
    var selectedOptionText by remember { mutableStateOf(labelResAsString) }

    option?.let {
        selectedOptionText = stringResource(id = option)
    }


    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = {
                Text(
                    text = labelResAsString,
                    style = TextStyle(color = PurpleRoutinely),
                    fontSize = 16.sp,
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = lightGray,
                focusedContainerColor = lightGray,
                focusedTextColor = Color.Blue,
                unfocusedTextColor = Color.Blue,
                focusedBorderColor = GrayRoutinely,
                unfocusedBorderColor = GrayRoutinely
            ),
            textStyle = TextStyle(color = Color.Black)
        )

        ExposedDropdownMenu(
            modifier = modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        labelResAsString,
                        color = Color.Blue
                    )
                },
                onClick = { },
                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                enabled = false,
            )

            mapStringToId.forEach { (string, id) ->

                DropdownMenuItem(
                    text = {
                        Text(
                            string,
                            color = Color.DarkGray
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


@Preview(showBackground = true)
@Composable
private fun TaskFilterRoutinelyPreview() {
    var selectedTasktag by remember { mutableIntStateOf(ActivityTag.AllActivity.stringId) }
    DropdownTaskFilter(
        labelRes = selectedTasktag,
        onValueChange = { newTask ->
            selectedTasktag = newTask
        },
        list = TaskFields.getAllOptions<TaskTag>()
    )

}