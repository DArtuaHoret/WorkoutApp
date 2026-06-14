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
    val exerciseId: String,
    val name: String,
    val series: String,
    val weight: String,
    val restTime: String,
    val note: String = "",
    val photoUrl: String = "", // ← nowe pole
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateDetailScreen(
    viewModel: TemplateDetailViewModel,
    onBackClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onEditExercise: (ExerciseEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    val templateName by viewModel.templateName.collectAsState()
    val exercises    by viewModel.exercises.collectAsState()
    val templateDescription by viewModel.templateDescription.collectAsState()
    val canSave = templateName.isNotBlank()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

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
                text = "Przegląd szablonu",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.weight(1f), // ← zamiast fillMaxSize()
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
                        onValueChange = viewModel::onTemplateNameChange,
                    )
                    if (templateName.isBlank()) {
                        Text(
                            text = "Nazwa jest wymagana",
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    }
                }
            }



            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Opis szablonu",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = templateDescription,
                        onValueChange = viewModel::onTemplateDescriptionChange,
                        placeholder = "np. Plan na masę, 3 dni w tygodniu...",
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
                    photoUrl = exercise.photoUrl, // ← przekazanie
                    onEditClick = { onEditExercise(exercise) },
                    onDeleteClick = { viewModel.deleteExercise(exercise) },
                )
            }

            // Add exercise card
            item {
                AddExerciseCard(onAddClick = onAddExerciseClick)
            }



        }
        ActionButton(
            onClick = {
                if (!(templateName.isNotBlank())) return@ActionButton

                viewModel.saveTemplate()
                onBackClick()
            },
            label = "ZAPISZ SZABLON",
            enabled = canSave,
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
