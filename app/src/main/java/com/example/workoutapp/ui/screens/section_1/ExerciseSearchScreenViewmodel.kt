package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.ExerciseRepository
import kotlinx.coroutines.flow.*

// ExerciseSearchViewModel.kt
class ExerciseSearchViewModel(
    private val exerciseRepository: ExerciseRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    // Bezpośrednio combine bez pośredniego StateFlow
    val filteredExercises: StateFlow<List<ExerciseOption>> =
        combine(
            exerciseRepository.getActiveExercisesWithMuscleGroup(),
            _query,
        ) { exercises, query ->
            exercises
                .map { ExerciseOption(id = it.exerciseId.toString(), name = it.exerciseName, muscleGroup = it.muscleGroupName) }
                .filter { query.isBlank() || it.name.contains(query, ignoreCase = true) }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(newQuery: String) { _query.value = newQuery }
}