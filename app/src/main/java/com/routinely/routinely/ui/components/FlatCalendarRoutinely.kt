package com.routinely.routinely.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.OutDateStyle
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.routinely.routinely.R
import com.routinely.routinely.ui.theme.categoryColor
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarRoutinely(
    state: WeekCalendarState,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit
    ) {

    var currentDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("MMMM", Locale("pt", "BR"))
    val monthName = formatter.format(currentDate)

    Column {

        val currentMonth = remember { YearMonth.now() }
        val startDate = remember { currentMonth.minusMonths(100).atStartOfMonth() }
        val endDate = remember { currentMonth.plusMonths(100).atEndOfMonth() }
        val firstDayOfWeek = remember { firstDayOfWeekFromLocale() }


        val weekCalendarState = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
            firstDayOfWeek = firstDayOfWeek
        )
        Text(
            text = "${currentDate.dayOfMonth} de $monthName de ${currentDate.year}",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 12.dp, top = 24.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        WeekCalendar(
            state = weekCalendarState,
            dayContent = {weekDay ->
                WeekDay(
                    weekDay = weekDay,
                    currentDate = currentDate,
                    onDialogOpen = { showAlertDialog = true},
                    newDate = { newDate ->
                        currentDate = newDate
                        onDateSelected(newDate)
                    }
                )
            }
        )
        if (showAlertDialog) {
            CustomAlertDialog(
                initialDate = currentDate,
                onDateSelected = { newDate ->
                    currentDate = newDate
                    showAlertDialog = false
                    onDateSelected(newDate)
                },
                onDismissRequest = { showAlertDialog = false },

                )
        }
    }
}

@Composable
fun WeekDay(
    weekDay: WeekDay,
    currentDate: LocalDate,
    onDialogOpen: () -> Unit,
    newDate: (LocalDate) -> Unit,
) {
    val isSelected = currentDate == weekDay.date

    val currentColorButtonSelected = if (isSelected) {
        ButtonColors(
            containerColor = categoryColor,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Transparent
        )
    } else {
        ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.Transparent
        )
    }

    Column {
        Button(
            onClick = {
                if (isSelected) {
                    onDialogOpen()
                } else {
                    newDate(weekDay.date)
                }
            },
            colors = currentColorButtonSelected,
            modifier = Modifier.padding(horizontal = 4.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            shape = RoundedCornerShape(30)
        ) {
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = weekDay.date.dayOfWeek.getDisplayName(
                        TextStyle.SHORT, Locale("pt", "BR")
                    )
                        .take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = weekDay.date.dayOfMonth.toString(),
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CustomAlertDialog(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
) {

    var tempDate by remember { mutableStateOf(initialDate) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.Red
                )
            ) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            Button(
                onClick = { onDateSelected(tempDate) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = categoryColor,
                    contentColor = Color.White,
                    
                )
            ) {
                Text(text = stringResource(id = R.string.ok))
            }
        },
        text = {
            Column(modifier = Modifier.aspectRatio(0.8f)) {
                var currentDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }

                CalendarTop(currentDate = currentDate)
                DialogCalendarDay(currentDate = { newDate ->
                    currentDate = newDate
                    tempDate = newDate
                })
            }
        }
    )
}

@Composable
fun DialogCalendarDay(
    currentDate: (data: LocalDate) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startDate = remember { currentMonth.minusMonths(100) }
    val endDate = remember { currentMonth.plusMonths(100) }
    val firstDayOfWeek = remember { daysOfWeek().first() }
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    selectedDate?.let { newDate ->
        currentDate(newDate)
    }

    val state = rememberCalendarState(
        startMonth = startDate,
        endMonth = endDate,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
        outDateStyle = OutDateStyle.EndOfGrid
    )

    Column {
        HorizontalCalendar(
            modifier = Modifier,
            state = state,
            dayContent = { day ->
                DayOfWeek(
                    day = day,
                    isSelected = selectedDate == day.date,
                    onClick = { clickedDay ->
                        selectedDate =
                            if (selectedDate == clickedDay.date) null else clickedDay.date
                    }
                )
            },
            monthHeader = { month ->
                val daysOfWeek = remember { month.weekDays.first().map { it.date.dayOfWeek } }
                MonthHeader(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
fun DayOfWeek(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {

    Box(
        modifier = Modifier
            .aspectRatio(0.9f)
            .clip(CircleShape)
            .background(color = if (isSelected) categoryColor else Color.Transparent)
            .clickable(enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = when {
                isSelected -> Color.White
                day.position == DayPosition.MonthDate -> Color.Black
                else -> Color.LightGray
            }
        )
    }
}

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        daysOfWeek.forEach { dayOfWeek ->
            Text(
                text = dayOfWeek.getDisplayName(
                    TextStyle.SHORT, Locale("pt", "BR")
                ).take(1).uppercase(),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun CalendarTop(
    modifier: Modifier = Modifier,
    currentDate: LocalDate
) {
    val formatterDate = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("pt", "BR"))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceAround
    ) {
        Text(
            text = currentDate.format(formatterDate).toString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekCalendarPreview() {
    val state = rememberWeekCalendarState()
    CalendarRoutinely(state = state, Modifier, onDateSelected = { } )
}

@Preview(showBackground = true)
@Composable
private fun CalendarTopPreview() {
    val localData = LocalDate.now()
    CalendarTop(currentDate = localData)
}

@Preview(showBackground = true)
@Composable
private fun DialogCalendarDayPreview() {
    DialogCalendarDay {}
}


