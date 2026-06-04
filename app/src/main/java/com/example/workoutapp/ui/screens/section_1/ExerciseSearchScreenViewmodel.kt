package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExerciseSearchViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val allExercises = MutableStateFlow<List<ExerciseOption>>(emptyList())

    private val _filteredExercises = MutableStateFlow<List<ExerciseOption>>(emptyList())
    val filteredExercises: StateFlow<List<ExerciseOption>> = _filteredExercises.asStateFlow()

    init {
        loadExercises()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        applyFilter()
    }

    private fun loadExercises() {
        // TODO: allExercises.value = exerciseRepository.getAll()
        allExercises.value = listOf(
            ExerciseOption(id = "1", name = "Wyciskanie sztangi",    muscleGroup = "Klatka piersiowa"),
            ExerciseOption(id = "2", name = "Rozpiętki",             muscleGroup = "Klatka piersiowa"),
            ExerciseOption(id = "3", name = "Wiosłowanie sztangą",   muscleGroup = "Plecy"),
            ExerciseOption(id = "4", name = "Podciąganie",           muscleGroup = "Plecy"),
            ExerciseOption(id = "5", name = "Przysiad ze sztangą",   muscleGroup = "Nogi"),
            ExerciseOption(id = "6", name = "Martwy ciąg",           muscleGroup = "Nogi"),
            ExerciseOption(id = "7", name = "Uginanie ramion",       muscleGroup = "Biceps"),
            ExerciseOption(id = "8", name = "Wyciskanie francuskie", muscleGroup = "Triceps"),
        )
        applyFilter()
    }

    private fun applyFilter() {
        val q = _query.value
        _filteredExercises.value = if (q.isBlank()) allExercises.value
        else allExercises.value.filter { it.name.contains(q, ignoreCase = true) }
    }
}