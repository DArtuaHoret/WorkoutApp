package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.data.WorkoutSessionSet
import com.example.workoutapp.database.ExerciseRepository
import com.example.workoutapp.database.WorkoutSessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class SessionSetState(
    val setId: Long = 0L,
    val setNumber: Int,
    val reps: Int = 8,
    val weight: Int = 0,
    val restTime: Int = 60
)

class SessionExerciseEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.SessionExerciseEdit>()
    val sessionItemId: Long = args.sessionItemId.toLongOrNull() ?: 0L
    val exerciseId: Long = args.exerciseId.toLongOrNull() ?: 0L

    private var lang: String = "pl"

    private val _exerciseName = MutableStateFlow("")
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _sets = MutableStateFlow<List<SessionSetState>>(emptyList())
    val sets: StateFlow<List<SessionSetState>> = _sets.asStateFlow()

    init {
        loadSets()
        loadExerciseName()
    }

    fun setLang(lang: String) {
        this.lang = lang
        loadExerciseName()
    }

    private fun loadExerciseName() {
        viewModelScope.launch {
            val exercise = exerciseRepository.getExerciseById(exerciseId).first().firstOrNull() ?: return@launch
            _exerciseName.value = if (lang == "en" && exercise.nameEn.isNotBlank())
                exercise.nameEn else exercise.name
        }
    }

    private fun loadSets() {
        viewModelScope.launch {
            val existingSets = sessionRepository.getSetsForSessionItemOnce(sessionItemId)
            if (existingSets.isNotEmpty()) {
                _sets.value = existingSets.map { set ->
                    SessionSetState(
                        setId = set.id,
                        setNumber = set.setNumber,
                        reps = set.plannedReps,
                        weight = set.plannedWeight.toInt(),
                        restTime = set.plannedRestTime
                    )
                }
            } else {
                _sets.value = listOf(SessionSetState(setNumber = 1))
            }
        }
    }

    fun onSetChange(index: Int, updated: SessionSetState) {
        _sets.value = _sets.value.toMutableList().also { it[index] = updated }
    }

    fun onAddSet() {
        val nextNumber = (_sets.value.maxOfOrNull { it.setNumber } ?: 0) + 1
        _sets.value = _sets.value + SessionSetState(setNumber = nextNumber)
    }

    fun onDeleteSet(index: Int) {
        if (_sets.value.size <= 1) return
        _sets.value = _sets.value.toMutableList().also { it.removeAt(index) }
    }

    fun deleteExercise(onDone: () -> Unit) {
        viewModelScope.launch {
            sessionRepository.deleteSetsForSessionItem(sessionItemId)
            sessionRepository.deleteSessionItem(sessionItemId)
            onDone()
        }
    }

    fun save(onDone: () -> Unit) {
        viewModelScope.launch {
            sessionRepository.deleteSetsForSessionItem(sessionItemId)
            _sets.value.forEachIndexed { index, setState ->
                sessionRepository.saveSessionSet(
                    WorkoutSessionSet(
                        workoutSessionItemId = sessionItemId,
                        setNumber = index + 1,
                        plannedReps = setState.reps,
                        plannedWeight = setState.weight.toDouble(),
                        plannedRestTime = setState.restTime
                    )
                )
            }
            onDone()
        }
    }
}