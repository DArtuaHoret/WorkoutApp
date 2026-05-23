package com.example.workoutapp.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import java.time.format.DateTimeFormatter

@Composable
fun WorkoutCalendar(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    workoutDays: Set<LocalDate> = emptySet()
) {
    var currentYearMonth by remember { mutableStateOf(YearMonth.from(selectedDate)) }

    val localePl = Locale.forLanguageTag("pl")
    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", localePl)

    val headerTitle = currentYearMonth.format(formatter)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(localePl) else it.toString() }

    val firstDayOfMonth = currentYearMonth.atDay(1)
    val totalDaysInMonth = currentYearMonth.lengthOfMonth()
    val firstDayOfWeekInMonth = firstDayOfMonth.dayOfWeek.value
    val daysFromPreviousMonth = firstDayOfWeekInMonth - 1

    val calendarGridCells = remember(currentYearMonth) {
        val cells = mutableListOf<LocalDate>()
        val prevMonth = currentYearMonth.minusMonths(1)
        val prevMonthLength = prevMonth.lengthOfMonth()

        for (i in (prevMonthLength - daysFromPreviousMonth + 1)..prevMonthLength) {
            cells.add(prevMonth.atDay(i))
        }
        for (i in 1..totalDaysInMonth) {
            cells.add(currentYearMonth.atDay(i))
        }
        val nextMonth = currentYearMonth.plusMonths(1)
        var nextMonthDay = 1
        while (cells.size % 7 != 0) {
            cells.add(nextMonth.atDay(nextMonthDay))
            nextMonthDay++
        }
        cells
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        CalendarHeader(
            currentMonthName = headerTitle,
            onPreviousMonthClick = { currentYearMonth = currentYearMonth.minusMonths(1) },
            onNextMonthClick = { currentYearMonth = currentYearMonth.plusMonths(1) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        WeekDaysRow(modifier = Modifier.padding(horizontal = 4.dp))
        Spacer(modifier = Modifier.height(8.dp))

        calendarGridCells.chunked(7).forEach { weekDays ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weekDays.forEach { date ->
                    val isCurrentMonth = YearMonth.from(date) == currentYearMonth

                    CalendarDaySlot(
                        dayNumber = date.dayOfMonth,
                        isCurrentMonth = isCurrentMonth,
                        isSelected = date == selectedDate && isCurrentMonth,
                        hasWorkout = workoutDays.contains(date),
                        onClick = {
                            if (isCurrentMonth) {
                                onDateSelected(date)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Preview(name = "Full WorkoutCalendar - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutCalendar() {
    val mockSelectedDate = LocalDate.of(2026, 10, 15)

    val mockWorkoutDays = setOf(
        LocalDate.of(2026, 10, 2),
        LocalDate.of(2026, 10, 5),
        LocalDate.of(2026, 10, 8),
        LocalDate.of(2026, 10, 9),
        LocalDate.of(2026, 10, 10),
        LocalDate.of(2026, 10, 12),
        LocalDate.of(2026, 10, 14),
        LocalDate.of(2026, 10, 15)
    )

    val mockRestDays = setOf(
        LocalDate.of(2026, 10, 3),
        LocalDate.of(2026, 10, 4),
        LocalDate.of(2026, 10, 6),
        LocalDate.of(2026, 10, 7),
        LocalDate.of(2026, 10, 11),
        LocalDate.of(2026, 10, 13),
        LocalDate.of(2026, 10, 16),
        LocalDate.of(2026, 10, 17),
        LocalDate.of(2026, 10, 18)
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            WorkoutCalendar(
                selectedDate = mockSelectedDate,
                onDateSelected = {},
                workoutDays = mockWorkoutDays
            )
        }
    }
}