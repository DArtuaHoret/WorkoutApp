package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate

import com.example.workoutapp.ui.calendar.WorkoutCalendar
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateRangeSelector
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredTemplateSelectionDialog
import com.example.workoutapp.ui.reusableContents.Section_3.TemplateSelectionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutHistoryScreen(
    selectedDate: LocalDate?,
    workoutDays: Set<LocalDate>,
    availableTemplates: List<TemplateSelectionItem>,
    onDateSelected: (LocalDate) -> Unit,
    onBackClick: () -> Unit,
    onAssignWorkoutClick: (templateId: String) -> Unit,
    onViewStatsClick: (LocalDate, LocalDate) -> Unit,
    onViewWorkoutDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showStatsRangeSelector by remember { mutableStateOf(false) }
    var showTemplateSelectionDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Historia treningów", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
                )
            },
            containerColor = Color.Black,
            modifier = modifier
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                WorkoutCalendar(
                    selectedDate = selectedDate,
                    onDateSelected = onDateSelected,
                    workoutDays = workoutDays
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (workoutDays.contains(selectedDate)) {
                        ActionButton(
                            onClick = onViewWorkoutDetailsClick,
                            label = "ZOBACZ SZCZEGÓŁY TRENINGU",
                            icon = null,
                            style = ActionButtonStyle.LightFilled
                        )
                    }

                    ActionButton(
                        onClick = { showTemplateSelectionDialog = true },
                        label = "WYBIERZ I PRZYPISZ TRENING",
                        style = ActionButtonStyle.LightFilled
                    )

                    ActionButton(
                        onClick = { showStatsRangeSelector = true },
                        label = "OBEJRZYJ STATYSTYKI",
                        icon = null,
                        style = ActionButtonStyle.LightFilled
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        if (showStatsRangeSelector) {
            CenteredDateRangeSelector(
                onVisibleChange = { showStatsRangeSelector = it },
                onDateRangeConfirmed = { start, end ->
                    showStatsRangeSelector = false
                    onViewStatsClick(start, end)
                }
            )
        }

        if (showTemplateSelectionDialog) {
            CenteredTemplateSelectionDialog(
                templates = availableTemplates,
                onVisibleChange = { showTemplateSelectionDialog = it },
                onTemplateSelected = { selectedTemplateId ->
                    showTemplateSelectionDialog = false
                    onAssignWorkoutClick(selectedTemplateId)
                }
            )
        }
    }
}