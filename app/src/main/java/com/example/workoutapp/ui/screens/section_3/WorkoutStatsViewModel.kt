package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.database.ExerciseRepository
import com.example.workoutapp.database.WorkoutSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare

data class WorkoutStatsUiState(
    val isLoading: Boolean = true,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val totalSessions: Int = 0,
    val completedWorkouts: Int = 0,
    val muscleDistribution: List<MuscleGroupShare> = emptyList(),
    val averageTimeInSeconds: Int = 0
)


class WorkoutStatsViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.HistoryStats>()

    private val startDate = LocalDate.parse(args.startDateIso)
    private val endDate = LocalDate.parse(args.endDateIso)

    private val _uiState = MutableStateFlow(WorkoutStatsUiState())
    val uiState: StateFlow<WorkoutStatsUiState> = _uiState.asStateFlow()

    init {
        loadAndCalculateStats()
    }

    private fun loadAndCalculateStats() {
        viewModelScope.launch {
            val allSessions = sessionRepository.getSessionsBetweenDates(startDate, endDate).first()
            val totalSessions = allSessions.size

            val workoutLogs = allSessions.filter { it.status == "DONE" }

            val calculatedCompletedWorkouts = workoutLogs.size

            val durationsInSeconds = workoutLogs.mapNotNull { session ->
                val started = session.startedAt
                val finished = session.finishedAt
                if (started != null && finished != null) {
                    (finished.time - started.time) / 1000
                } else null
            }

            val calculatedAvgTime = if (durationsInSeconds.isNotEmpty()) {
                (durationsInSeconds.sum() / durationsInSeconds.size).toInt()
            } else {
                0
            }


            val muscleGroupCounts = mutableMapOf<String, Int>()
            var totalCount = 0

            workoutLogs.forEach { session ->
                val items = sessionRepository.getItemsForSessionOnce(session.id)
                items.forEach { item ->
                    val groups = exerciseRepository.getMuscleGroupsForExercise(item.exerciseId).first()
                    groups.forEach { group ->
                        muscleGroupCounts[group.name] = (muscleGroupCounts[group.name] ?: 0) + 1
                        totalCount++
                    }
                }
            }

            val calculatedDistribution = if (totalCount == 0) {
                emptyList()
            } else {
                muscleGroupCounts.map { (name, count) ->
                    MuscleGroupShare(
                        name = name,
                        percentage = ((count.toDouble() / totalCount) * 100).toInt()
                    )
                }.sortedByDescending { it.percentage }
            }

            _uiState.value = WorkoutStatsUiState(
                isLoading = false,
                startDate = startDate,
                endDate = endDate,
                totalSessions = totalSessions,
                completedWorkouts = calculatedCompletedWorkouts,
                muscleDistribution = calculatedDistribution,
                averageTimeInSeconds = calculatedAvgTime
            )
        }
    }
}