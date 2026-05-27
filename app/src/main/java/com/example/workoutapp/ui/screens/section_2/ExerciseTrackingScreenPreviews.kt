package com.example.workoutapp.ui.screens.active_workout

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ExerciseTrackingScreen - Stoper działa w trakcie odpoczynku", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutResting() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach.",
            currentSet = 2,
            reps = 12,
            weight = 60,
            restTime = 59,
            isResting = true,
            isTimerRunning = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onTimerFinished = {},
            onSaveDescription = {},
            onDoneClick = {}
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Stoper działa w trakcie wykonania", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutExecution() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach.",
            currentSet = 2,
            reps = 12,
            weight = 60,
            restTime = 30,
            isResting = false,
            isTimerRunning = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onTimerFinished = {},
            onSaveDescription = {},
            onDoneClick = {}
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Stoper zatrzymany", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutPaused() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach.",
            currentSet = 2,
            reps = 12,
            weight = 60,
            restTime = 15,
            isResting = true,
            isTimerRunning = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onTimerFinished = {},
            onSaveDescription = {},
            onDoneClick = {}
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Stoper skończony", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutFinished() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach i napiętym brzuchu.",
            currentSet = 2,
            reps = 12,
            weight = 60,
            restTime = 0,
            isResting = true,
            isTimerRunning = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onTimerFinished = {},
            onSaveDescription = {},
            onDoneClick = {}
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Otwarte informacje o ćwiczeniu", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutDescriptionDialog() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach i napiętym brzuchu podczas fazy koncentrycznej. Nie przeprostowuj odcinka lędźwiowego w górnej fazie ruchu.",
            currentSet = 2,
            reps = 12,
            weight = 60,
            restTime = 59,
            isResting = true,
            isTimerRunning = false,
            initialShowDescription = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onTimerFinished = {},
            onDoneClick = {},
            onSaveDescription = {}
        )
    }
}