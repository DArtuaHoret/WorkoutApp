package com.example.workoutapp.ui.screens.section_1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.R
import com.example.workoutapp.ui.reusableContents.Section_1.*

data class ExerciseEntry(
    val id: String,
    val exerciseId: String,
    val name: String,
    val series: String,
    val weight: String,
    val restTime: String,
    val note: String = "",
    val photoUrl: String = "",
)

@Composable
fun TemplateDetailScreen(
    viewModel: TemplateDetailViewModel,
    onBackClick: () -> Unit,
    onAddExerciseClick: () -> Unit,
    onEditExercise: (ExerciseEntry) -> Unit,
    modifier: Modifier = Modifier,
) {
    val templateName        by viewModel.templateName.collectAsState()
    val exercises           by viewModel.exercises.collectAsState()
    val templateDescription by viewModel.templateDescription.collectAsState()
    val canSave = templateName.isNotBlank()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                )
            }
            Text(
                text = stringResource(R.string.template_detail_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(R.string.template_name_label),
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
                            text = stringResource(R.string.template_name_required),
                            color = Color.Red,
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(R.string.template_description_label),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = templateDescription,
                        onValueChange = viewModel::onTemplateDescriptionChange,
                        placeholder = stringResource(R.string.template_description_placeholder),
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.exercises_title),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

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
                    photoUrl = exercise.photoUrl,
                    onEditClick = { onEditExercise(exercise) },
                    onDeleteClick = { viewModel.deleteExercise(exercise) },
                )
            }

            item {
                AddExerciseCard(onAddClick = onAddExerciseClick)
            }
        }

        ActionButton(
            onClick = { viewModel.deleteTemplate { onBackClick() } },
            label = stringResource(R.string.delete_template),
            icon = Icons.Default.Delete,
            style = ActionButtonStyle.DangerFilled,
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = {
                if (!templateName.isNotBlank()) return@ActionButton
                viewModel.saveTemplate()
                onBackClick()
            },
            label = stringResource(R.string.save_template),
            enabled = canSave,
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}