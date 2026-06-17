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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

// Ćwiczenie z pogrupowanymi seriami — do wyświetlenia na liście
data class SessionExerciseEntry(
    val sessionItemId: Long,
    val exerciseId: Long,
    val exerciseName: String,
    val photoUrl: String?,
    val note: String?,
    val sets: List<SessionExerciseSetEntry>
)

// Pojedyncza seria — do wyświetlenia w ekranie edycji
data class SessionExerciseSetEntry(
    val sessionItemId: Long,
    val sessionSetId: Long,
    val exerciseId: Long,
    val exerciseName: String,
    val setNumber: Int,
    val plannedReps: Int,
    val plannedWeight: Double,
    val plannedRestTime: Int,
    val note: String?
)

class TemplateExercisesViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.TemplateExercises>()
    val sessionId = args.templateId.toLongOrNull() ?: 0L
    val sessionName = args.templateName

    // Pogrupowane ćwiczenia — każde ćwiczenie zawiera listę swoich serii
    private val _exercises = MutableStateFlow<List<SessionExerciseEntry>>(emptyList())
    val exercises: StateFlow<List<SessionExerciseEntry>> = _exercises

    init {
        observeExercises()
    }

    private fun observeExercises() {
        viewModelScope.launch {
            // Obserwuj items sesji — gdy coś się zmieni (np. po edycji), odświeży listę
            sessionRepository.getItemsForSession(sessionId).collect { items ->
                val exerciseEntries = items.map { item ->
                    val exercise = runCatching {
                        exerciseRepository.getExerciseById(item.exerciseId).first().firstOrNull()
                    }.getOrNull()
                    val exerciseName = exercise?.name ?: "Ćwiczenie"
                    val exercisePhotoUrl = exercise?.photoUrl

                    // Dla każdego itemu obserwuj jego serie reaktywnie
                    val sets = sessionRepository.getSetsForSessionItem(item.id).first()
                        .map { set ->
                            SessionExerciseSetEntry(
                                sessionItemId = item.id,
                                sessionSetId = set.id,
                                exerciseId = item.exerciseId,
                                exerciseName = exerciseName,
                                setNumber = set.setNumber,
                                plannedReps = set.plannedReps,
                                plannedWeight = set.plannedWeight,
                                plannedRestTime = set.plannedRestTime,
                                note = item.note
                            )
                        }

                    SessionExerciseEntry(
                        sessionItemId = item.id,
                        exerciseId = item.exerciseId,
                        exerciseName = exerciseName,
                        photoUrl = exercisePhotoUrl,
                        note = item.note,
                        sets = sets
                    )
                }
                _exercises.value = exerciseEntries
            }
        }
    }

    // Odśwież dane — wywołaj po powrocie z ekranu edycji
    fun refresh() {
        viewModelScope.launch {
            val items = sessionRepository.getItemsForSessionOnce(sessionId)
            val exerciseEntries = items.map { item ->
                val exercise = runCatching {
                    exerciseRepository.getExerciseById(item.exerciseId).first().firstOrNull()
                }.getOrNull()
                val exerciseName = exercise?.name ?: "Ćwiczenie"
                val exercisePhotoUrl = exercise?.photoUrl

                val sets = sessionRepository.getSetsForSessionItemOnce(item.id)
                    .map { set ->
                        SessionExerciseSetEntry(
                            sessionItemId = item.id,
                            sessionSetId = set.id,
                            exerciseId = item.exerciseId,
                            exerciseName = exerciseName,
                            setNumber = set.setNumber,
                            plannedReps = set.plannedReps,
                            plannedWeight = set.plannedWeight,
                            plannedRestTime = set.plannedRestTime,
                            note = item.note
                        )
                    }

                SessionExerciseEntry(
                    sessionItemId = item.id,
                    exerciseId = item.exerciseId,
                    exerciseName = exerciseName,
                    photoUrl = exercisePhotoUrl,
                    note = item.note,
                    sets = sets
                )
            }
            _exercises.value = exerciseEntries
        }
    }

    fun deleteSessionItem(sessionItemId: Long) {
        viewModelScope.launch {
            sessionRepository.deleteSetsForSessionItem(sessionItemId)
            sessionRepository.deleteSessionItem(sessionItemId)
        }
    }
}