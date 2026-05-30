package com.example.workoutapp.ui.screens.section_1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.*


private val sampleExercises = listOf(
    ExerciseOption("1",  "Wyciskanie na płasko",         "Klatka piersiowa"),
    ExerciseOption("2",  "Wyciskanie na skosie",          "Klatka piersiowa"),
    ExerciseOption("3",  "Rozpiętki",                     "Klatka piersiowa"),
    ExerciseOption("4",  "Martwy ciąg",                   "Plecy"),
    ExerciseOption("5",  "Podciąganie",                   "Plecy"),
    ExerciseOption("6",  "Pull-ups",                      "Plecy"),
    ExerciseOption("7",  "Wiosłowanie sztangą",           "Plecy"),
    ExerciseOption("8",  "Przysiad ze sztangą",           "Nogi"),
    ExerciseOption("9",  "Wykroki",                       "Nogi"),
    ExerciseOption("10", "Wyciskanie żołnierskie",        "Barki"),
    ExerciseOption("11", "Unoszenie bokiem",              "Barki"),
    ExerciseOption("12", "Uginanie ze sztangielkami",     "Biceps"),
    ExerciseOption("13", "Wyciskanie francuskie",         "Triceps"),
)

@Preview(name = "ExerciseSearchScreen – all exercises", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSearchFull() {
    MaterialTheme {
        ExerciseSearchScreen(
            exercises = sampleExercises,
            onBackClick = {},
            onAddExercise = {},
            onAddCustomExercise = {},
        )
    }
}


@Preview(name = "ExerciseSearchScreen – no results", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSearchEmpty() {
    MaterialTheme {
        ExerciseSearchScreen(
            exercises = emptyList(),
            onBackClick = {},
            onAddExercise = {},
            onAddCustomExercise = {},
        )
    }
}
