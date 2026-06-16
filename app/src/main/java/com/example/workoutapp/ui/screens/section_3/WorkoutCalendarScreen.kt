package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.calendar.WorkoutCalendar
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateRangeSelector
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredTemplateSelectionDialog

@Composable
fun WorkoutHistoryScreen(
    viewModel: WorkoutCalendarViewModel,
    selectedDate: java.time.LocalDate?,
    onDateSelected: (java.time.LocalDate) -> Unit,
    onBackClick: () -> Unit,
    onAssignWorkoutClick: (templateId: String) -> Unit,
    onViewStatsClick: (java.time.LocalDate, java.time.LocalDate) -> Unit,
    onViewWorkoutDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val availableTemplates by viewModel.availableTemplates.collectAsState()
    val workoutDays by viewModel.scheduledDates.collectAsState()

    var showStatsRangeSelector by remember { mutableStateOf(false) }
    var showTemplateSelectionDialog by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Historia treningów",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

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