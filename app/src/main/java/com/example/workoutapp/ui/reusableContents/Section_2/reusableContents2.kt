package com.example.workoutapp.ui.reusableContents.Section_2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.drawscope.Stroke
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
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, color = Color(0xFFCCCCCC), fontSize = 12.sp)

        Row(
            modifier = Modifier
                .border(2.dp, Color.White, RoundedCornerShape(50))
                .padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = { if (value > minValue) onValueChange(value - step) }, modifier = Modifier.size(28.dp)) {
                Text(text = "−", color = Color.White, fontSize = 22.sp)
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
            IconButton(onClick = { onValueChange(value + step) }, modifier = Modifier.size(28.dp)) {
                Text(text = "+", color = Color.White, fontSize = 22.sp)
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
    modifier: Modifier = Modifier,
) {
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
                )
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
                    AdjustableRow(label = "CIĘŻAR:", value = weight, onValueChange = onWeightChange, step = 5, unit = "kg")
                    AdjustableRow(label = "POWTÓRZENIA:", value = reps, onValueChange = onRepsChange, step = 1, minValue = 1)
                    AdjustableRow(label = "CZAS:", value = rest, onValueChange = onRestChange, step = 15, unit = "s")
                }

                Spacer(modifier = Modifier.width(24.dp))

                Icon(
                    imageVector = Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(70.dp)
                )
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
                text = "Szczegóły",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Zamknij",
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
                label = { Text("Opis ćwiczenia") },
                modifier = Modifier.fillMaxWidth().height(150.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )
        } else {
            Text(
                text = description.ifEmpty { "Brak opisu." },
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFE0E0E0),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                    text = "EDYTUJ",
                    style = MaterialTheme.typography.titleMedium,

                )
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { isEditing = false }) { Text("Anuluj", color = Color.Gray) }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onSave(description); isEditing = false },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) { Text("Zapisz") }
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



@Composable
fun ExerciseTimer(
    title: String,
    initialSeconds: Int = 59,
    initialIsRunning: Boolean = true,
    onTimerFinished: () -> Unit = {},
    onFinishClick: () -> Unit = {}
) {
    var timeLeft by remember { mutableIntStateOf(initialSeconds) }
    var isRunning by remember { mutableStateOf(initialIsRunning) }

    LaunchedEffect(key1 = timeLeft, key2 = isRunning) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0 && isRunning) {
            isRunning = false
            onTimerFinished()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(32.dp),
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
                    enabled = timeLeft <= 0,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
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

            if (timeLeft > 0) {
                Text(
                    text = timeLeft.toString(),
                    color = Color.White,
                    fontSize = 120.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Skończono",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (timeLeft > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { if (timeLeft > 0) timeLeft-- }) {
                    Text("−", color = Color.White, fontSize = 60.sp)
                }

                TextButton(onClick = { isRunning = !isRunning }) {
                    Icon(
                        imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isRunning) "Pauza" else "Start",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                TextButton(onClick = { timeLeft++ }) {
                    Text("+", color = Color.White, fontSize = 60.sp)
                }
            }
        } else {
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRestTimer() {
    ExerciseTimer(title = "ODPOCZYNEK", initialSeconds = 59)
}