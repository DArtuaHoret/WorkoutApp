/*package com.example.workoutapp.ui.screens.section_3

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate

class FakeWorkoutStatsViewModel(initialState: WorkoutStatsUiState) : WorkoutStatsViewModel(
    savedStateHandle = SavedStateHandle()
) {
    private val _mockUiState = MutableStateFlow(initialState)
    override val uiState: StateFlow<WorkoutStatsUiState> = _mockUiState
}

@Preview(name = "Workout Stats Screen - Full Scrollable", showBackground = true, backgroundColor = 0xFF000000, heightDp = 1200)
@Composable
private fun PreviewWorkoutStatsScreen() {
    val initialState = WorkoutStatsUiState(
        isLoading = false,
        startDate = LocalDate.now().minusMonths(1),
        endDate = LocalDate.now(),
        totalDays = 168,
        completedWorkouts = 115,
        muscleDistribution = listOf(
            MuscleGroupShare("Klatka", 35),
            MuscleGroupShare("Plecy", 25),
            MuscleGroupShare("Nogi", 20),
            MuscleGroupShare("Ramiona", 10),
            MuscleGroupShare("Brzuch", 5)
        ),
        averageTimeInSeconds = 4500
    )

    MaterialTheme {
        WorkoutStatsScreen(
            viewModel = FakeWorkoutStatsViewModel(initialState),
            onBackClick = {}
        )
    }
}*/