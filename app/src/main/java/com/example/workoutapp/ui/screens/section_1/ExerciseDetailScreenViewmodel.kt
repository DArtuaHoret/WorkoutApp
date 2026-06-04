package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ExerciseDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.ExerciseDetail>()
    val isNewExercise: Boolean = args.exerciseId.isEmpty()

    private val _exerciseName = MutableStateFlow(args.exerciseName)
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _selectedMuscleGroups = MutableStateFlow<Set<String>>(emptySet())
    val selectedMuscleGroups: StateFlow<Set<String>> = _selectedMuscleGroups.asStateFlow()

    private val _sets = MutableStateFlow<List<ExerciseSetState>>(
        listOf(ExerciseSetState(id = UUID.randomUUID().toString()))
    )
    val sets: StateFlow<List<ExerciseSetState>> = _sets.asStateFlow()

    init {
        if (!isNewExercise) loadExercise(args.exerciseId)
    }

    fun onExerciseNameChange(newName: String) {
        _exerciseName.value = newName
    }

    fun onMuscleGroupsChange(newGroups: Set<String>) {
        _selectedMuscleGroups.value = newGroups
    }

    fun onSetChange(index: Int, updated: ExerciseSetState) {
        _sets.value = _sets.value.toMutableList().also { it[index] = updated }
    }

    fun onAddSet() {
        _sets.value = _sets.value + ExerciseSetState(id = UUID.randomUUID().toString())
    }

    fun onDeleteSet(index: Int) {
        _sets.value = _sets.value.toMutableList().also { it.removeAt(index) }
    }

    fun saveExercise() {
        // TODO: exerciseRepository.save(
        //     templateId   = args.templateId,
        //     exerciseId   = args.exerciseId,
        //     name         = _exerciseName.value,
        //     muscleGroups = _selectedMuscleGroups.value,
        //     sets         = _sets.value,
        // )
    }

    private fun loadExercise(exerciseId: String) {
        // TODO: val exercise = exerciseRepository.get(exerciseId)
        // _exerciseName.value         = exercise.name
        // _selectedMuscleGroups.value = exercise.muscleGroups
        // _sets.value                 = exercise.sets
    }
}