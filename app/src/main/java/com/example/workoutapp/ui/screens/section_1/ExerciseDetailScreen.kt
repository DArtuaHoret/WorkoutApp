package com.example.workoutapp.ui.screens.section_1


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

data class ExerciseSetState(
    val id: String,
    val weight: Int = 0,
    val reps: Int = 8,
    val rest: Int = 60,
)

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,

) {
    val exerciseName        by viewModel.exerciseName.collectAsState()
    val selectedMuscleGroups by viewModel.selectedMuscleGroups.collectAsState()
    val sets                by viewModel.sets.collectAsState()
    val muscleGroups by viewModel.muscleGroups.collectAsState()
    val exerciseNote by viewModel.exerciseNote.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Nagłówek
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Wróć",
                    tint = Color.White,
                )
            }
            Text(
                text = "Ćwiczenie",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            // Exercise image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    ExerciseImagePlaceholder()
                }
            }

            // Exercise name
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Nazwa ćwiczenia",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = exerciseName,
                        onValueChange = viewModel::onExerciseNameChange,
                        placeholder = "np. Martwy ciąg",
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Opis / notatka",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = exerciseNote,
                        onValueChange = viewModel::onExerciseNoteChange,
                        placeholder = "np. Trzymaj plecy proste...",
                    )
                }
            }

            // Muscle groups
            item {
                MuscleGroupSelector(
                    selectedGroups = selectedMuscleGroups,
                    onSelectionChange = viewModel::onMuscleGroupsChange,
                    allGroups = muscleGroups,
                )
            }

            // Sets header
            item {
                Text(
                    text = "Serie",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            // Set cards
            itemsIndexed(
                items = sets,
                key = { _, set -> set.id },
            ) { index, set ->
                ExerciseSetCard(
                    setNumber = index + 1,
                    weight = set.weight,
                    onWeightChange = { viewModel.onSetChange(index, set.copy(weight = it)) },
                    reps = set.reps,
                    onRepsChange = { viewModel.onSetChange(index, set.copy(reps = it)) },
                    rest = set.rest,
                    onRestChange = { viewModel.onSetChange(index, set.copy(rest = it)) },
                    onDelete = if (sets.size > 1) ({ viewModel.onDeleteSet(index) }) else null,
                )
            }

            // Add set button
            item {
                ActionButton(
                    onClick = viewModel::onAddSet,
                    label = "DODAJ SERIĘ",
                    style = ActionButtonStyle.DarkOutlined,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = {
                viewModel.saveExercise()
                onSaveClick()
            },
            label = "ZAPISZ ĆWICZENIE",
            icon = null,
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

