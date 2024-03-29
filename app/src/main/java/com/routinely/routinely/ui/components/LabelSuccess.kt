package com.routinely.routinely.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LabelSuccess(
    labelRes: String,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = labelRes,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun LabelSuccess() {

}