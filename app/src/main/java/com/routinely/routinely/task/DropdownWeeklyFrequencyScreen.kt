package com.routinely.routinely.task

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.routinely.routinely.R
import com.routinely.routinely.ui.components.MonthHeader
import com.routinely.routinely.ui.theme.PurpleRoutinely
import com.routinely.routinely.ui.theme.lightGray
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DropdownWeeklyFrequencyScreen(
    onWeekDaysSelected: (List<String>) -> Unit
) {
    val daysOfWeek = listOf("D", "S", "T", "Q", "Q", "S", "S")
    var currentDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var selectedDays by rememberSaveable { mutableStateOf<Set<Int>>(emptySet()) }
    val formatter = DateTimeFormatter.ofPattern("MMMM", Locale("pt", "BR"))
    val monthName = formatter.format(currentDate)

    var isExpanded by remember { mutableStateOf(true) }

    LaunchedEffect(selectedDays) {
        val weekDays = selectedDays.map { index ->
            when (index) {
                0 -> "Sunday"
                1 -> "Monday"
                2 -> "Tuesday"
                3 -> "Wednesday"
                4 -> "Thursday"
                5 -> "Friday"
                6 -> "Saturday"
                else -> ""
            }
        }.filter { it.isNotEmpty() }
        onWeekDaysSelected(weekDays)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = "Frequência semanal",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
            Icon(
                painter = painterResource(
                    id = if (isExpanded) {
                        R.drawable.baseline_keyboard_arrow_up_24
                    } else {
                        R.drawable.baseline_keyboard_arrow_down_24
                    }
                ),
                contentDescription = "Expandir/Recolher",
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            )
        }

        if (isExpanded) {
            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = "Dias da semana",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                daysOfWeek.forEachIndexed { index, day ->
                    val isSelected = selectedDays.contains(index)
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) PurpleRoutinely else Color.Gray,
                                shape = CircleShape
                            )
                            .background(
                                color = if (isSelected) PurpleRoutinely else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                selectedDays = if (isSelected) {
                                    selectedDays - index
                                } else {
                                    selectedDays + index
                                }
                            }
                            .padding(1.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Left,
                            color = if (isSelected) Color.White else Color.Black,
                        )
                    }
                }
            }

            Text(
                modifier = Modifier.padding(horizontal = 5.dp),
                text = "Finaliza em:",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Column(
                modifier = Modifier
                    .background(lightGray)
                    .border(
                        border = BorderStroke(1.dp, Color.Blue),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Column(
                    Modifier.padding(10.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 25.dp),
                        horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            text = "${monthName.capitalize()} de ${currentDate.year}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = PurpleRoutinely,
                            textAlign = TextAlign.Center
                        )
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                                tint = PurpleRoutinely,
                                contentDescription = "Mês anterior",
                                modifier = Modifier.clickable {
                                    currentDate = currentDate.minusMonths(1)
                                }
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                tint = PurpleRoutinely,
                                contentDescription = "Próximo mês",
                                modifier = Modifier.clickable {
                                    currentDate = currentDate.plusMonths(1)
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    CalendarGrid(
                        currentDate = currentDate,
                        selectedDate = selectedDate,
                        onDateSelected = { date ->
                            selectedDate = date
                            currentDate = date
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun CalendarGrid(
    currentDate: LocalDate,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {

    val currentMonth = YearMonth.from(currentDate)

    val startMonth = remember { currentMonth.minusMonths(100) }
    val endMonth = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    LaunchedEffect(currentMonth) {
        state.scrollToMonth(currentMonth)
    }

    HorizontalCalendar(
        state = state,
        dayContent = { day ->
            val isSelected = selectedDate?.isEqual(day.date) == true
            Day(
                day = day,
                isSelected = isSelected,
                onClick = { onDateSelected(day.date) }
            )
        },
        monthHeader = { month ->
            Box(
                modifier = Modifier
                .background(
                    color = lightGray
                )
                .fillMaxWidth(0.85f)) {

            val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
            MonthHeader(daysOfWeek = daysOfWeek)
            }

        },
        monthBody = { _, content ->
            Box(
                modifier = Modifier
                    .background(
                        color = lightGray
                    )
                    .fillMaxWidth(0.85f)
            ) {
                content()
            }
        },
        monthContainer = { _, container ->
            val configuration = LocalConfiguration.current
            val screenWidth = configuration.smallestScreenWidthDp.dp

            Box(
                modifier = Modifier
                    .width(screenWidth)
                    .padding(0.dp)
                    .clip(shape = RoundedCornerShape(8.dp))

            ) {
                container()
            }
        }
    )
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (isSelected) PurpleRoutinely else Color.Transparent,
                shape = CircleShape
            )
            .clickable { onClick() }
            .wrapContentSize(Alignment.Center)
            .border(
                width = 1.dp,
                color = if (isSelected) PurpleRoutinely else Color.Transparent,
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = day.date.dayOfMonth.toString(),
            color = if (isSelected) Color.White else Color.Black,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeeklyFrequencyScreen() {
    DropdownWeeklyFrequencyScreen { _ -> }
}
