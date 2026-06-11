package com.example.workoutapp.ui.screens.section_3


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateRangeSelector
import com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare
import java.time.LocalDate

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
            startDate = LocalDate.now().minusMonths(1), // Przykładowa data początkowa (miesiąc temu)
            endDate = LocalDate.now(),                  // Przykładowa data końcowa (dzisiaj)
            totalDays = mockTotalDays,
            completedWorkouts = mockCompletedWorkouts,
            muscleDistribution = mockMuscleDistribution,
            averageTimeInSeconds = mockAverageTimeInSeconds,
            onBackClick = {}
        )
    }
}


@Preview(name = "Stats Screen with Date Range Data", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewStatsScreenWithDates() {
    MaterialTheme {
        WorkoutStatsScreen(
            startDate = LocalDate.now().minusMonths(1),
            endDate = LocalDate.now(),
            totalDays = 30,
            completedWorkouts = 18,
            muscleDistribution = listOf(
                com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare("Klatka", 40),
                com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare("Nogi", 60)
            ),
            averageTimeInSeconds = 4200,
            onBackClick = {}
        )
    }
}