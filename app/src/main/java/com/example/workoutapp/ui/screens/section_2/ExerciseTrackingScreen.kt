package com.example.workoutapp.ui.screens.active_workout

import androidx.compose.foundation.layout.*
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
    isTimerRunning: Boolean = true,
    initialShowDescription: Boolean = false,
    onBackClick: () -> Unit,
    onRepsChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    onRestTimeChange: (Int) -> Unit,
    onTimerFinished: () -> Unit,
    onDoneClick: () -> Unit,
    onSaveDescription: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDescription by remember(initialShowDescription) { mutableStateOf(initialShowDescription) }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Aktywny trening",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
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
                    actions = {
                        IconButton(onClick = { showDescription = true }) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Informacje o ćwiczeniu",
                                tint = Color.White
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Spacer(modifier = Modifier.weight(1f))

                ExerciseTimer(
                    title = if (isResting) "ODPOCZYNEK" else "WYKONANIE ĆWICZENIA",
                    initialSeconds = restTime,
                    initialIsRunning = isTimerRunning,
                    onTimerFinished = onTimerFinished,
                    onFinishClick = onDoneClick
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (showDescription) {
            CenteredDescriptionDialog(
                initialDescription = exerciseDescription,
                onVisibleChange = { showDescription = it },
                onSave = { updatedText ->
                    onSaveDescription(updatedText)
                }
            )
        }
    }
}