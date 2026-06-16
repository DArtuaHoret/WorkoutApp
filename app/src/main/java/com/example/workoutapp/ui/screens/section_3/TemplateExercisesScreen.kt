package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ExerciseItemCard

@Composable
fun TemplateExercisesScreen(
    viewModel: TemplateExercisesViewModel,
    onEditExercise: (sessionItemId: String, exerciseId: String, exerciseName: String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exercises by viewModel.exercises.collectAsState()

    val lang = LocalConfiguration.current.locales[0].language
    LaunchedEffect(Unit) { viewModel.setLang(lang) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Powrót",
                    tint = Color.White
                )
            }
            Text(
                text = viewModel.sessionName.uppercase(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp)
            )
        }

        if (exercises.isEmpty()) {
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
                items(items = exercises, key = { it.sessionItemId }) { exercise ->
                    val setCount = exercise.sets.size.toString()
                    val weights = exercise.sets.map { it.plannedWeight.toInt() }
                    val weight = if (weights.isEmpty()) "0 kg"
                    else if (weights.min() == weights.max()) "${weights.min()} kg"
                    else "${weights.min()}–${weights.max()} kg"
                    val rests = exercise.sets.map { it.plannedRestTime }
                    val restTime = if (rests.isEmpty()) "00:00"
                    else if (rests.min() == rests.max()) formatRestTime(rests.min())
                    else "${formatRestTime(rests.min())}–${formatRestTime(rests.max())}"

                    ExerciseItemCard(
                        exerciseName = exercise.exerciseName,
                        series = setCount,
                        weight = weight,
                        restTime = restTime,
                        note = exercise.note ?: "",
                        photoUrl = exercise.photoUrl,
                        onEditClick = {
                            onEditExercise(
                                exercise.sessionItemId.toString(),
                                exercise.exerciseId.toString(),
                                exercise.exerciseName
                            )
                        },
                        onDeleteClick = {
                            viewModel.deleteSessionItem(exercise.sessionItemId)
                        }
                    )
                }
            }
        }
    }
}

private fun formatRestTime(seconds: Int): String =
    "%02d:%02d".format(seconds / 60, seconds % 60)