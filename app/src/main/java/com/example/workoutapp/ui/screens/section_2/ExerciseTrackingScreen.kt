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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveWorkoutScreen(
    exerciseName: String,
    exerciseDescription: String,
    currentSet: Int,
    reps: Int,
    weight: Int,
    restTime: Int,
    isResting: Boolean,
    restsCompleted: Int,
    isWorkoutFinished: Boolean,
    initialShowDescription: Boolean = false,
    initialShowExitDialog: Boolean = false,
    onBackClick: () -> Unit,
    onRepsChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onRestTimeChange: (Int) -> Unit,
    onDoneClick: () -> Unit,
    onTimerFinished: () -> Unit,
    onSaveDescription: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDescription by remember(initialShowDescription) { mutableStateOf(initialShowDescription) }
    var showExitDialog by remember(initialShowExitDialog) { mutableStateOf(initialShowExitDialog) }

    BackHandler(enabled = !isWorkoutFinished) {
        showExitDialog = true
    }

    Box(modifier = modifier.fillMaxSize()) {

        // WARSTWA 1: Interfejs treningowy
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Aktywny trening", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        IconButton(onClick = { showExitDialog = true }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Wróć", tint = Color.White)
                        }
                    },
                    actions = {
                        IconButton(onClick = { showDescription = true }) {
                            Icon(Icons.Default.Info, "Informacje o ćwiczeniu", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
                )
            },
            containerColor = Color.Black,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                ExerciseSetCardDetailed(
                    exerciseName = exerciseName,
                    setNumber = currentSet,
                    weight = weight,
                    onWeightChange = onWeightChange,
                    reps = reps,
                    onRepsChange = onRepsChange,
                    rest = restTime,
                    onRestChange = onRestTimeChange
                )

                Spacer(modifier = Modifier.height(48.dp))

                // resetKey zmienia się przy każdej zmianie fazy lub powtórzenia → timer się resetuje
                key(currentSet, exerciseName, isResting, restsCompleted) {
                    ExerciseTimer(
                        title = if (isResting) "ODPOCZYNEK" else "WYKONANIE ĆWICZENIA",
                        resetKey = Triple(currentSet, isResting, restsCompleted),
                        initialSeconds = restTime,
                        initialIsRunning = isResting, // odpoczynek startuje automatycznie
                        isExercisePhase = !isResting,
                        onTimerFinished = {},
                        onFinishClick = {
                            if (isResting) {
                                onTimerFinished()
                            } else {
                                onDoneClick()
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }

        // WARSTWA 2: Opis ćwiczenia
        if (showDescription) {
            CenteredDescriptionDialog(
                initialDescription = exerciseDescription,
                onVisibleChange = { showDescription = it },
                onSave = { updatedText -> onSaveDescription(updatedText) }
            )
        }

        // WARSTWA 3: Potwierdzenie wyjścia
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

        // WARSTWA 4: Sukces — trening zakończony
        if (isWorkoutFinished) {
            CenteredWorkoutSuccessDialog(
                onConfirm = { onBackClick() }
            )
        }
    }
}