package com.example.workoutapp.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@Composable
fun CalendarHeader(
    currentMonthName: String,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp)
            )
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(14.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonthClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Poprzedni miesiąc",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = currentMonthName,
            color = Color(0xFFE5D5C5),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        IconButton(onClick = onNextMonthClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Następny miesiąc",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

//dni tygodnia
@Composable
fun WeekDaysRow(modifier: Modifier = Modifier) {
    val daysOfWeek = listOf("Pn", "Wt", "Śr", "Czw", "Pt", "So", "Nd")
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                color = Color(0xFFCCCCCC),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CalendarDaySlot(
    dayNumber: Int,
    isCurrentMonth: Boolean,     //false dla dni z poprzedniego/następnego miesiąca
    isSelected: Boolean,         //true jeśli to aktualnie kliknięty dzień (biała obwódka)
    hasWorkout: Boolean,         //true jeśli w tym dniu zrealizowano trening (ciemnoszary kafel)
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = when {
        !isCurrentMonth -> Color(0xFF444444) // wyszarzone dni poza bieżącym miesiącem
        else -> Color.White
    }

    Column(
        modifier = modifier
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(10.dp))
                .then(
                    if (isSelected) {
                        Modifier.border(2.dp, Color.White, RoundedCornerShape(10.dp))
                    } else Modifier
                )
                .background(
                    if (hasWorkout && isCurrentMonth) Color(0xFF2C2C2C) else Color.Transparent
                )
                .clickable(enabled = isCurrentMonth) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$dayNumber",
                color = textColor,
                fontSize = 16.sp,
                fontWeight = if (hasWorkout && isCurrentMonth) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}


@Preview(name = "CalendarComponents - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCalendarComponents() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CalendarHeader(
                currentMonthName = "Październik 2026",
                onPreviousMonthClick = {},
                onNextMonthClick = {}
            )

            Spacer(modifier = Modifier.height(4.dp))

            WeekDaysRow()

            Row(modifier = Modifier.fillMaxWidth()) {
                CalendarDaySlot(
                    dayNumber = 12,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = true,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 13,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = false,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 14,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = true,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 15,
                    isCurrentMonth = true,
                    isSelected = true,
                    hasWorkout = true,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 16,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = false,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 17,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = false,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
                CalendarDaySlot(
                    dayNumber = 18,
                    isCurrentMonth = true,
                    isSelected = false,
                    hasWorkout = false,
                    onClick = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}