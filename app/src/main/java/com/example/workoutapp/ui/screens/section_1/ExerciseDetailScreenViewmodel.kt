package com.example.workoutapp.ui.screens.section_1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.WorkoutTemplateItem
import com.example.workoutapp.data.WorkoutTemplateSet
import com.example.workoutapp.database.ExerciseRepository
import com.example.workoutapp.database.WorkoutTemplateRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class ExerciseDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val exerciseRepository: ExerciseRepository,
    private val templateRepository: WorkoutTemplateRepository,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.ExerciseDetail>()
    val isNewExercise: Boolean = args.exerciseId.isEmpty()

    private val _exerciseName = MutableStateFlow(args.exerciseName)
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _selectedMuscleGroups = MutableStateFlow<Set<String>>(emptySet())
    val selectedMuscleGroups: StateFlow<Set<String>> = _selectedMuscleGroups.asStateFlow()

    private val _sets = MutableStateFlow(
        listOf(ExerciseSetState(id = UUID.randomUUID().toString()))
    )
    val sets: StateFlow<List<ExerciseSetState>> = _sets.asStateFlow()

    private val _exerciseNote = MutableStateFlow(args.note ?: "")
    val exerciseNote: StateFlow<String> = _exerciseNote.asStateFlow()

    val muscleGroups: StateFlow<List<String>> =
        exerciseRepository.getAllMuscleGroups()
            .map { list -> list.map { it.name } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList(),
            )

    init {
        if (!isNewExercise) loadExercise(args.exerciseId)
    }

    fun onExerciseNameChange(newName: String) { _exerciseName.value = newName }
    fun onMuscleGroupsChange(newGroups: Set<String>) { _selectedMuscleGroups.value = newGroups }
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
        viewModelScope.launch {
            val templateId = args.templateId.toLongOrNull() ?: return@launch
            val isEditing = args.itemId.isNotEmpty()



            if (isEditing) {
                val itemId = args.itemId.toLong()
                val exerciseId = args.exerciseId.toLong()

                // Zaktualizuj grupy mięśniowe
                exerciseRepository.unlinkAllMuscleGroupsForExercise(exerciseId)  // ← usuń stare
                val allGroups = exerciseRepository.getAllMuscleGroups().first()
                _selectedMuscleGroups.value.forEach { groupName ->
                    val group = allGroups.firstOrNull { it.name == groupName } ?: return@forEach
                    exerciseRepository.linkExerciseToMuscleGroup(exerciseId, group.id)
                }

                // Usuń stare sety i wstaw nowe
                templateRepository.deleteSetsForItem(itemId)
                _sets.value.forEachIndexed { index, setState ->
                    templateRepository.saveTemplateSet(
                        WorkoutTemplateSet(
                            workoutTemplateItemId = itemId,
                            setNumber             = index + 1,
                            reps                  = setState.reps,
                            weight                = setState.weight.toDouble(),
                            restTime              = setState.rest,
                        )
                    )
                }
            } else {
                // 1. Zapisz lub pobierz exercise
                val exerciseId: Long = if (isNewExercise) {
                    val newExercise = Exercise(
                        name     = _exerciseName.value,
                        isCustom = true,
                    )
                    exerciseRepository.saveExercise(newExercise)
                    exerciseRepository.getActiveExercises().first()
                        .lastOrNull { it.name == _exerciseName.value }?.id ?: return@launch
                } else {
                    args.exerciseId.toLong()
                }

                // 2. Powiąż ćwiczenie z grupami mięśniowymi
                val allGroups = exerciseRepository.getAllMuscleGroups().first()
                _selectedMuscleGroups.value.forEach { groupName ->
                    val group = allGroups.firstOrNull { it.name == groupName } ?: return@forEach
                    exerciseRepository.linkExerciseToMuscleGroup(exerciseId, group.id)
                }

                // 3. Wstaw WorkoutTemplateItem
                val orderIndex = templateRepository.getItemsForTemplate(templateId).first().size
                val item = WorkoutTemplateItem(
                    workoutTemplateId = templateId,
                    exerciseId        = exerciseId,
                    orderIndex        = orderIndex,
                    note              = _exerciseNote.value.ifBlank { null },
                )
                val itemId = templateRepository.saveTemplateItem(item)

                // 4. Wstaw serie
                _sets.value.forEachIndexed { index, setState ->
                    templateRepository.saveTemplateSet(
                        WorkoutTemplateSet(
                            workoutTemplateItemId = itemId,
                            setNumber             = index + 1,
                            reps                  = setState.reps,
                            weight                = setState.weight.toDouble(),
                            restTime              = setState.rest,
                        )
                    )
                }
            }
        }
    }

    fun onExerciseNoteChange(newNote: String) { _exerciseNote.value = newNote }

    private fun loadExercise(exerciseId: String) {
        viewModelScope.launch {
            val id = exerciseId.toLongOrNull() ?: return@launch
            exerciseRepository.getExerciseById(id).first().firstOrNull()?.let { exercise ->
                _exerciseName.value = exercise.name
            }
            exerciseRepository.getMuscleGroupsForExercise(id)
                .first()
                .map { it.name }
                .toSet()
                .let { _selectedMuscleGroups.value = it }

            // Załaduj sety jeśli to edycja istniejącego itemu
            val itemId = args.itemId.toLongOrNull() ?: return@launch
            templateRepository.getSetsForItem(itemId).first()
                .mapIndexed { _, set ->
                    ExerciseSetState(
                        id     = UUID.randomUUID().toString(),
                        weight = set.weight.toInt(),
                        reps   = set.reps,
                        rest   = set.restTime,
                    )
                }
                .let { if (it.isNotEmpty()) _sets.value = it }
        }
    }
}