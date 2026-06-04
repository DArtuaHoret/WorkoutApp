package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TemplateListViewModel : ViewModel() {

    private val _templates = MutableStateFlow<List<WorkoutTemplate>>(emptyList())
    val templates: StateFlow<List<WorkoutTemplate>> = _templates.asStateFlow()

    init {
        loadTemplates()
    }

    fun loadTemplates() {
        // TODO: _templates.value = templateRepository.getAll()
        _templates.value = listOf(
            WorkoutTemplate(id = "1", name = "Push Day A"),
            WorkoutTemplate(id = "2", name = "Pull Day B"),
            WorkoutTemplate(id = "3", name = "Nogi"),
        )
    }

    fun deleteTemplate(template: WorkoutTemplate) {
        // TODO: templateRepository.delete(template)
        _templates.value = _templates.value.filter { it.id != template.id }
    }
}