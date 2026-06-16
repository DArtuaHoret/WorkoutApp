package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.ExerciseRepository
import kotlinx.coroutines.flow.*

class ExerciseSearchViewModel(
    private val exerciseRepository: ExerciseRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _showOnlyCustom = MutableStateFlow(false)
    val showOnlyCustom: StateFlow<Boolean> = _showOnlyCustom.asStateFlow()

    private val _lang = MutableStateFlow("pl") // ← NOWE

    fun setLang(lang: String) { _lang.value = lang } // ← NOWE

    val filteredExercises: StateFlow<List<ExerciseOption>> =
        combine(
            exerciseRepository.getActiveExercisesWithMuscleGroup(),
            _query,
            _showOnlyCustom,
            _lang,
        ) { exercises, query, onlyCustom, lang ->
            exercises
                .map {
                    ExerciseOption(
                        id = it.exerciseId.toString(),
                        name = if (lang == "en" && it.exerciseNameEn.isNotBlank()) // ← NOWE
                            it.exerciseNameEn else it.exerciseName,
                        muscleGroup = it.muscleGroupName,
                        isCustom = it.isCustom,
                        photoUrl = it.photoUrl,
                    )
                }
                .filter { query.isBlank() || it.name.contains(query, ignoreCase = true) }
                .filter { !onlyCustom || !it.isCustom }
        }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun onQueryChange(newQuery: String) { _query.value = newQuery }
    fun onToggleShowOnlyCustom() { _showOnlyCustom.value = !_showOnlyCustom.value }
}