package com.example.workoutapp.ui.screens.section_3


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_3.CompletedWorkoutCard
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class WorkoutSessionData(
    val id: String,
    val workoutName: String,
    val timeRange: String,
    val icon: ImageVector,
    val isCompleted: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailsScreen(
    date: LocalDate,
    workoutSessions: List<WorkoutSessionData>,
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val formattedDateHeader = remember(date) {
        val localePl = Locale.forLanguageTag("pl-PL")


        val datePartFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", localePl)
        val datePart = date.format(datePartFormatter).uppercase(localePl)


        val dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", localePl)
        val dayOfWeek = date.format(dayOfWeekFormatter).replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(localePl) else it.toString()
        }

        "$datePart ($dayOfWeek)"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "SZCZEGÓŁY TRENINGÓW DNIA",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Powrót",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black
                )
            )
        },
        containerColor = Color.Black,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(16.dp))

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
                workoutSessions.forEach { session ->
                    CompletedWorkoutCard(
                        workoutName = session.workoutName,
                        timeRange = session.timeRange,
                        icon = session.icon,
                        isCompleted = session.isCompleted,
                        modifier = Modifier.clickable { onWorkoutClick(session.id) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Naciśnij na zestaw, aby zobaczyć\npełny log treningowy",
                color = Color(0xFFAAAAAA),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}