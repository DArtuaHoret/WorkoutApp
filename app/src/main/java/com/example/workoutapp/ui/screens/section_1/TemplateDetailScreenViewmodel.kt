package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TemplateDetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.TemplateDetail>()
    val isNewTemplate: Boolean = args.id.isEmpty()

    private val _templateName = MutableStateFlow(args.name)
    val templateName: StateFlow<String> = _templateName.asStateFlow()

    private val _exercises = MutableStateFlow<List<ExerciseEntry>>(emptyList())
    val exercises: StateFlow<List<ExerciseEntry>> = _exercises.asStateFlow()

    init {
        if (!isNewTemplate) loadExercises(args.id)
    }

    fun onTemplateNameChange(newName: String) {
        _templateName.value = newName
    }

    fun deleteExercise(exercise: ExerciseEntry) {
        // TODO: templateRepository.deleteExercise(exercise)
        _exercises.value = _exercises.value.filter { it.id != exercise.id }
    }

    fun saveTemplate() {
        // TODO: templateRepository.save(id = args.id, name = _templateName.value, exercises = _exercises.value)
    }

    private fun loadExercises(templateId: String) {
        // TODO: _exercises.value = templateRepository.getExercises(templateId)
        _exercises.value = listOf(
            ExerciseEntry(id = "1", name = "Wyciskanie sztangi", series = "4", weight = "80kg", restTime = "90s"),
            ExerciseEntry(id = "2", name = "Rozpiętki", series = "3", weight = "20kg", restTime = "60s"),
        )
    }
}