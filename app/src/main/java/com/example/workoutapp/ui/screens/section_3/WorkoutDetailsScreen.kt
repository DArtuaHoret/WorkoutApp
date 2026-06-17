package com.example.workoutapp.ui.screens.section_3

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_3.CompletedWorkoutCard
import com.example.workoutapp.ui.reusableContents.Section_3.NutritionItem
import com.example.workoutapp.ui.reusableContents.Section_3.NutritionProgressBarRow
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WorkoutDetailsScreen(
    viewModel: WorkoutDetailsViewModel,
    onBackClick: () -> Unit,
    onViewExercisesClick: (String) -> Unit,
    onStartWorkoutClick: (sessionId: String, templateId: String) -> Unit,
    modifier: Modifier = Modifier,
    onMealIconClick: () -> Unit,

) {
    val uiState by viewModel.uiState.collectAsState()

    val formattedDateHeader = remember(uiState.date) {
        val localePl = Locale.forLanguageTag("pl-PL")
        val datePartFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", localePl)
        val datePart = uiState.date.format(datePartFormatter).uppercase(localePl)
        val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", localePl)
        val dayOfWeek = uiState.date.format(dayOfWeekFormatter).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(localePl) else it.toString()
        }
        "$datePart ($dayOfWeek)"
    }

    var selectedSessionId by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // ── HEADER ──────────────────────────────────────────────
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.offset(x = (-12).dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Powrót",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Szczegóły dnia",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = (-16).dp)
                )

                Spacer(modifier = Modifier.weight(1f))  // ← dodaj to

                IconButton(
                    onClick = onMealIconClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = "Dieta",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = formattedDateHeader,
                color = Color(0xFFAAAAAA),
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        // ── LISTA TRENINGÓW (tylko karty, scrollowalna) ─────────
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(uiState.workoutSessions, key = { it.id }) { session ->
                CompletedWorkoutCard(
                    workoutName = session.workoutName,
                    note = session.note,
                    startedAtFormatted = session.startedAtFormatted,
                    finishedAtFormatted = session.finishedAtFormatted,
                    icon = session.icon,
                    isCompleted = session.isCompleted,
                    modifier = Modifier.clickable {
                        selectedSessionId =
                            if (selectedSessionId == session.id) null else session.id
                    }
                )
            }
        }

        // ── POPRAWKA 2: strefa przycisków — stała, nad progress barami ──
        AnimatedVisibility(visible = selectedSessionId != null) {
            val selectedSession = uiState.workoutSessions.find { it.id == selectedSessionId }

            val isToday = uiState.date == java.time.LocalDate.now()

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                ActionButton(
                    onClick = {
                        viewModel.deleteSession(selectedSessionId!!) {
                            selectedSessionId = null
                        }
                    },
                    label = "USUŃ TRENING",
                    icon = Icons.Default.Delete,
                    style = ActionButtonStyle.DangerFilled
                )
                ActionButton(
                    onClick = { onViewExercisesClick(selectedSessionId!!) },
                    label = "ZOBACZYĆ SZCZEGÓŁY",
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )


                if (selectedSession?.isCompleted != true && isToday) {

                    ActionButton(
                        onClick = {
                            val templateId =
                                uiState.sessionToTemplateId[selectedSessionId!!] ?: ""
                            onStartWorkoutClick(selectedSessionId!!, templateId)
                        },
                        label = "ROZPOCZĄĆ TRENING",
                        icon = null,
                        style = ActionButtonStyle.LightFilled
                    )
                } else if (selectedSession?.isCompleted != true && !isToday) {
                    Text(
                        text = "Trening można rozpocząć tylko w dniu, na który jest zaplanowany",
                        color = Color(0xFFAAAAAA),
                        fontSize = 13.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // ── PROGRESS BARY — zawsze na samym dole ────────────────
        NutritionProgressBarRow(
            kcal = NutritionItem("Kcal", uiState.currentKcal, uiState.totalKcal, "kcal", Color(0xFFE040FB)),
            protein = NutritionItem("Prot.", uiState.currentProtein, uiState.totalProtein, "g", Color(0xFF40C4FF)),
            fats = NutritionItem("Fats", uiState.currentFats, uiState.totalFats, "g", Color(0xFFFFD740)),
            carbs = NutritionItem("Carbs", uiState.currentCarbs, uiState.totalCarbs, "g", Color(0xFFB388FF))
        )
    }
}