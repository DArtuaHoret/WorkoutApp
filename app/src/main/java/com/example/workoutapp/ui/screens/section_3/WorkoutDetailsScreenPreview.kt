/*package com.example.workoutapp.ui.screens.section_3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

// Mock ViewModel dla podglądu
class FakeWorkoutDetailsViewModel(initialState: WorkoutDetailsUiState) : WorkoutDetailsViewModel(
    savedStateHandle = SavedStateHandle(),
    sessionRepository = object : com.example.workoutapp.database.WorkoutSessionRepository { /* puste */ },
    templateRepository = object : com.example.workoutapp.database.WorkoutTemplateRepository { /* puste */ }
) {
    private val _mockUiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<WorkoutDetailsUiState> = _mockUiState
}

@Preview(name = "Workout Details - October 12", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutDetailsScreen() {
    val mockDate = LocalDate.of(2026, 10, 12)
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

    val fakeViewModel = FakeWorkoutDetailsViewModel(
        WorkoutDetailsUiState(
            date = mockDate,
            workoutSessions = mockSessions,
            sessionToTemplateId = mapOf("1" to "temp1", "2" to "temp2")
        )
    )

    MaterialTheme {
        WorkoutDetailsScreen(
            viewModel = fakeViewModel,
            onBackClick = {},
            onViewExercisesClick = {},
            onStartWorkoutClick = { _, _ -> }
        )
    }
}*/