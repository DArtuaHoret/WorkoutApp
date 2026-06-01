package com.example.workoutapp.ui.screens.section_3


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare

@Preview(
    name = "Workout Stats Screen - Full Scrollable",
    showBackground = true,
    backgroundColor = 0xFF000000,
    heightDp = 1200
)
@Composable
private fun PreviewWorkoutStatsScreen() {

    val mockTotalDays = 168
    val mockCompletedWorkouts = 115

    val mockMuscleDistribution = listOf(
        MuscleGroupShare("Klatka", 35),
        MuscleGroupShare("Plecy", 25),
        MuscleGroupShare("Nogi", 20),
        MuscleGroupShare("Ramiona", 10),
        MuscleGroupShare("Brzuch", 5),
        MuscleGroupShare("Barki", 5)
    )

    val mockAverageTimeInSeconds = 4500

    MaterialTheme {
        WorkoutStatsScreen(
            totalDays = mockTotalDays,
            completedWorkouts = mockCompletedWorkouts,
            muscleDistribution = mockMuscleDistribution,
            averageTimeInSeconds = mockAverageTimeInSeconds,
            onBackClick = {}
        )
    }
}