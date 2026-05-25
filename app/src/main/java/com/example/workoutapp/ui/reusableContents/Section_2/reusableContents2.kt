package com.example.workoutapp.ui.reusableContents.Section_2

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
                    AdjustableRow(label = "ODPOCZYNEK:", value = rest, onValueChange = onRestChange, step = 15, unit = "s")
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
fun ExerciseDescriptionDialog(
    initialDescription: String,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var description by remember { mutableStateOf(initialDescription) }
    var isEditing by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Szczegóły", style = MaterialTheme.typography.titleLarge)
        },
        text = {
            Column {
                if (isEditing) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Opis ćwiczenia") },
                        modifier = Modifier.fillMaxWidth().height(150.dp)
                    )
                } else {
                    Text(
                        text = description.ifEmpty { "Brak opisu." },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (isEditing) {
                    onSave(description)
                    isEditing = false
                } else {
                    isEditing = true
                }
            }) {
                Text(if (isEditing) "Zapisz" else "Edytuj")
            }
        },
        dismissButton = {
            if (!isEditing) {
                TextButton(onClick = onDismiss) { Text("Zamknij") }
            } else {
                TextButton(onClick = { isEditing = false }) { Text("Anuluj") }
            }
        }
    )
}

@Preview(showBackground = true, name = "Exercise Description Dialog Preview")
@Composable
private fun PreviewExerciseDescriptionDialog() {
    var isDialogOpen by remember { mutableStateOf(true) }
    var currentDescription by remember { mutableStateOf("To jest przykładowy opis ćwiczenia. Kliknij 'Edytuj', aby zmienić jego treść.") }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            if (isDialogOpen) {
                ExerciseDescriptionDialog(
                    initialDescription = currentDescription,
                    onDismiss = { isDialogOpen = false },
                    onSave = { updatedText ->
                        currentDescription = updatedText
                        isDialogOpen = false
                    }
                )
            } else {
                Button(onClick = { isDialogOpen = true }) {
                    Text("Otwórz dialog ponownie")
                }
            }
        }
    }
}



@Composable
fun RestTimer(
    initialSeconds: Int = 59,
    onTimerFinished: () -> Unit = {}
) {
    var timeLeft by remember { mutableIntStateOf(initialSeconds) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = timeLeft, key2 = isRunning) {
        if (isRunning && timeLeft > 0) {
            delay(1000L)
            timeLeft--
        } else if (timeLeft == 0) {
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
            text = "ODPOCZYNEK",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawCircle(
                    color = Color.White,
                    style = Stroke(width = 8.dp.toPx())
                )
            }

            Text(
                text = timeLeft.toString(),
                color = Color.White,
                fontSize = 120.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

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
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewRestTimer() {
    RestTimer(initialSeconds = 59)
}