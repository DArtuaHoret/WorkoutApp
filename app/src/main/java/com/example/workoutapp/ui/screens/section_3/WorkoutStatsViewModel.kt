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

// Reprezentacja stanu interfejsu (co ekran ma wyświetlić)
data class WorkoutStatsUiState(
    val isLoading: Boolean = true,
    val totalDays: Int = 0,
    val completedWorkouts: Int = 0,
    val muscleDistribution: List<MuscleGroupShare> = emptyList(),
    val averageTimeInSeconds: Int = 0
)

class WorkoutStatsViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    // 1. BEZPIECZNE POBIERANIE ARGUMENTÓW (Zgodnie z architekturą Twojej aplikacji)
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
            // 1. Obliczenie całkowitej liczby dni (plus 1, aby zakres był włączny)
            val daysBetween = ChronoUnit.DAYS.between(startDate, endDate).toInt() + 1

            // 2. Symulacja zapytania do bazy danych.
            // Na razie wymuszamy pustą listę, co odpowiada rzeczywistości (brak treningów).
            // W przyszłości zamienisz to na: val workoutLogs = historyRepository.getWorkoutsBetweenDates(startDate, endDate)
            val workoutLogs = emptyList<Any>() // Typ Any użyty tymczasowo jako placeholder

            // 3. Dynamiczne obliczenia na podstawie pobranej listy
            val calculatedCompletedWorkouts = workoutLogs.size // Zwróci 0

            // Bezpieczne liczenie średniej (chroni przed błędem dzielenia przez zero)
            val calculatedAvgTime = if (calculatedCompletedWorkouts > 0) {
                // Docelowo: workoutLogs.sumOf { it.durationInSeconds } / calculatedCompletedWorkouts
                0
            } else {
                0
            }

            // Bezpieczne liczenie rozkładu mięśni
            val calculatedDistribution = if (workoutLogs.isEmpty()) {
                emptyList<MuscleGroupShare>() // Pusta lista wyczyści wykresy
            } else {
                // Docelowo: logika grupowania partii mięśniowych
                emptyList<MuscleGroupShare>()
            }

            // 4. Aktualizacja stanu interfejsu rzeczywistymi obliczeniami
            _uiState.value = WorkoutStatsUiState(
                isLoading = false,
                totalDays = daysBetween,
                completedWorkouts = calculatedCompletedWorkouts,
                muscleDistribution = calculatedDistribution,
                averageTimeInSeconds = calculatedAvgTime
            )
        }
    }
}