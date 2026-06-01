package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

import com.example.workoutapp.ui.calendar.WorkoutCalendar
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutHistoryScreen(
    selectedDate: LocalDate?,
    workoutDays: Set<LocalDate>,
    onDateSelected: (LocalDate) -> Unit,
    onBackClick: () -> Unit,
    onAssignWorkoutClick: () -> Unit,
    onViewStatsClick: () -> Unit,
    onViewWorkoutDetailsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Historia treningów",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Powrót",
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { onBackClick() }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
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
                        style = ActionButtonStyle.LightFilled
                    )
                }

                ActionButton(
                    onClick = onAssignWorkoutClick,
                    label = "WYBIERZ I PRZYPISZ TRENING",
                    style = ActionButtonStyle.LightFilled
                )

                ActionButton(
                    onClick = onViewStatsClick,
                    label = "OBEJRZYJ STATYSTYKI",
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}