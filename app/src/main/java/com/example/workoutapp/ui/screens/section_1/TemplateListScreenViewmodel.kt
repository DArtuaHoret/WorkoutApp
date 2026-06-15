package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.WorkoutTemplateRepository
import com.example.workoutapp.data.WorkoutTemplate as DbWorkoutTemplate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class TemplateListViewModel(
    private val templateRepository: WorkoutTemplateRepository,
) : ViewModel() {

    val templates: StateFlow<List<WorkoutTemplate>> =
        templateRepository.getActiveTemplates()
            .map { list -> list.map { WorkoutTemplate(id = it.id.toString(), name = it.name) } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun deleteTemplate(template: WorkoutTemplate) {
        viewModelScope.launch {
            templateRepository.deleteTemplate(
                DbWorkoutTemplate(name = template.name, id = template.id.toLong())
            )
        }
    }


    suspend fun createTemplate(): String =
        templateRepository.saveTemplate(DbWorkoutTemplate(name = "")).toString()
}