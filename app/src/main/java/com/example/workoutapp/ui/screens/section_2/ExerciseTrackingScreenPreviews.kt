package com.example.workoutapp.ui.screens.active_workout

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "ExerciseTrackingScreen - Faza ćwiczenia", showBackground = true, backgroundColor = 0xFF000000)
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
            isResting = false,
            restsCompleted = 0,
            isWorkoutFinished = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {},
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Faza odpoczynku", showBackground = true, backgroundColor = 0xFF000000)
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
            isResting = true,
            restsCompleted = 0,
            isWorkoutFinished = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
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
            restsCompleted = 0,
            isWorkoutFinished = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
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
            restsCompleted = 0,
            isWorkoutFinished = false,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
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
            isResting = false,
            restsCompleted = 0,
            isWorkoutFinished = false,
            initialShowDescription = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
        )
    }
}

@Preview(showBackground = true, name = "Active Workout - W trakcie wyjścia")
@Composable
private fun PreviewActiveWorkoutScreenWithExitDialog() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach i napiętym brzuchu.",
            currentSet = 3,
            reps = 8,
            weight = 100,
            restTime = 60,
            isResting = false,
            restsCompleted = 0,
            isWorkoutFinished = false,
            initialShowDescription = false,
            initialShowExitDialog = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
        )
    }
}

@Preview(name = "ExerciseTrackingScreen - Dialog sukcesu", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActiveWorkoutSuccess() {
    MaterialTheme {
        ActiveWorkoutScreen(
            exerciseName = "Martwy ciąg",
            exerciseDescription = "Pamiętaj o prostych plecach.",
            currentSet = 3,
            reps = 8,
            weight = 100,
            restTime = 60,
            isResting = false,
            restsCompleted = 0,
            isWorkoutFinished = true,
            onBackClick = {},
            onRepsChange = {},
            onWeightChange = {},
            onRestTimeChange = {},
            onDoneClick = {},
            onTimerFinished = {},
            onSaveDescription = {}
        )
    }
}