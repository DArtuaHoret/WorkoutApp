package com.example.workoutapp.ui.reusableContents.Section_3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.stringResource
import com.example.workoutapp.R
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Dialog
import java.time.LocalDate
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import com.example.workoutapp.ui.reusableContents.Section_4.NutrientChip
import java.time.format.DateTimeFormatter



@Composable
fun TrainingProgressCard(
    totalSessions: Int,
    completedWorkouts: Int,
    modifier: Modifier = Modifier,
) {

    val percentage = if (totalSessions > 0) {
        (completedWorkouts.toFloat() / totalSessions.toFloat()) * 100f
    } else {
        0f
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier.size(200.dp),
                contentAlignment = Alignment.Center,
            ) {
                val progressColor = Color(0xFF5E9C52)
                val strokeWidth = 14.dp

                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(strokeWidth / 2)
                ) {
                    drawArc(
                        color = Color(0xFF333333),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = strokeWidth.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )

                    drawArc(
                        color = progressColor,
                        startAngle = -90f,
                        sweepAngle = (percentage / 100f) * 360f,
                        useCenter = false,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = strokeWidth.toPx(),
                            cap = androidx.compose.ui.graphics.StrokeCap.Round
                        )
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = String.format(java.util.Locale.US, "%.1f%%", percentage),
                        color = Color(0xFF5E9C52),
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = stringResource(R.string.completed_workouts_title),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(
                    R.string.all_workouts_count,
                    totalSessions
                ),
                color = Color(0xFFCCCCCC),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = stringResource(
                    R.string.completed_workouts_count,
                    completedWorkouts
                ),
                color = Color(0xFFCCCCCC),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(name = "TrainingProgressCard", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTrainingProgressCard() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .width(320.dp)
        ) {
            TrainingProgressCard(
                totalSessions = 168,
                completedWorkouts = 115
            )
        }
    }
}


data class MuscleGroupShare(
    val name: String,
    val percentage: Int
)

@Composable
fun MuscleGroupDistributionCard(
    distribution: List<MuscleGroupShare>,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.muscle_group_distribution),
) {
    val maxPercentage = distribution.maxOfOrNull { it.percentage }?.toFloat() ?: 100f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 20.dp)
        ) {
            Text(
                text = title,
                color = Color(0xFFE5D5C5),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                distribution.forEach { entry ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = entry.name,
                            color = Color(0xFFCCCCCC),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .width(85.dp)
                                .padding(end = 10.dp)
                        )

                        Box(
                            modifier = Modifier
                                .width(1.5.dp)
                                .height(34.dp)
                                .background(Color(0xFF555555))
                        )

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .height(28.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val pct = entry.percentage.coerceIn(0, 100)

                            if (pct > 0 && maxPercentage > 0f) {

                                val weightFraction = pct.toFloat() / maxPercentage

                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(weightFraction)
                                            .height(24.dp)
                                            .background(Color(0xFF5E9C52))
                                    )

                                    if (weightFraction < 1f) {
                                        Spacer(modifier = Modifier.weight(1f - weightFraction))
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }


                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$pct%",
                                color = Color(0xFFE5D5C5),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "MuscleGroupDistributionCard - Fixed", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewMuscleGroupDistributionCard() {
    val sampleData = listOf(
        MuscleGroupShare("Klatka", 35),
        MuscleGroupShare("Plecy", 25),
        MuscleGroupShare("Nogi", 20),
        MuscleGroupShare("Ramiona", 10),
        MuscleGroupShare("Brzuch", 5),
        MuscleGroupShare("Barki", 5)
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .width(340.dp)
        ) {
            MuscleGroupDistributionCard(distribution = sampleData)
        }
    }
}




@Composable
fun AverageTimeCard(
    averageTimeInSeconds: Int,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.average_training_time),
) {
    val minutes = averageTimeInSeconds / 60
    val seconds = averageTimeInSeconds % 60

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                color = Color(0xFFE5D5C5),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = stringResource(R.string.timer_icon_description),
                    tint = Color(0xFF5E9C52),
                    modifier = Modifier
                        .size(36.dp)
                        .padding(end = 8.dp)
                )

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = "$minutes",
                        color = Color(0xFF5E9C52),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.minutes_short),
                        color = Color(0xFFCCCCCC),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.alignByBaseline()
                    )

                    Spacer(modifier = Modifier.width(12.dp))


                    Text(
                        text = String.format(java.util.Locale.US, "%02d", seconds),
                        color = Color(0xFF5E9C52),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.seconds_short),
                        color = Color(0xFFCCCCCC),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.alignByBaseline()
                    )
                }
            }
        }
    }
}

