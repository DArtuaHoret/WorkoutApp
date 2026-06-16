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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Pojedyncza seria ćwiczenia sesji z pełnymi danymi
data class SessionExerciseSetEntry(
    val sessionItemId: Long,
    val sessionSetId: Long,
    val exerciseId: Long,
    val exerciseName: String,
    val setNumber: Int,
    val plannedReps: Int,
    val plannedWeight: Double,
    val plannedRestTime: Int,   // NOWE
    val note: String?
)

class TemplateExercisesViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.TemplateExercises>()
    val sessionId = args.templateId.toLongOrNull() ?: 0L  // templateId reużywamy jako sessionId
    val sessionName = args.templateName

    private val _exerciseSets = MutableStateFlow<List<SessionExerciseSetEntry>>(emptyList())
    val exerciseSets: StateFlow<List<SessionExerciseSetEntry>> = _exerciseSets

    init {
        loadExerciseSets()
    }

    private fun loadExerciseSets() {
        viewModelScope.launch {
            sessionRepository.getItemsForSession(sessionId).collect { items ->
                val allSets = mutableListOf<SessionExerciseSetEntry>()
                items.forEach { item ->
                    // Pobierz nazwę ćwiczenia
                    val exerciseName = runCatching {
                        exerciseRepository.getExerciseById(item.exerciseId)
                            .first().firstOrNull()?.name ?: "Ćwiczenie"
                    }.getOrElse { "Ćwiczenie" }

                    val sets = sessionRepository.getSetsForSessionItemOnce(item.id)
                    if (sets.isEmpty()) {
                        allSets.add(
                            SessionExerciseSetEntry(
                                sessionItemId = item.id,
                                sessionSetId = 0L,
                                exerciseId = item.exerciseId,
                                exerciseName = exerciseName,
                                setNumber = 1,
                                plannedReps = 0,
                                plannedWeight = 0.0,
                                plannedRestTime = 60,
                                note = item.note
                            )
                        )
                    } else {
                        sets.forEach { set ->
                            allSets.add(
                                SessionExerciseSetEntry(
                                    sessionItemId = item.id,
                                    sessionSetId = set.id,
                                    exerciseId = item.exerciseId,
                                    exerciseName = exerciseName,
                                    setNumber = set.setNumber,
                                    plannedReps = set.plannedReps,
                                    plannedWeight = set.plannedWeight,
                                    plannedRestTime = set.plannedRestTime,  // NOWE
                                    note = item.note
                                )
                            )
                        }
                    }
                }
                _exerciseSets.value = allSets
            }
        }
    }

    // Usuwa ćwiczenie (item) i wszystkie jego serie z sesji
    fun deleteSessionItem(sessionItemId: Long) {
        viewModelScope.launch {
            sessionRepository.deleteSetsForSessionItem(sessionItemId)
            sessionRepository.deleteSessionItem(sessionItemId)
        }
    }
}