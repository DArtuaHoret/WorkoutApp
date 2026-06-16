package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ExerciseItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateExercisesScreen(
    viewModel: TemplateExercisesViewModel,
    onEditExercise: (sessionItemId: String, exerciseId: String, exerciseName: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exerciseSets by viewModel.exerciseSets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewModel.sessionName.uppercase(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Powrót",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            if (exerciseSets.isEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Brak ćwiczeń w tym treningu",
                    color = Color(0xFFAAAAAA),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = exerciseSets,
                        key = { "${it.sessionItemId}_${it.setNumber}" }
                    ) { set ->
                        ExerciseItemCard(
                            exerciseName = "${set.exerciseName} — Seria ${set.setNumber}",
                            series = "${set.plannedReps} powtórzeń",
                            weight = "${set.plannedWeight.toInt()} kg",
                            restTime = formatRestTime(set.plannedRestTime),  // ← rzeczywisty czas
                            note = set.note ?: "",
                            onEditClick = {
                                onEditExercise(
                                    set.sessionItemId.toString(),
                                    set.exerciseId.toString(),
                                    set.exerciseName
                                )
                            },
                            onDeleteClick = {
                                viewModel.deleteSessionItem(set.sessionItemId)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun formatRestTime(seconds: Int): String =
    "%02d:%02d".format(seconds / 60, seconds % 60)