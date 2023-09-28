package com.example.routinely.ui.components

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.routinely.ui.theme.PurpleRoutinely
import java.time.Instant
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerRoutinely(
    state: DatePickerState,
) {
    Column {
        val dateFormatter: DatePickerFormatter = remember {
            /**
             * At the moment the only date formatter is for brazilian formatter ('day of week', 'day of month' 'de' 'name of month')...
             * It should be adjust for each country format
             */
            /**
             * At the moment the only date formatter is for brazilian formatter ('day of week', 'day of month' 'de' 'name of month')...
             * It should be adjust for each country format
             */
            /**
             * At the moment the only date formatter is for brazilian formatter ('day of week', 'day of month' 'de' 'name of month')...
             * It should be adjust for each country format
             */

            /**
             * At the moment the only date formatter is for brazilian formatter ('day of week', 'day of month' 'de' 'name of month')...
             * It should be adjust for each country format
             */
            DatePickerDefaults
                .dateFormatter(
                    yearSelectionSkeleton = "yMMMM",
                    selectedDateDescriptionSkeleton = "yMMMM",
                    selectedDateSkeleton = "EEEE, d MMMM"
                )
        }

        DatePicker(
            state = state,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .border(
                    width = 1.dp,
                    color = PurpleRoutinely,
                    shape = RoundedCornerShape(10.dp)
                ),
            showModeToggle = false,
            title = null,
            headline = {
                HeadlineDatePickerRoutinely(
                    state.selectedDateMillis,
                    dateFormatter = dateFormatter
                )
            },
            colors = DatePickerDefaults.colors(
                dividerColor = PurpleRoutinely,
                navigationContentColor = PurpleRoutinely,
                headlineContentColor = PurpleRoutinely,
                selectedDayContainerColor = PurpleRoutinely,
            )
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun datePickerState(): DatePickerState {
    val initialDate: Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Instant.now().toEpochMilli()
    } else {
        val calendar = Calendar.getInstance(TimeZone.getDefault())
        calendar.timeInMillis
    }

    return rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        yearRange = IntRange(2013, 2023), // TODO Add this automatically
        initialSelectedDateMillis = initialDate
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadlineDatePickerRoutinely(
    @Suppress("AutoBoxing") selectedDateMillis: Long?,
    dateFormatter: DatePickerFormatter
) {
    val calendar: CalendarLocale = Locale.getDefault()

    val formattedDate = dateFormatter.formatDate(
        dateMillis = selectedDateMillis,
        locale = calendar,
    )
    val firstLetter = formattedDate!!.substring(0, 1).uppercase()
    val formattedDateWithUppercaseFirstLetter = firstLetter + formattedDate.substring(1)


    Text(
        text = formattedDateWithUppercaseFirstLetter,
        modifier = Modifier
            .semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = "teste"
            }
            .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 12.dp)
        ,
        maxLines = 1,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
    )
}