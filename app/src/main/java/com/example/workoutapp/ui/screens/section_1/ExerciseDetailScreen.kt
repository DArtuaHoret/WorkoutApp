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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailScreen(
    exerciseName: String,
    onExerciseNameChange: (String) -> Unit,
    selectedMuscleGroups: Set<String>,
    onMuscleGroupsChange: (Set<String>) -> Unit,
    sets: List<ExerciseSetState>,
    onSetChange: (index: Int, ExerciseSetState) -> Unit,
    onAddSet: () -> Unit,
    onDeleteSet: (index: Int) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    imageContent: @Composable BoxScope.() -> Unit = { ExerciseImagePlaceholder() },
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ćwiczenie",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Wróć",
                            tint = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                ),
            )
        },
        containerColor = Color.Black,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
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
                        imageContent()
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
                            onValueChange = onExerciseNameChange,
                            placeholder = "np. Martwy ciąg",
                        )
                    }
                }

                // Muscle groups
                item {
                    MuscleGroupSelector(
                        selectedGroups = selectedMuscleGroups,
                        onSelectionChange = onMuscleGroupsChange,
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
                        onWeightChange = { onSetChange(index, set.copy(weight = it)) },
                        reps = set.reps,
                        onRepsChange = { onSetChange(index, set.copy(reps = it)) },
                        rest = set.rest,
                        onRestChange = { onSetChange(index, set.copy(rest = it)) },
                        onDelete = if (sets.size > 1) ({ onDeleteSet(index) }) else null,
                    )
                }

                // Add set button
                item {
                    ActionButton(
                        onClick = onAddSet,
                        label = "DODAJ SERIĘ",
                        style = ActionButtonStyle.DarkOutlined,
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                onClick = onSaveClick,
                label = "ZAPISZ ĆWICZENIE",
                icon = null,
                style = ActionButtonStyle.LightFilled,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