@Preview(name = "AverageTimeCard - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewAverageTimeCard() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .width(360.dp)
        ) {

            AverageTimeCard(averageTimeInSeconds = 2904)
        }
    }
}



@Composable
fun CompletedWorkoutCard(
    workoutName: String,
    note: String,
    startedAtFormatted: String?,
    finishedAtFormatted: String?,
    icon: ImageVector,
    isCompleted: Boolean = true,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Text(
                    text = stringResource(
                        R.string.workout_set,
                        workoutName
                    ),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (note.isNotBlank()) {
                    Text(
                        text = note,
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                val timeText = when {
                    startedAtFormatted != null && finishedAtFormatted != null ->
                        stringResource(
                            R.string.workout_time,
                            startedAtFormatted,
                            finishedAtFormatted
                        )
                    startedAtFormatted != null ->
                        stringResource(
                            R.string.workout_started,
                            startedAtFormatted
                        )
                    else -> null
                }

                if (timeText != null) {
                    Text(
                        text = timeText,
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = if (isCompleted) Color(0xFF5E9C52) else Color(0xFFD32F2F),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = stringResource(
                            if (isCompleted)
                                R.string.workout_completed
                            else
                                R.string.workout_not_completed
                        ),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}


@Preview(name = "Single CompletedWorkoutCard - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSingleCompletedWorkoutCard() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            CompletedWorkoutCard(
                workoutName = "GÓRA CIAŁA",
                note = "Skupiłem się na wolniejszym tempie",
                startedAtFormatted = "18:30",
                finishedAtFormatted = "19:45",
                icon = Icons.Filled.FitnessCenter,
                isCompleted = true
            )
        }
    }
}

@Preview(name = "Single UncompletedWorkoutCard - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSingleUncompletedWorkoutCard() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            CompletedWorkoutCard(
                workoutName = "NOGI",
                note = "",
                startedAtFormatted = null,
                finishedAtFormatted = null,
                icon = Icons.Filled.FitnessCenter,
                isCompleted = false
            )
        }
    }
}

@Preview(name = "CompletedWorkoutsList - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCompletedWorkoutsList() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CompletedWorkoutCard(
                workoutName = "GÓRA CIAŁA",
                note = "Skupiłem się na wolniejszym tempie",
                startedAtFormatted = "18:30",
                finishedAtFormatted = "19:45",
                icon = Icons.Default.FitnessCenter,
                isCompleted = true
            )

            CompletedWorkoutCard(
                workoutName = "TRENING CARDIO",
                note = "",
                startedAtFormatted = null,
                finishedAtFormatted = null,
                icon = Icons.AutoMirrored.Filled.DirectionsRun,
                isCompleted = false
            )
        }
    }
}

@Preview(name = "WorkoutActionButtons - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWorkoutActionButtons() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            ActionButton(
                onClick = { },
                label = "WYBIERZ I PRZYPISZ TRENING",
                style = ActionButtonStyle.LightFilled
            )


            ActionButton(
                onClick = { },
                label = "OBEJRZYJ STATYSTYKI",
                icon = null,
                style = ActionButtonStyle.LightFilled
            )
        }
    }
}


@Composable
fun DateSelectionDialog(
    initialDate: LocalDate = LocalDate.now(),
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var tempDate by remember { mutableStateOf(initialDate) }
    val pickerStartDate = remember { initialDate }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(14.dp),
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),

            ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_date),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                WheelDatePicker(
                    startDate = pickerStartDate,
                    textColor = Color.White,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        color = Color(0xFFD7DAD7).copy(alpha = 0.2f),
                        border = BorderStroke(width = 1.dp, color = Color(0xFFFFFFFF))
                    ),
                    onSnappedDate = { snappedDate ->
                        tempDate = snappedDate
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                ActionButton(
                    onClick = { onDateSelected(tempDate) },
                    label = stringResource(R.string.confirm),
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }
        }
    }
}

