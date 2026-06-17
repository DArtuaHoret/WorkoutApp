package com.example.workoutapp.ui.screens.active_workout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_2.CenteredDescriptionDialog
import com.example.workoutapp.ui.reusableContents.Section_2.CenteredExitConfirmationDialog
import com.example.workoutapp.ui.reusableContents.Section_2.CenteredWorkoutSuccessDialog
import com.example.workoutapp.ui.reusableContents.Section_2.ExerciseSetCardDetailed
import com.example.workoutapp.ui.reusableContents.Section_2.ExerciseTimer
import com.example.workoutapp.ui.screens.section_2.ExerciseTrackingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutScreen(
    viewModel: ExerciseTrackingViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exerciseName by viewModel.exerciseName.collectAsState()
    val exerciseDescription by viewModel.exerciseDescription.collectAsState()
    val exercisePhotoUrl by viewModel.exercisePhotoUrl.collectAsState()
    val currentSet by viewModel.currentSet.collectAsState()
    val reps by viewModel.reps.collectAsState()
    val weight by viewModel.weight.collectAsState()
    val restTime by viewModel.restTime.collectAsState()
    val isResting by viewModel.isResting.collectAsState()
    val restsCompleted by viewModel.restsCompleted.collectAsState()
    val isWorkoutFinished by viewModel.isWorkoutFinished.collectAsState()

    var showDescription by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = !isWorkoutFinished) {
        showExitDialog = true
    }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { showExitDialog = true },
                    modifier = Modifier.offset(x = (-12).dp),
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Wróć", tint = Color.White)
                }
                Text(
                    "Aktywny trening",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = (-16).dp).weight(1f),
                )
                IconButton(onClick = { showDescription = true }) {
                    Icon(Icons.Default.Info, "Informacje o ćwiczeniu", tint = Color.White)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                ExerciseSetCardDetailed(
                    exerciseName = exerciseName,
                    setNumber = currentSet,
                    weight = weight,
                    onWeightChange = viewModel::updateWeight,
                    reps = reps,
                    onRepsChange = viewModel::updateReps,
                    rest = restTime,
                    onRestChange = viewModel::updateRestTime,
                    enabled = !isResting,
                    photoUrl = exercisePhotoUrl
                )

                Spacer(modifier = Modifier.height(48.dp))

                key(currentSet, exerciseName, isResting, restsCompleted) {
                    ExerciseTimer(
                        title = if (isResting) "ODPOCZYNEK" else "WYKONANIE ĆWICZENIA",
                        resetKey = Triple(currentSet, isResting, restsCompleted),
                        initialSeconds = restTime,
                        initialIsRunning = isResting,
                        isExercisePhase = !isResting,
                        onTimerFinished = {},
                        onFinishClick = {
                            if (isResting) {
                                viewModel.onTimerFinished()
                            } else {
                                viewModel.onDoneClick()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        if (showDescription) {
            CenteredDescriptionDialog(
                initialDescription = exerciseDescription,
                onVisibleChange = { showDescription = it }
                //onSave = { updatedText -> viewModel.updateDescription(updatedText) }
            )
        }

        if (showExitDialog) {
            CenteredExitConfirmationDialog(
                onConfirm = {
                    showExitDialog = false
                    onBackClick()
                },
                onDismiss = {
                    showExitDialog = false
                }
            )
        }

        if (isWorkoutFinished) {
            CenteredWorkoutSuccessDialog(
                onConfirm = { onBackClick() }
            )
        }
    }
}