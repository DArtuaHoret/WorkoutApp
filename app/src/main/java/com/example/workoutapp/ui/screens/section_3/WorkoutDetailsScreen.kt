package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_3.CompletedWorkoutCard
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun WorkoutDetailsScreen(
    viewModel: WorkoutDetailsViewModel,
    onBackClick: () -> Unit,
    onViewExercisesClick: (String) -> Unit,
    onStartWorkoutClick: (sessionId: String, templateId: String) -> Unit,
    modifier: Modifier = Modifier
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
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp)
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
        }

        Text(
            text = formattedDateHeader,
            color = Color(0xFFAAAAAA),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            uiState.workoutSessions.forEach { session ->
                CompletedWorkoutCard(
                    workoutName = session.workoutName,
                    timeRange = session.timeRange,
                    icon = session.icon,
                    isCompleted = session.isCompleted,
                    modifier = Modifier.clickable {
                        selectedSessionId = if (selectedSessionId == session.id) null else session.id
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (selectedSessionId != null) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) {
                ActionButton(
                    onClick = { onViewExercisesClick(selectedSessionId!!) },
                    label = "ZOBACZYĆ SZCZEGÓŁY",
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
                ActionButton(
                    onClick = {
                        val templateId = uiState.sessionToTemplateId[selectedSessionId!!] ?: ""
                        onStartWorkoutClick(selectedSessionId!!, templateId)
                    },
                    label = "ROZPOCZĄĆ TRENING",
                    icon = null,
                    style = ActionButtonStyle.LightFilled
                )
            }
        } else {
            Text(
                text = "Naciśnij na zestaw, aby zobaczyć opcje",
                color = Color(0xFFAAAAAA),
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}