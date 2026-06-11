package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.WorkoutTemplateRepository
import com.example.workoutapp.ui.reusableContents.Section_3.TemplateSelectionItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WorkoutCalendarViewModel(
    private val templateRepository: WorkoutTemplateRepository
) : ViewModel() {

    val availableTemplates: StateFlow<List<TemplateSelectionItem>> =
        templateRepository.getActiveTemplates()
            .map { list ->
                list.map { template ->
                    TemplateSelectionItem(
                        id = template.id.toString(),
                        name = template.name
                    )
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
}