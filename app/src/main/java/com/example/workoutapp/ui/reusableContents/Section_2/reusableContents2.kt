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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
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
                    //maxLines = 2,
                    //overflow = TextOverflow.Ellipsis,
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
                    AdjustableRow(label = "CIĘŻAR:", value = weight, onValueChange = onWeightChange, step = 5, unit = "kg", enabled = enabled)
                    AdjustableRow(label = "POWTÓRZENIA:", value = reps, onValueChange = onRepsChange, step = 1, minValue = 1, enabled = enabled)
                    AdjustableRow(label = "ODPOCZYNEK:", value = rest, onValueChange = onRestChange, step = 10, unit = "s", enabled = enabled)
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
                text = "ZAPISZ",
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
    isExercisePhase: Boolean = false, // true = faza ćwiczenia (od razu "Skończono"), false = faza odpoczynku (timer)
    onTimerFinished: () -> Unit = {},
    onFinishClick: () -> Unit = {}
) {
    var timeLeft by remember(resetKey) { mutableIntStateOf(initialSeconds) }
    var isRunning by remember(resetKey) { mutableStateOf(initialIsRunning) }
    var finishClickHandled by remember(resetKey) { mutableStateOf(false) }

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
                    // Klikalne gdy: faza ćwiczenia (zawsze) LUB faza odpoczynku gdy timer doszedł do zera
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
                // Faza ćwiczenia — zawsze pokazuj "Skończono"
                Text(
                    text = "Skończono",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            } else if (timeLeft > 0) {
                // Faza odpoczynku — odliczanie
                Text(
                    text = timeLeft.toString(),
                    color = Color.White,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                // Faza odpoczynku — timer doszedł do zera
                Text(
                    text = "Skończono",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Przyciski +/- i play/pause tylko w fazie odpoczynku gdy timer jeszcze biegnie
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
                        contentDescription = "Minus",
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
                        contentDescription = if (isRunning) "Pauza" else "Start",
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
                        contentDescription = "Plus",
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
    // Dodajemy Box z czarnym tłem, aby zasymulować środowisko z ExerciseTrackingScreen
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

// 1. KARTA KOMUNIKATU (Sama zawartość wizualna)
@Composable
fun ExitConfirmationDialog(
    title: String = "Przerwać trening?",
    message: String = "Jeśli wyjdziesz, postęp obecnego treningu zostanie utracony. Czy na pewno chcesz opuścić ten ekran?",
    confirmText: String = "ZAKOŃCZ TRENING",
    dismissText: String = "ZOSTAŃ",
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

// PODGLĄD DLA KARTY KOMUNIKATU
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

// -------------------------------------------------------------------------

// 2. PEŁNOEKRANOWY KONTENER (Zaciemnione tło + wycentrowana karta)
@Composable
fun CenteredExitConfirmationDialog(
    title: String = "Przerwać trening?",
    message: String = "Jeśli wyjdziesz, postęp obecnego treningu zostanie utracony. Czy na pewno chcesz opuścić ten ekran?",
    confirmText: String = "ZAKOŃCZ TRENING",
    dismissText: String = "ZOSTAŃ",
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
                // Zamknięcie po kliknięciu poza obszarem karty
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
                    // Blokada propagacji kliknięć w obrębie karty
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
        )
    }
}

// PODGLĄD DLA PEŁNOEKRANOWEGO KONTENERA
@Preview(showBackground = true, name = "2. Centered Exit Dialog Overlay")
@Composable
private fun PreviewCenteredExitConfirmationDialog() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White) // Użycie bieli wizualizuje przezroczystość nakładki
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
    title: String = "Trening zakończony",
    message: String = "Twój trening zakończył się sukcesem i został pomyślnie zapisany w kalendarzu.",
    confirmText: String = "OK",
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

        // Pojedynczy przycisk zatwierdzający
        ActionButton(
            onClick = onConfirm,
            label = confirmText,
            icon = null,
            style = ActionButtonStyle.LightFilled
        )
    }
}

// PODGLĄD DLA KARTY KOMUNIKATU O SUKCESIE
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
    title: String = "Trening zakończony!",
    message: String = "Twój trening zakończył się sukcesem i został pomyślnie zapisany w kalendarzu.",
    confirmText: String = "OK",
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
                // Zamknięcie po kliknięciu poza obszarem karty traktujemy jako zatwierdzenie (OK)
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
                    // Blokada propagacji kliknięć w obrębie karty, aby kliknięcie w tekst nie zamykało okna
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
                .background(Color.White) // Użycie bieli wizualizuje przezroczystość nakładki
        ) {
            CenteredWorkoutSuccessDialog(
                onConfirm = {}
            )
        }
    }
}