@Preview(name = "Date Selection Dialog Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewDateSelectionDialog() {
    MaterialTheme {
        DateSelectionDialog(
            onDismissRequest = {},
            onDateSelected = {}
        )
    }
}






@Composable
fun DateBox(label: String, date: LocalDate?, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .border(1.dp, Color(0xFF444444), RoundedCornerShape(8.dp))
        .clickable { onClick() }
        .padding(12.dp)
    ) {
        Text(text = label, color = Color(0xFFAAAAAA), fontSize = 12.sp)
        Text(
            text =
                date?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    ?: stringResource(R.string.choose),
            color = if (date == null) Color.Gray else Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}









@Composable
fun CenteredDateSelectionDialog(
    initialDate: LocalDate = LocalDate.now(),
    onVisibleChange: (Boolean) -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    var tempDate by remember { mutableStateOf(initialDate) }

    val pickerStartDate = remember { initialDate }

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
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
                .border(2.dp, Color.White, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_date),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                WheelDatePicker(
                    startDate = pickerStartDate,
                    textColor = Color.White,
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        color = Color(0xFFD7DAD7).copy(alpha = 0.2f),
                        border = BorderStroke(width = 1.dp, color = Color(0xFFFFFFFF))
                    ),
                    onSnappedDate = { snappedDate ->
                        tempDate = snappedDate
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                ActionButton(
                    onClick = {
                        onDateSelected(tempDate)
                        onVisibleChange(false)
                    },
                    label = stringResource(R.string.confirm),
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }
        }
    }
}

@Preview(name = "Centered Date Selection Dialog - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCenteredDateSelectionDialog() {
    MaterialTheme {
        CenteredDateSelectionDialog(
            onVisibleChange = {},
            onDateSelected = {}
        )
    }
}





@Composable
fun CenteredDateRangeSelector(
    initialStartDate: LocalDate = LocalDate.now().minusWeeks(1),
    initialEndDate: LocalDate = LocalDate.now(),
    onVisibleChange: (Boolean) -> Unit,
    onDateRangeConfirmed: (LocalDate, LocalDate) -> Unit
) {
    var startDate by remember { mutableStateOf(initialStartDate) }
    var endDate by remember { mutableStateOf(initialEndDate) }

    var showStartDialog by remember { mutableStateOf(false) }
    var showEndDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onVisibleChange(false) },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
                .border(2.dp, Color.White, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_data_range),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showStartDialog = true }
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.date_from), color = Color.Gray, fontSize = 16.sp)
                    Text(startDate.toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Divider(color = Color(0xFF333333), thickness = 1.dp)


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showEndDialog = true }
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Data do:", color = Color.Gray, fontSize = 16.sp)
                    Text(endDate.toString(), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(32.dp))

                ActionButton(
                    onClick = { onDateRangeConfirmed(startDate, endDate) },
                    label = stringResource(R.string.confirm_range),
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }
        }
    }


    if (showStartDialog) {
        CenteredDateSelectionDialog(
            initialDate = startDate,
            onVisibleChange = { showStartDialog = it },
            onDateSelected = {
                startDate = it
                showStartDialog = false
            }
        )
    }

    if (showEndDialog) {
        CenteredDateSelectionDialog(
            initialDate = endDate,
            onVisibleChange = { showEndDialog = it },
            onDateSelected = {
                endDate = it
                showEndDialog = false
            }
        )
    }
}

