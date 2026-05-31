package com.example.workoutapp.ui.screens.section_1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlin.collections.plus


@Preview(name = "ExerciseDetailScreen", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseDetailScreen() {
    var name by remember { mutableStateOf("Martwy Ciąg") }
    var muscles by remember { mutableStateOf(setOf("Plecy", "Nogi")) }
    var sets by remember {
        mutableStateOf(
            listOf(
                ExerciseSetState(id = "1", weight = 100, reps = 5, rest = 90),
                ExerciseSetState(id = "2", weight = 100, reps = 5, rest = 90),
                ExerciseSetState(id = "3", weight = 100, reps = 5, rest = 90),
            )
        )
    }

    MaterialTheme {
        ExerciseDetailScreen(
            exerciseName = name,
            onExerciseNameChange = { name = it },
            selectedMuscleGroups = muscles,
            onMuscleGroupsChange = { muscles = it },
            sets = sets,
            onSetChange = { index, updated ->
                sets = sets.toMutableList().also { it[index] = updated }
            },
            onAddSet = {
                sets = sets + ExerciseSetState(id = (sets.size + 1).toString())
            },
            onDeleteSet = { index ->
                sets = sets.toMutableList().also { it.removeAt(index) }
            },
            onSaveClick = {},
            onBackClick = {},
        )
    }
}


@Preview(name = "ExerciseDetailScreen – nowe ćwiczenie (puste)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseDetailEmpty() {
    var name by remember { mutableStateOf("") }
    var muscles by remember { mutableStateOf(emptySet<String>()) }
    var sets by remember { mutableStateOf(emptyList<ExerciseSetState>()) }

    MaterialTheme {
        ExerciseDetailScreen(
            exerciseName = name,
            onExerciseNameChange = { name = it },
            selectedMuscleGroups = muscles,
            onMuscleGroupsChange = { muscles = it },
            sets = sets,
            onSetChange = { index, updated ->
                sets = sets.toMutableList().also { it[index] = updated }
            },
            onAddSet = {
                sets = sets + ExerciseSetState(id = (sets.size + 1).toString())
            },
            onDeleteSet = { index ->
                sets = sets.toMutableList().also { it.removeAt(index) }
            },
            onSaveClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ExerciseDetailScreen – z bazy (wypełnione)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseDetailFromDb() {
    var name by remember { mutableStateOf("Wyciskanie sztangi") }
    var muscles by remember { mutableStateOf(setOf("Klatka", "Triceps", "Barki")) }
    var sets by remember {
        mutableStateOf(
            listOf(
                ExerciseSetState(id = "1", weight = 80, reps = 8, rest = 90),
                ExerciseSetState(id = "2", weight = 80, reps = 8, rest = 90),
                ExerciseSetState(id = "3", weight = 80, reps = 8, rest = 90),
            )
        )
    }

    MaterialTheme {
        ExerciseDetailScreen(
            exerciseName = name,
            onExerciseNameChange = { name = it },
            selectedMuscleGroups = muscles,
            onMuscleGroupsChange = { muscles = it },
            sets = sets,
            onSetChange = { index, updated ->
                sets = sets.toMutableList().also { it[index] = updated }
            },
            onAddSet = {
                sets = sets + ExerciseSetState(id = (sets.size + 1).toString())
            },
            onDeleteSet = { index ->
                sets = sets.toMutableList().also { it.removeAt(index) }
            },
            onSaveClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ExerciseDetailScreen – nowe z jedną serią", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseDetailOneSet() {
    var name by remember { mutableStateOf("") }
    var muscles by remember { mutableStateOf(emptySet<String>()) }
    var sets by remember {
        mutableStateOf(
            listOf(
                ExerciseSetState(id = "1"),
            )
        )
    }

    MaterialTheme {
        ExerciseDetailScreen(
            exerciseName = name,
            onExerciseNameChange = { name = it },
            selectedMuscleGroups = muscles,
            onMuscleGroupsChange = { muscles = it },
            sets = sets,
            onSetChange = { index, updated ->
                sets = sets.toMutableList().also { it[index] = updated }
            },
            onAddSet = {
                sets = sets + ExerciseSetState(id = (sets.size + 1).toString())
            },
            onDeleteSet = { index ->
                sets = sets.toMutableList().also { it.removeAt(index) }
            },
            onSaveClick = {},
            onBackClick = {},
        )
    }
}