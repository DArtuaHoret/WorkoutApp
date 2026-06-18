package com.example.workoutapp.ui.reusableContents.Section_2

import android.util.Size
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.res.stringResource
import com.example.workoutapp.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_1.resolveImageModel
import kotlinx.coroutines.delay


@Composable
fun AdjustableRow(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    step: Int = 1,
    unit: String = "",
    minValue: Int = 0,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, color = Color(0xFFCCCCCC), fontSize = 12.sp)

        Row(
            modifier = Modifier
                .border(2.dp, if (enabled) Color.White else Color(0xFF555555), RoundedCornerShape(50))
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { if (value > minValue) onValueChange(value - step) },
                modifier = Modifier.size(28.dp),
                enabled = enabled
            ) {
                Text(text = "−", color = if (enabled) Color.White else Color(0xFF555555), fontSize = 22.sp)
            }

            Box(
                modifier = Modifier.width(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when {
                        unit == "s" -> { val m = value / 60; val s = value % 60; String.format("%02d:%02d", m, s) }
                        unit == "kg" -> "$value kg"
                        else -> value.toString()
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            IconButton(
                onClick = { onValueChange(value + step) },
                modifier = Modifier.size(28.dp),
                enabled = enabled
            ) {
                Text(text = "+", color = if (enabled) Color.White else Color(0xFF555555), fontSize = 22.sp)
            }
        }
    }
}
@Composable
fun ExerciseSetCardDetailed(
    exerciseName: String,
    setNumber: Int,
    weight: Int,
    onWeightChange: (Int) -> Unit,
    reps: Int,
    onRepsChange: (Int) -> Unit,
    rest: Int,
    onRestChange: (Int) -> Unit,
    enabled: Boolean = true,
    photoUrl: String? = null,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val imageModel = remember(photoUrl) { resolveImageModel(context, photoUrl) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.White, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exerciseName.uppercase(),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SERIA $setNumber",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AdjustableRow(
                        label = stringResource(R.string.weight_label_colon),
                        value = weight,
                        onValueChange = onWeightChange,
                        step = 5,
                        unit = "kg",
                        enabled = enabled
                    )
                    AdjustableRow(
                        label = stringResource(R.string.reps_label_colon),
                        value = reps,
                        onValueChange = onRepsChange,
                        step = 1,
                        minValue = 1,
                        enabled = enabled
                    )
                    AdjustableRow(
                        label = stringResource(R.string.rest_label_colon),
                        value = rest,
                        onValueChange = onRestChange,
                        step = 10,
                        unit = "s",
                        enabled = enabled
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF2C2C2C)),
                    contentAlignment = Alignment.Center,
                ) {
                    if (imageModel != null) {
                        AsyncImage(
                            model = imageModel,
                            contentDescription = exerciseName,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.FitnessCenter,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSetCardDetailed() {
    val weight = androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(60) }
    val reps = androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(8) }
    val rest = androidx.compose.runtime.remember { androidx.compose.runtime.mutableIntStateOf(60) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color.Black),
            verticalArrangement = Arrangement.Center
        ) {
            ExerciseSetCardDetailed(
                exerciseName = "Martwy ciąg",
                setNumber = 2,
                weight = weight.intValue,
                onWeightChange = { weight.intValue = it },
                reps = reps.intValue,
                onRepsChange = { reps.intValue = it },
                rest = rest.intValue,
                onRestChange = { rest.intValue = it }
            )
        }
    }
}





@Composable
fun ExerciseDescriptionContent(
    initialDescription: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf(initialDescription) }
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), RoundedCornerShape(28.dp))
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(28.dp))
            .padding(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.details_title),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_description),
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .clickable { onDismiss() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isEditing) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.exercise_description_label)) },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
        } else {
            Text(
                text = description.ifEmpty {
                    stringResource(R.string.no_description)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE0E0E0),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }


        if (!isEditing) {
            Button(
                onClick = { isEditing = true },
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                ),

                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_button),
                    style = MaterialTheme.typography.titleMedium,

                )
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { isEditing = false }) {
                    Text(
                        stringResource(R.string.cancel),
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSave(description); isEditing = false },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) { Text(stringResource(R.string.save)) }
            }
        }

    }
}


