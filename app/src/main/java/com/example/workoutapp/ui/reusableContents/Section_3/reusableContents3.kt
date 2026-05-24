package com.example.workoutapp.ui.reusableContents.Section_3

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
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

// -----------------Anzh-----------
@Composable
fun TrainingProgressCard(
    totalDays: Int,
    completedWorkouts: Int,
    modifier: Modifier = Modifier,
) {

    val percentage = if (totalDays > 0) {
        (completedWorkouts.toFloat() / totalDays.toFloat()) * 100f
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
                        text = "ZREALIZOWANO\nTRENINGÓW",
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
                text = "Całkowita liczba dni: $totalDays",
                color = Color(0xFFCCCCCC),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Zrealizowano treningów: $completedWorkouts",
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
                totalDays = 168,
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
    title: String = "ROZKŁAD GRUP MIĘŚNIOWYCH"
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
    title: String = "ŚREDNI CZAS PROWADZENIA TRENINGU"
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
                    contentDescription = "Stoper",
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
                        text = "min",
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
                        text = "s",
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
    timeRange: String,
    icon: ImageVector,
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
                    text = "Zestaw: $workoutName",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )


                Text(
                    text = "Zrealizowano: $timeRange",
                    color = Color(0xFF888888),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(2.dp))


                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF5E9C52),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "ZREALIZOWANY",
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
                timeRange = "18:30 - 19:45",
                icon = Icons.Filled.FitnessCenter
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
                timeRange = "18:30 - 19:45",
                icon = Icons.Default.FitnessCenter
            )


            CompletedWorkoutCard(
                workoutName = "TRENING CARDIO",
                timeRange = "07:00 - 07:45",
                icon = Icons.Default.DirectionsRun
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






