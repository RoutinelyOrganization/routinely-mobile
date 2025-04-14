package com.routinely.routinely.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.categoryColor
import com.routinely.routinely.util.ActivityTag
import com.routinely.routinely.util.TaskCategoryNew

@Composable
fun CardTask(
    title: String,
    description: String,
    taskType: Int,
    category: Int,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (emoji, cardBackgroundColor, border) = getCategoryAttributes(taskType)
    val taskTypeText = when (taskType) {
        ActivityTag.Task.stringId -> "Atividade"
        ActivityTag.Habit.stringId -> "Hábitos"
        else -> "Atividade"
    }

    val categoryText = when (category) {
        1 -> TaskCategoryNew.Career.name
        2 -> TaskCategoryNew.Finances.name
        3 -> TaskCategoryNew.Studies.name
        4 -> TaskCategoryNew.Health.name
        5 -> TaskCategoryNew.Leisure.name
        6 -> TaskCategoryNew.Productivity.name
        else -> TaskCategoryNew.Several.name
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, border),
        colors = CardDefaults.cardColors(cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = emoji,
                    fontSize = 16.sp
                )
                Text(
                    text = taskTypeText,
                    fontSize = 14.sp,
                    color = categoryColor,
                    fontWeight = FontWeight.Medium
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom

                ) {
                Column(
                    modifier = Modifier.weight(1f)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "00:00",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2
                    )
                }
                CustomRadioButton(
                    selected = isSelected,
                    onSelected = onSelected,
                    selectedIcon = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                    unselectedIcon = painterResource(id = R.drawable.baseline_radio_button_unchecked_24),
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = categoryColor,
                        selectedColor = categoryColor,
                        disabledSelectedColor = categoryColor,
                        disabledUnselectedColor = categoryColor
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = categoryColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = categoryText,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal
                    )
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = PurpleRoutinely,
                    modifier = Modifier.size(24.dp)
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
    colors: RadioButtonColors = RadioButtonDefaults.colors(),
) {
    IconToggleButton(
        checked = selected,
        onCheckedChange = { onSelected(!selected) },
        modifier = modifier.size(24.dp)

        ) {
        Icon(
            painter = if (selected) selectedIcon else unselectedIcon,
            contentDescription = null,
            tint = if (selected) colors.selectedColor else colors.unselectedColor,
            modifier = modifier.fillMaxSize()
        )

    }
}

data class CardCategory(
    val icon: String,
    val cardColor: Color,
    val cardBorder: Color,
    val originalCategoryName: String,
)

fun getCategoryAttributes(taskType: Int): CardCategory {
    val defaultBorderColor = Color.Gray

    val backgroundColor = when (taskType) {
        ActivityTag.Task.stringId -> Color(0xFFD1EAFF)
        ActivityTag.Habit.stringId -> Color(0xF1E0DFFF)
        else -> Color.Transparent
    }

    val borderColor = when (taskType) {
        ActivityTag.Task.stringId -> Color(0xFF115D9E)
        ActivityTag.Habit.stringId -> Color(0xFF5450BC)
        else -> defaultBorderColor
    }

    val icon = when (taskType) {
        ActivityTag.Task.stringId -> "📋"
        ActivityTag.Habit.stringId -> "📌"
        else -> "❓"
    }

    val originalCategoryName = getCategoryName(taskType)
    return CardCategory(icon, backgroundColor, borderColor, originalCategoryName)
}

fun getCategoryName(category: Int): String {
    return when (category) {
        ActivityTag.Task.stringId -> "Tarefa"
        ActivityTag.Habit.stringId -> "Hábito"
        else -> "Desconhecido"
    }
}

@Preview(showBackground = true)
@Composable
private fun CardTaskPreview() {
    CardTask(
        title = "Titulo",
        description = "Description",
        isSelected = true,
        category = 4,
        onSelected = {},
        taskType = ActivityTag.Habit.stringId
    )
}
