package com.example.workoutapp.ui.screens.section_3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Preview(name = "Workout Details - October 12", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutDetailsScreen() {

    val mockSessions = listOf(
        WorkoutSessionData(
            id = "1",
            workoutName = "GÓRA CIAŁA",
            timeRange = "18:30 - 19:45",
            icon = Icons.Filled.FitnessCenter,
            isCompleted = true
        ),
        WorkoutSessionData(
            id = "2",
            workoutName = "TRENING CARDIO",
            timeRange = "07:00 - 07:45",
            icon = Icons.AutoMirrored.Filled.DirectionsRun,
            isCompleted = true
        )
    )

    MaterialTheme {
        WorkoutDetailsScreen(
            date = LocalDate.of(2026, 10, 12),
            workoutSessions = mockSessions,
            onBackClick = {},
            onWorkoutClick = {},
            onViewExercisesClick = {},
            onStartWorkoutClick = {}
        )
    }
}