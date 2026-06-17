package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.workoutapp.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.workoutapp.ui.calendar.WorkoutCalendar
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredDateRangeSelector
import com.example.workoutapp.ui.reusableContents.Section_3.CenteredTemplateSelectionDialog

@OptIn(ExperimentalMaterial3Api::class)
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.workout_history_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp),
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
                /*
                if (workoutDays.contains(selectedDate)) {
                */

                    ActionButton(
                        onClick = onViewWorkoutDetailsClick,
                        label = stringResource(R.string.view_workout_details_button),
                        icon = null,
                        style = ActionButtonStyle.LightFilled
                    )
                //}

                ActionButton(
                    onClick = { showTemplateSelectionDialog = true },
                    label = stringResource(R.string.select_assign_workout_button),
                    style = ActionButtonStyle.LightFilled
                )

                ActionButton(
                    onClick = { showStatsRangeSelector = true },
                    label = stringResource(R.string.view_stats_button),
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