@Composable
fun CenteredDescriptionDialog(
    initialDescription: String,
    onVisibleChange: (Boolean) -> Unit,
    onSave: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onVisibleChange(false)
            },
        contentAlignment = Alignment.Center
    ) {
        ExerciseDescriptionContent(
            initialDescription = initialDescription,
            onSave = onSave,
            onDismiss = { onVisibleChange(false) },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
        )
    }
}

@Preview(showBackground = true, name = "Centered Dialog Preview")
@Composable
private fun PreviewCenteredDescriptionDialog() {
    var isDialogVisible by remember { mutableStateOf(true) }
    var currentDescription by remember {
        mutableStateOf("To jest tekst w wycentrowanym kontenerze z przyciemnionym tłem (overlay).")
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isDialogVisible) {
                    CenteredDescriptionDialog(
                        initialDescription = currentDescription,
                        onVisibleChange = { isDialogVisible = it },
                        onSave = { currentDescription = it }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Dialog Content - Edycja")
@Composable
private fun PreviewExerciseDescriptionContentEditing() {
    var currentDescription by remember {
        mutableStateOf("To jest tekst opisu, który zaraz będziemy edytować w podglądzie.")
    }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            PreviewEditingContentWrapper(
                initialDescription = currentDescription,
                onSave = { currentDescription = it },
                onDismiss = {}
            )
        }
    }
}

@Composable
private fun PreviewEditingContentWrapper(
    initialDescription: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var description by remember { mutableStateOf(initialDescription) }
    val isEditing = true

    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), RoundedCornerShape(28.dp))
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(28.dp))
            .padding(24.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.details_title),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.close_description),
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .clickable { onDismiss() }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                unfocusedTextColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onSave(description) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        ) {
            Text(
                text = stringResource(R.string.save),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}



@Composable
fun ExerciseTimer(
    title: String,
    resetKey: Any = Unit,
    initialSeconds: Int = 59,
    initialIsRunning: Boolean = false,
    isExercisePhase: Boolean = false,
    onTimerFinished: () -> Unit = {},
    onFinishClick: () -> Unit = {},
    onTick: (Int) -> Unit = {},          // powiad
    onRunningChanged: (Boolean) -> Unit = {}  // powiad
) {
    var timeLeft by remember(resetKey) { mutableIntStateOf(initialSeconds) }
    var isRunning by remember(resetKey) { mutableStateOf(initialIsRunning) }
    var finishClickHandled by remember(resetKey) { mutableStateOf(false) }

    // powiad
    LaunchedEffect(timeLeft) {
        onTick(timeLeft)
    }

    LaunchedEffect(isRunning) {
        onRunningChanged(isRunning)
    }

    val toneGen = remember { android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 100) }

    DisposableEffect(Unit) {
        onDispose { toneGen.release() }
    }

    LaunchedEffect(key1 = timeLeft, key2 = isRunning) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--

            if (!isExercisePhase && timeLeft in 0..4) {
                if (timeLeft == 0) {
                    toneGen.startTone(android.media.ToneGenerator.TONE_CDMA_ANSWER, 600)
                } else {
                    toneGen.startTone(android.media.ToneGenerator.TONE_CDMA_ANSWER, 120)
                }
            }
        } else if (timeLeft == 0 && isRunning) {
            isRunning = false
            onTimerFinished()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .size(300.dp)
                .clickable(
                    enabled = isExercisePhase || (timeLeft <= 0 && !finishClickHandled),
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    finishClickHandled = true
                    onFinishClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.White,
                    style = Stroke(width = 8.dp.toPx())
                )
            }

            if (isExercisePhase) {
                Text(
                    text = stringResource(R.string.finished_label),
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            } else if (timeLeft > 0) {
                Text(
                    text = timeLeft.toString(),
                    color = Color.White,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = stringResource(R.string.finished_label),
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (!isExercisePhase && timeLeft > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { timeLeft = maxOf(0, timeLeft - 5) },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = stringResource(R.string.decrease_description),
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(
                    onClick = { isRunning = !isRunning },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription =
                            if (isRunning)
                                stringResource(R.string.pause_description)
                            else
                                stringResource(R.string.start_description),
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }

                IconButton(
                    onClick = { timeLeft += 5 },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.increase_description),
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        } else {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewRestTimer() {
    Box(modifier = Modifier.background(Color.Black).padding(16.dp)) {
        ExerciseTimer(
            title = "ODPOCZYNEK",
            initialSeconds = 59,
            initialIsRunning = true,
            onTimerFinished = {},
            onFinishClick = {}
        )
    }
}


@Composable
fun ExitConfirmationDialog(
    title: String = stringResource(R.string.exit_workout_dialog_title),
    message: String = stringResource(R.string.exit_workout_dialog_message),
    confirmText: String = stringResource(R.string.end_workout_button),
    dismissText: String = stringResource(R.string.stay_button),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), RoundedCornerShape(28.dp))
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(28.dp))
            .padding(24.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 16.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionButton(
                onClick = onConfirm,
                label = confirmText,
                icon = null,
                style = ActionButtonStyle.LightFilled
            )

            ActionButton(
                onClick = onDismiss,
                label = dismissText,
                icon = null,
                style = ActionButtonStyle.LightFilled
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "1. Exit Confirmation Card")
@Composable
private fun PreviewExitConfirmationDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ExitConfirmationDialog(
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}



@Composable
fun CenteredExitConfirmationDialog(
    title: String = stringResource(R.string.exit_workout_dialog_title),
    message: String = stringResource(R.string.exit_workout_dialog_message),
    confirmText: String = stringResource(R.string.end_workout_button),
    dismissText: String = stringResource(R.string.stay_button),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onDismiss()
            },
        contentAlignment = Alignment.Center
    ) {
        ExitConfirmationDialog(
            title = title,
            message = message,
            confirmText = confirmText,
            dismissText = dismissText,
            onConfirm = onConfirm,
            onDismiss = onDismiss,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
        )
    }
}

@Preview(showBackground = true, name = "2. Centered Exit Dialog Overlay")
@Composable
private fun PreviewCenteredExitConfirmationDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            CenteredExitConfirmationDialog(
                onConfirm = {},
                onDismiss = {}
            )
        }
    }
}

