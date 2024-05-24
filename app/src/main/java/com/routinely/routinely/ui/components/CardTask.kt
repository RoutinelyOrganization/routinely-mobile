package com.routinely.routinely.ui.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.cardHabit
import com.routinely.routinely.ui.theme.cardProject
import com.routinely.routinely.ui.theme.cardTask
import com.routinely.routinely.ui.theme.categoryColor
import com.routinely.routinely.util.ActivityTag

@Composable
fun CardTask(
    title: String,
    description: String,
    category: Int,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val (emoji, cardBackgroundColor, border) = getCategoryAttributes(category)
    val categoryName = getCategoryName(category)

    Card(
        modifier = modifier
            .padding(vertical = 12.dp)
            .height(124.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, border),
        colors = CardDefaults.cardColors(cardBackgroundColor)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround

        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = emoji,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = categoryColor,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = description,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 2.dp)
                        .weight(0.5f),
                    maxLines = 2,

                    )
                CustomRadioButton(
                    modifier = modifier.padding(start = 10.dp),
                    selected = isSelected,
                    onSelected = onSelected,
                    selectedIcon =painterResource(id = R.drawable.baseline_check_circle_outline_24),
                    unselectedIcon = painterResource(id = R.drawable.baseline_radio_button_unchecked_24),
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = categoryColor,
                        selectedColor = categoryColor,
                        disabledSelectedColor = categoryColor,
                        disabledUnselectedColor = categoryColor
                    )
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .background(
                            color = categoryColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 4.dp)

                ) {
                    Text(
                        text = categoryName,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        color = Color.White,
                        )
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = PurpleRoutinely,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun CustomRadioButton(
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    colors: RadioButtonColors = RadioButtonDefaults.colors()
) {
    IconToggleButton(
        checked = selected,
        onCheckedChange = { onSelected(!selected) },
        modifier = modifier
            .width(24.dp)
            .height(24.dp)
    ) {
        Icon(
            painter = if (selected) selectedIcon else unselectedIcon,
            contentDescription = null,
            tint = if (selected) colors.selectedColor else colors.unselectedColor
        )

    }
}

fun getCategoryAttributes(category: Int): Triple<String, Color, Color> {
    return when (category) {
        ActivityTag.Task.stringId -> Triple("📋", cardTask, Color(0xFF115D9E))
        ActivityTag.Habit.stringId -> Triple("📌", cardHabit, Color(0xFF5450BC))
        ActivityTag.Project.stringId -> Triple("🚀", cardProject, Color(0xFF747400))
        ActivityTag.AllActivity.stringId -> Triple("✨", Color.Yellow, Color.Black)
        else -> Triple("🧷", Color.Gray, Color.Gray)
    }
}

fun getCategoryName(category: Int): String {
    return when (category) {
        ActivityTag.Task.stringId -> "Task"
        ActivityTag.Habit.stringId -> "Habit"
        ActivityTag.Project.stringId -> "Project"
        ActivityTag.AllActivity.stringId -> "All Activity"
        else -> "Unknown"
    }
}

@Preview(showBackground = true)
@Composable
private fun CardTaskPreview() {
    CardTask(title = "Title", description = "Description", isSelected = true, onSelected = {}, category = ActivityTag.Task.stringId)
}
