package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.data.WorkoutTemplate
import com.example.workoutapp.database.TemplateExerciseEntry
import com.example.workoutapp.database.WorkoutTemplateRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// TemplateDetailViewModel.kt
class TemplateDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val templateRepository: WorkoutTemplateRepository,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.TemplateDetail>()

    // Jeden StateFlow dla ID — null oznacza "jeszcze nie zapisano"
    private val _dbId = MutableStateFlow<Long?>(args.id.toLongOrNull())
    val dbId: StateFlow<Long?> = _dbId.asStateFlow()

    private val _templateName = MutableStateFlow(args.name)
    val templateName: StateFlow<String> = _templateName.asStateFlow()

    val exercises: StateFlow<List<ExerciseEntry>> = _dbId
        .flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else templateRepository.getExerciseEntriesForTemplate(id)
                .map { list -> list.map { it.toExerciseEntry() } }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        if (_dbId.value == null) {
            viewModelScope.launch {
                _dbId.value = templateRepository.saveTemplate(WorkoutTemplate(name = ""))
            }
        }
    }

    fun onTemplateNameChange(newName: String) { _templateName.value = newName }

    fun saveTemplate() {
        val id = _dbId.value ?: return
        viewModelScope.launch {
            templateRepository.updateTemplate(WorkoutTemplate(name = _templateName.value, id = id))
        }
    }

    fun deleteExercise(exercise: ExerciseEntry) {
        val itemId = exercise.id.toLong()
        viewModelScope.launch {
            templateRepository.deleteSetsForItem(itemId)
            templateRepository.deleteTemplateItem(itemId)
        }
    }
}

// Mapper poza klasą — czytelniejszy
private fun TemplateExerciseEntry.toExerciseEntry() = ExerciseEntry(
    id         = itemId.toString(),
    exerciseId = exerciseId.toString(),
    name       = exerciseName,
    series     = setCount.toString(),
    weight     = "${weight.toInt()}kg",
    restTime   = "${restTime}s",
    note       = note ?: "",
)