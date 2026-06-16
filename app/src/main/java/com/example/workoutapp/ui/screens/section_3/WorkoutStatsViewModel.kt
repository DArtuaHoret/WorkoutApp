package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.ui.reusableContents.Section_3.MuscleGroupShare
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class WorkoutStatsUiState(
    val isLoading: Boolean = true,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val totalDays: Int = 0,
    val completedWorkouts: Int = 0,
    val muscleDistribution: List<MuscleGroupShare> = emptyList(),
    val averageTimeInSeconds: Int = 0
)

class WorkoutStatsViewModel(
    savedStateHandle: SavedStateHandle,
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
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1

            val workoutLogs = emptyList<Any>()

            val calculatedCompletedWorkouts = workoutLogs.size

            val calculatedAvgTime = if (calculatedCompletedWorkouts > 0) 0 else 0

            val calculatedDistribution = if (workoutLogs.isEmpty()) {
                emptyList<MuscleGroupShare>()
            } else {
                emptyList<MuscleGroupShare>()
            }

            _uiState.value = WorkoutStatsUiState(
                isLoading = false,
                startDate = startDate,
                endDate = endDate,
                totalDays = daysBetween,
                completedWorkouts = calculatedCompletedWorkouts,
                muscleDistribution = calculatedDistribution,
                averageTimeInSeconds = calculatedAvgTime
            )
        }
    }
}