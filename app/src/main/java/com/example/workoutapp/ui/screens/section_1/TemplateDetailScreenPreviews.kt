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


@Preview(name = "TemplateDetailScreen – with exercises", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTemplateDetailWithExercises() {
    var name by remember { mutableStateOf("PLAN NA MASĘ (P&P&L)") }

    MaterialTheme {
        TemplateDetailScreen(
            templateName = name,
            onTemplateNameChange = { name = it },
            exercises = listOf(
                ExerciseEntry("1", "Wyciskanie na ławce", "4 x 8", "80 kg", "02:00"),
                ExerciseEntry("2", "Martwy Ciąg", "3 x 5", "120 kg", "03:00", "Uważaj na technikę"),
            ),
            onBackClick = {},
            onAddExerciseClick = {},
            onEditExercise = {},
            onDeleteExercise = {},
        )
    }
}

@Preview(name = "TemplateDetailScreen – empty", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTemplateDetailEmpty() {
    var name by remember { mutableStateOf("") }

    MaterialTheme {
        TemplateDetailScreen(
            templateName = name,
            onTemplateNameChange = { name = it },
            exercises = emptyList(),
            onBackClick = {},
            onAddExerciseClick = {},
            onEditExercise = {},
            onDeleteExercise = {},
        )
    }
}
