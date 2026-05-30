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


data class ExerciseEntry(
    val id: String,
    val name: String,
    val series: String,
    val weight: String,
    val restTime: String,
    val note: String = "",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDetailScreen(
    templateName: String,
    onTemplateNameChange: (String) -> Unit,
    exercises: List<ExerciseEntry>,
    onBackClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onEditExercise: (ExerciseEntry) -> Unit,
    onDeleteExercise: (ExerciseEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Przegląd szablonu",
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
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            // Template name field
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Nazwa szablonu",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = templateName,
                        onValueChange = onTemplateNameChange,
                    )
                }
            }

            // Exercises header
            item {
                Text(
                    text = "Ćwiczenia",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

            // Exercise cards
            items(
                items = exercises,
                key = { it.id },
            ) { exercise ->
                ExerciseItemCard(
                    exerciseName = exercise.name,
                    series = exercise.series,
                    weight = exercise.weight,
                    restTime = exercise.restTime,
                    note = exercise.note,
                    onEditClick = { onEditExercise(exercise) },
                    onDeleteClick = { onDeleteExercise(exercise) },
                )
            }

            // Add exercise card
            item {
                AddExerciseCard(onAddClick = onAddExerciseClick)
            }

            // Save button
            item {
                Spacer(modifier = Modifier.height(4.dp))
                ActionButton(
                    onClick = {},
                    label = "ZAPISZ SZABLON",
                    icon = null,
                    style = ActionButtonStyle.LightFilled,
                )
            }
        }
    }
}
