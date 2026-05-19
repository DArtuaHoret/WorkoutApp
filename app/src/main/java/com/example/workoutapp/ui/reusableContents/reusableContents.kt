package com.example.workoutapp.ui.reusableContents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview

/**
 * Reusable card component for adding a new exercise to a workout.
 *
 * @param onAddClick  Callback triggered when the user taps the "+" button or the card.
 * @param label       Header text shown above the button. Default: "Twoje Ćwiczenia:"
 * @param buttonLabel Text shown below the "+" button. Default: "DODAJ ĆWICZENIE"
 * @param modifier    Optional [Modifier] for the outer container.
 */
@Composable
fun AddExerciseCard(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Twoje Ćwiczenia:",
    buttonLabel: String = "DODAJ ĆWICZENIE",
) {
    Card(
        onClick = onAddClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 130.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
        //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            // "+" circle button
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(52.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = buttonLabel,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action label below the button
            Text(
                text = buttonLabel,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "AddExerciseCard – dark bg", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun AddExerciseCardPreviewDark() {
    MaterialTheme {
        AddExerciseCard(
            onAddClick = {},
            modifier = Modifier
                .padding(16.dp)
                .width(320.dp),
        )
    }
}

/**
 * Reusable card for a single exercise in a workout list.
 *
 * @param exerciseName   Name of the exercise, e.g. "Martwy Ciąg"
 * @param series         Series description, e.g. "3 x 8"
 * @param weight         Weight string, e.g. "120 kg"
 * @param restTime       Rest time string, e.g. "02:00"
 * @param note           Optional note shown below the stats, e.g. "Uważaj na technikę."
 * @param imageContent   Slot for the exercise image/placeholder (composable lambda).
 *                       Rendered in a 64×64 dp box on the left side.
 * @param onEditClick    Callback for the edit (pencil) button.
 * @param onDeleteClick  Callback for the delete (trash) button.
 * @param modifier       Optional [Modifier] for the outer container.
 */
@Composable
fun ExerciseItemCard(
    exerciseName: String,
    series: String,
    weight: String,
    restTime: String,
    note: String = "",
    imageContent: @Composable BoxScope.() -> Unit = { ExerciseImagePlaceholder() },
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(14.dp))
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Obrazek (prostokąt pionowy)
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2C2C2C)),
                contentAlignment = Alignment.Center,
            ) {
                imageContent()
            }

            // Większy odstęp między obrazkiem a tekstem
            Spacer(modifier = Modifier.width(10.dp))

            // Kolumna z nazwą, przyciskami i statystykami
            Column(
                modifier = Modifier
                    .weight(1f)
                    //.padding(start = 4.dp) // dodatkowe odsunięcie od obrazka
            ) {
                // Wiersz: nazwa + ikony
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = exerciseName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        IconButton(onClick = onEditClick, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Edit, "Edytuj", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Delete, "Usuń", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                // Statystyki – każda w osobnej linii
                Column  {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Serie: $series", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Ciężar: $weight", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Odpoczynek: $restTime", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                }
            }
        }

        // Notatka pod spodem
        if (note.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = note, color = Color(0xFFCCCCCC), fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

/**
 * Default placeholder shown in the image slot until a real image is loaded.
 * Replace this later with AsyncImage (Coil) or your own image loading solution.
 */
@Composable
fun ExerciseImagePlaceholder() {
    Text(
        text = "🏋️",
        fontSize = 28.sp,
    )
}

@Preview(name = "ExerciseItemCard – with note", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWithNote() {
    MaterialTheme {
        ExerciseItemCard(
            exerciseName = "Martwy Ciąg",
            series = "3 x 8",
            weight = "120 kg",
            restTime = "02:00",
            note = "Uważaj na technikę.",
            modifier = Modifier.padding(12.dp),
        )
    }
}

// Przykład preview – oba komponenty w jednym kontenerze, aby pokazać spójną szerokość
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BothCardsPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AddExerciseCard(onAddClick = {})
        Spacer(modifier = Modifier.height(12.dp))
        ExerciseItemCard(
            exerciseName = "Martwy Ciąg z wynoszeniem rąk za głowe",
            series = "3 x 8",
            weight = "120 kg",
            restTime = "02:00",
            note = "Uważaj na technikę.Uważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikę"
        )
    }
}




