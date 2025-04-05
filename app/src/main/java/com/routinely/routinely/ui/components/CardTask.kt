package com.routinely.routinely.ui.components

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextDecoration
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
import com.routinely.routinely.util.TaskCategory

@Composable
fun CardTask(
    activityTag: Int,
    description: String,
    categoryTask: Int,
    isSelected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val (defaultEmoji, defaultBackgroundColor, defaultBorderColor) = getActivityAttributes(
        activityTag
    )
    val (selectedEmoji, selectedBackgroundColor, selectedBorderColor) = getSelectedActivityAttributes(
        activityTag
    )

    val (defaultActivity, defaultBackgroundActivityColor) = getActivityAttributes( activityTag )
    val (emojiActivity, backgroundActivityColor) = getSelectedActivityAttributes( activityTag )


    val emoji = if (isSelected) emojiActivity else defaultActivity
    val cardBackgroundColor = if (isSelected) backgroundActivityColor else defaultBackgroundActivityColor
    val borderColor = if (isSelected) selectedBorderColor else defaultBorderColor


    Card(
        modifier = modifier
            .padding(vertical = 12.dp)
            .height(124.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, borderColor),
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
                    text = getActivityName(activityTag),
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
                    style = MaterialTheme.typography.bodyLarge.copy(
                        textDecoration = if (isSelected) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                CustomRadioButton(
                    modifier = modifier.padding(start = 10.dp),
                    selected = isSelected,
                    onSelected = {
                        onSelected(it)
                    },
                    selectedIcon = painterResource(id = R.drawable.baseline_check_circle_outline_24),
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
                        text = getCategoryName(categoryTask),
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

data class CardCategory(
    val icon: String,
    val cardColor: Color,
    val cardBorder: Color,
    val checked: Int
)


fun darkenColor(color: Color, factor: Float = 0.8f): Color {
    return Color(
        red = (color.red * factor).coerceIn(0f, 1f),
        green = (color.green * factor).coerceIn(0f, 1f),
        blue = (color.blue * factor).coerceIn(0f, 1f),
        alpha = color.alpha
    )
}

fun getSelectedActivityAttributes(activityTag: Int): CardCategory {
    val selectedBackgroundColor = when (activityTag) {
        ActivityTag.Task.stringId -> darkenColor(cardTask, 0.6f)  // Darker color for selected state
        ActivityTag.Habit.stringId -> darkenColor(cardHabit, 0.6f)
        ActivityTag.Project.stringId -> darkenColor(cardProject, 0.6f)
        else -> Color.LightGray
    }

    val selectedBorderColor = when (activityTag) {
        ActivityTag.Task.stringId -> Color(0xFF002D5E)  // Darker border for selected state
        ActivityTag.Habit.stringId -> Color(0xFF303080)
        ActivityTag.Project.stringId -> Color(0xFF505000)
        else -> Color.Gray
    }

    val selectedIcon = when (activityTag) {
        ActivityTag.Task.stringId -> "✅"
        ActivityTag.Habit.stringId -> "🎯"
        ActivityTag.Project.stringId -> "🚀"
        else -> "❓"
    }

    return CardCategory(
        selectedIcon,
        selectedBackgroundColor,
        selectedBorderColor,
        activityTag
    )
}

fun getActivityAttributes(activityTag: Int, isSelected: Boolean = false): CardCategory {
    val defaultBackgroundColor = when (activityTag) {
        ActivityTag.Task.stringId -> cardTask
        ActivityTag.Habit.stringId -> cardHabit
        ActivityTag.Project.stringId -> cardProject
        else -> Color.LightGray
    }

    val defaultBorderColor = when (activityTag) {
        ActivityTag.Task.stringId -> Color(0xFF115D9E)
        ActivityTag.Habit.stringId -> Color(0xFF5450BC)
        ActivityTag.Project.stringId -> Color(0xFF747400)
        else -> Color.Gray
    }

    val backgroundColor = if (isSelected) darkenColor(defaultBackgroundColor, 0.6f) else defaultBackgroundColor
    val borderColor = if (isSelected) darkenColor(defaultBorderColor, 0.6f) else defaultBorderColor

    val icon = when (activityTag) {
        ActivityTag.Task.stringId -> "📋"
        ActivityTag.Habit.stringId -> "📌"
        ActivityTag.Project.stringId -> "🚀"
        else -> "❓"
    }

    val activityName = getActivityName(activityTag)

    return CardCategory(icon, backgroundColor, borderColor, activityTag)
}

fun getActivityName(activity: Int): String {
    return when (activity) {
        ActivityTag.Task.stringId -> "Task"
        ActivityTag.Habit.stringId -> "Habit"
        ActivityTag.Project.stringId -> "Project"
        else -> "Unknown"
    }
}

fun getCategoryName(category: Int): String {
    return when (category) {
        TaskCategory.Personal.stringId -> "Personal"
        TaskCategory.Career.stringId -> "Career"
        TaskCategory.Health.stringId -> "Health"
        TaskCategory.Studies.stringId -> "Studies"
        TaskCategory.Finances.stringId -> "Finances"
        else -> "Unknown"
    }
}


@Preview(showBackground = true)
@Composable
private fun CardTaskPreview() {
    CardTask(
        activityTag = ActivityTag.Task.stringId,
        description = "Description",
        isSelected = false,
        onSelected = {},
        categoryTask = TaskCategory.Career.stringId
    )
}
