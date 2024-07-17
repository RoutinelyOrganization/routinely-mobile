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

@Composable
fun CardTask(
    title: String,
    description: String,
    category: Int,
    isSelected: Boolean,
    originalCategory: String?,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val (emoji, cardBackgroundColor, border) = getCategoryAttributes(category,originalCategory?.toInt())
    val categoryAttributes = getCategoryAttributes(category)


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
                        text = categoryAttributes.originalCategoryName,
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
    val originalCategoryName: String,
    val isSelected: Int
)


fun darkenColor(color: Color, factor: Float = 0.8f): Color {
    return Color(
        red = (color.red * factor).coerceIn(0f, 1f),
        green = (color.green * factor).coerceIn(0f, 1f),
        blue = (color.blue * factor).coerceIn(0f, 1f),
        alpha = color.alpha
    )
}
fun getCategoryAttributes(category: Int,originalCategory: Int? = null): CardCategory {
    val defaultBorderColor = Color.Gray
    val darkenFactor = 0.8f

    val backgroundColor = when {
        category == ActivityTag.Completed.stringId && originalCategory != null -> {
            val originalColor = when (originalCategory) {
                ActivityTag.Task.stringId -> cardTask
                ActivityTag.Habit.stringId -> cardHabit
                ActivityTag.Project.stringId -> cardProject
                else -> Color.Transparent
            }
            darkenColor(originalColor, darkenFactor)
        }
        category == ActivityTag.Task.stringId -> cardTask
        category == ActivityTag.Habit.stringId -> cardHabit
        category == ActivityTag.Project.stringId -> cardProject
        category == ActivityTag.Completed.stringId -> darkenColor(Color.Red, darkenFactor)
        else -> Color.Transparent
    }

    val borderColor = when (category) {
        ActivityTag.Task.stringId -> Color(0xFF115D9E)
        ActivityTag.Habit.stringId -> Color(0xFF5450BC)
        ActivityTag.Project.stringId -> Color(0xFF747400)
        ActivityTag.Completed.stringId -> Color.Gray
        else -> defaultBorderColor
    }

    val icon = when (originalCategory ?: category) {
        ActivityTag.Task.stringId -> "📋"
        ActivityTag.Habit.stringId -> "📌"
        ActivityTag.Project.stringId -> "🚀"
        ActivityTag.Completed.stringId -> "✔️"
        else -> "❓"
    }

    val originalCategoryName = getCategoryName(category)

    return CardCategory(icon, backgroundColor, borderColor, originalCategoryName, category)

}

fun getCategoryName(category: Int): String {
    return when (category) {
        ActivityTag.Task.stringId -> "Task"
        ActivityTag.Habit.stringId -> "Habit"
        ActivityTag.Project.stringId -> "Project"
        ActivityTag.AllActivity.stringId -> "All Activity"
        else -> "Unknow"
    }
}


@Preview(showBackground = true)
@Composable
private fun CardTaskPreview() {
    CardTask(
        title = "Title",
        description = "Description",
        isSelected = true,
        originalCategory = null,
        onSelected = {},
        category = ActivityTag.Task.stringId
    )
}