@Preview(name = "Centered Date Range Selector - Preview", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCenteredDateRangeSelector() {
    MaterialTheme {
        CenteredDateRangeSelector(
            initialStartDate = LocalDate.now().minusWeeks(1),
            initialEndDate = LocalDate.now(),
            onVisibleChange = {},
            onDateRangeConfirmed = { _, _ -> }
        )
    }
}


data class TemplateSelectionItem(
    val id: String,
    val name: String
)

@Composable
fun CenteredTemplateSelectionDialog(
    templates: List<TemplateSelectionItem>,
    onVisibleChange: (Boolean) -> Unit,
    onTemplateSelected: (String) -> Unit
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
        Card(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.65f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {}
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(14.dp),
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.choose_template),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                if (templates.isEmpty()) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_templates_available),
                            color = Color(0xFF888888),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    androidx.compose.foundation.lazy.LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(templates.size) { index ->
                            val template = templates[index]
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onTemplateSelected(template.id)
                                        onVisibleChange(false)
                                    }
                                    .border(1.dp, Color(0xFF444444), RoundedCornerShape(8.dp)),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF222222))
                            ) {
                                Text(
                                    text = template.name,
                                    color = Color.White,
                                    modifier = Modifier.padding(16.dp),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                ActionButton(
                    onClick = { onVisibleChange(false) },
                    label = stringResource(R.string.cancel),
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }
        }
    }
}

@Preview(name = "Template Selection Dialog - Populated", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCenteredTemplateSelectionDialogPopulated() {
    val mockTemplates = listOf(
        TemplateSelectionItem("1", "Trening FBW (Full Body Workout)"),
        TemplateSelectionItem("2", "Push / Pull / Legs"),
        TemplateSelectionItem("3", "Klatka + Triceps"),
        TemplateSelectionItem("4", "Kardio 45 min")
    )
    MaterialTheme {
        CenteredTemplateSelectionDialog(
            templates = mockTemplates,
            onVisibleChange = {},
            onTemplateSelected = {}
        )
    }
}

@Preview(name = "Template Selection Dialog - Empty", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewCenteredTemplateSelectionDialogEmpty() {
    MaterialTheme {
        CenteredTemplateSelectionDialog(
            templates = emptyList(),
            onVisibleChange = {},
            onTemplateSelected = {}
        )
    }
}


data class NutritionItem(
    val label: String,
    val current: Float,
    val total: Float,
    val unit: String,
    val color: Color
)

@Composable
fun NutritionProgressBar(
    item: NutritionItem,
    modifier: Modifier = Modifier
) {
    val progress = if (item.total > 0f) (item.current / item.total).coerceIn(0f, 1f) else 0f

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFF333333))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(item.color)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = item.label,
                color = Color(0xFFAAAAAA),
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${item.current.toInt()}",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "/${item.total.toInt()} ${item.unit}",
            color = Color(0xFF777777),
            fontSize = 11.sp
        )
    }
}

@Composable
fun NutritionProgressBarRow(
    kcal: NutritionItem,
    protein: NutritionItem,
    fats: NutritionItem,
    carbs: NutritionItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        NutritionProgressBar(item = kcal, modifier = Modifier.weight(1f))
        NutritionProgressBar(item = protein, modifier = Modifier.weight(1f))
        NutritionProgressBar(item = fats, modifier = Modifier.weight(1f))
        NutritionProgressBar(item = carbs, modifier = Modifier.weight(1f))
    }
}

@Composable
fun LoggedProductItemCard(
    productName: String,
    productDescription: String = "",
    kcal: String,
    protein: String,
    fat: String,
    carbs: String,
    grams: String,
    onCardClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onGramsChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(14.dp))
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(14.dp))
            .clickable(onClick = onCardClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (productDescription.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = productDescription,
                        color = Color(0xFF888888),
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = Color(0xFFFF4D4D),
                    modifier = Modifier.size(20.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            NutrientChip(
                label = stringResource(R.string.kcal),
                value = kcal
            )
            NutrientChip(
                label = stringResource(R.string.protein),
                value = "${protein}g"
            )
            NutrientChip(
                label = stringResource(R.string.fat),
                value = "${fat}g"
            )
            NutrientChip(
                label = stringResource(R.string.carbohydrates_short),
                value = "${carbs}g"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = grams,
                onValueChange = { onGramsChange(it) },
                label = {
                    Text(
                        text = stringResource(R.string.grams),
                        color = Color(0xFF888888),
                        fontSize = 12.sp,
                    )
                },
                suffix = {
                    Text(
                        text = stringResource(R.string.grams_unit),
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Color.White,
                    unfocusedBorderColor = Color(0xFF555555),
                    focusedTextColor     = Color.White,
                    unfocusedTextColor   = Color.White,
                    cursorColor          = Color.White,
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(130.dp),
            )
        }
    }
}

