/*package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateRangeSelector
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateSelectionDialog
import java.time.LocalDate

@Preview(name = "Workout History Screen - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutHistoryScreenOctober() {

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.of(2025, 10, 12)) }

    val mockWorkoutDays = remember {
        setOf(
            LocalDate.of(2025, 10, 3),
            LocalDate.of(2025, 10, 4),
            LocalDate.of(2025, 10, 5),
            LocalDate.of(2025, 10, 6),
            LocalDate.of(2025, 10, 7),
            LocalDate.of(2025, 10, 8)
        )
    }

    MaterialTheme {
        WorkoutHistoryScreen(
            selectedDate = selectedDate,
            workoutDays = mockWorkoutDays,
            availableTemplates = emptyList(),
            onDateSelected = { selectedDate = it },
            onBackClick = {},
            onAssignWorkoutClick = { _ -> },
            onViewStatsClick = { _, _ -> },
            onViewWorkoutDetailsClick = {}
        )
    }
}

@Preview(name = "Workout History Screen without selected date - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutHistoryScreenMay() {

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }


    val mockWorkoutDays = remember {
        setOf(
            LocalDate.of(2026, 5, 7),
            LocalDate.of(2026, 5, 24)
        )
    }

    MaterialTheme {
        WorkoutHistoryScreen(
            selectedDate = selectedDate,
            workoutDays = mockWorkoutDays,
            availableTemplates = emptyList(),
            onDateSelected = { selectedDate = it },
            onBackClick = {},
            onAssignWorkoutClick = { _ -> },
            onViewStatsClick = { _, _ -> },
            onViewWorkoutDetailsClick = {}
        )
    }
}

@Preview(name = "Workout History Screen with selected date on workoutDay - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutHistoryScreenJanuary() {

    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.of(2026, 1, 11)) }


    val mockWorkoutDays = remember {
        setOf(
            LocalDate.of(2026, 1, 3),
            LocalDate.of(2026, 1, 11)
        )
    }

    MaterialTheme {
        WorkoutHistoryScreen(
            selectedDate = selectedDate,
            workoutDays = mockWorkoutDays,
            availableTemplates = emptyList(),
            onDateSelected = { selectedDate = it },
            onBackClick = {},
            onAssignWorkoutClick = { _ -> },
            onViewStatsClick = { _, _ -> },
            onViewWorkoutDetailsClick = {}
        )
    }
}



@Preview(name = "History with Date Selection Overlay", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewHistoryWithSelectionOverlay() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WorkoutHistoryScreen(
                selectedDate = LocalDate.now(),
                workoutDays = setOf(LocalDate.now()),
                availableTemplates = emptyList(),
                onDateSelected = {},
                onBackClick = {},
                onAssignWorkoutClick = { _ -> },
                onViewStatsClick = { _, _ -> },
                onViewWorkoutDetailsClick = {}
            )

            CenteredDateSelectionDialog(
                onVisibleChange = {},
                onDateSelected = {}
            )
        }
    }
}


@Preview(name = "History Screen - Range Selector Active", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewHistoryWithRangeActive() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            WorkoutHistoryScreen(
                selectedDate = LocalDate.now(),
                workoutDays = setOf(LocalDate.now()),
                availableTemplates = emptyList(),
                onDateSelected = {},
                onBackClick = {},
                onAssignWorkoutClick = { _ -> },
                onViewStatsClick = { _, _ -> },
                onViewWorkoutDetailsClick = {}
            )
            CenteredDateRangeSelector(
                onVisibleChange = {},
                onDateRangeConfirmed = { _, _ -> }
            )
        }
    }
}*/