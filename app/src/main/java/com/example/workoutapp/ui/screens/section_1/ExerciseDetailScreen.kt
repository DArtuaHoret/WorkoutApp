package com.example.workoutapp.ui.screens.section_1

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.workoutapp.R
import com.example.workoutapp.ui.reusableContents.Section_1.*
import java.io.File

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
    val exerciseName         by viewModel.exerciseName.collectAsState()
    val selectedMuscleGroups by viewModel.selectedMuscleGroups.collectAsState()
    val sets                 by viewModel.sets.collectAsState()
    val muscleGroups         by viewModel.muscleGroups.collectAsState()
    val exerciseNote         by viewModel.exerciseNote.collectAsState()
    val photoUrl             by viewModel.photoUrl.collectAsState()
    val context = LocalContext.current

    val lang = LocalConfiguration.current.locales[0].language
    LaunchedEffect(Unit) { viewModel.setLang(lang) }

    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.onImagePicked(context, it) } }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

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
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                )
            }
            Text(
                text = stringResource(R.string.exercise_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2C2C2C))
                            .clickable { pickImage.launch("image/*") },
                        contentAlignment = Alignment.Center,
                    ) {
                        val url = photoUrl
                        val model: Any? = when {
                            url.isNullOrBlank() -> null
                            url.startsWith("/") -> File(url)
                            else -> {
                                val resId = remember(url) {
                                    context.resources.getIdentifier(url, "raw", context.packageName)
                                        .takeIf { it != 0 }
                                }
                                resId
                            }
                        }

                        if (model != null) {
                            AsyncImage(
                                model = model,
                                contentDescription = exerciseName,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        } else {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ExerciseImagePlaceholder()
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.exercise_photo_hint),
                                    color = Color(0xFF888888),
                                    fontSize = 12.sp,
                                )
                            }
                        }
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(R.string.exercise_name_label),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = exerciseName,
                        onValueChange = viewModel::onExerciseNameChange,
                        placeholder = stringResource(R.string.exercise_name_placeholder),
                    )
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = stringResource(R.string.exercise_note_label),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    WorkoutTextField(
                        value = exerciseNote,
                        onValueChange = viewModel::onExerciseNoteChange,
                        placeholder = stringResource(R.string.exercise_note_placeholder),
                    )
                }
            }

            item {
                MuscleGroupSelector(
                    selectedGroups = selectedMuscleGroups,
                    onSelectionChange = viewModel::onMuscleGroupsChange,
                    allGroups = muscleGroups,
                )
            }

            item {
                Text(
                    text = stringResource(R.string.sets_title),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp),
                )
            }

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

            item {
                ActionButton(
                    onClick = viewModel::onAddSet,
                    label = stringResource(R.string.add_set),
                    style = ActionButtonStyle.DarkOutlined,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = { viewModel.deleteExercise { onBackClick() } },
            label = stringResource(R.string.delete_exercise),
            icon = Icons.Default.Delete,
            style = ActionButtonStyle.DangerFilled,
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = {
                viewModel.saveExercise()
                onSaveClick()
            },
            label = stringResource(R.string.save_exercise),
            icon = null,
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}