@Composable
fun WorkoutSuccessDialog(
    title: String = stringResource(R.string.workout_success_title),
    message: String = stringResource(R.string.workout_success_message),
    confirmText: String = stringResource(R.string.ok_button),
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Color(0xFF1A1A1A), RoundedCornerShape(28.dp))
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(28.dp))
            .padding(24.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 16.sp,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        ActionButton(
            onClick = onConfirm,
            label = confirmText,
            icon = null,
            style = ActionButtonStyle.LightFilled
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, name = "3. Workout Success Card")
@Composable
private fun PreviewWorkoutSuccessDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            WorkoutSuccessDialog(
                onConfirm = {}
            )
        }
    }
}

@Composable
fun CenteredWorkoutSuccessDialog(
    title: String = stringResource(R.string.workout_success_title),
    message: String = stringResource(R.string.workout_success_message),
    confirmText: String = stringResource(R.string.ok_button),
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onConfirm()
            },
        contentAlignment = Alignment.Center
    ) {
        WorkoutSuccessDialog(
            title = title,
            message = message,
            confirmText = confirmText,
            onConfirm = onConfirm,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
        )
    }
}


@Preview(showBackground = true, name = "4. Centered Success Dialog Overlay")
@Composable
private fun PreviewCenteredWorkoutSuccessDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            CenteredWorkoutSuccessDialog(
                onConfirm = {}
            )
        }
    }
}