package com.example.workoutapp.ui.screens.section_2

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.data.WorkoutSessionSet
import com.example.workoutapp.data.WorkoutTemplateSet
import com.example.workoutapp.database.TemplateExerciseEntry
import com.example.workoutapp.database.WorkoutSessionRepository
import com.example.workoutapp.database.WorkoutTemplateRepository
import com.example.workoutapp.database.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

// Ujednolicona reprezentacja serii — niezależnie od źródła (Template lub Session)
private data class UnifiedSet(
    val setNumber: Int,
    val reps: Int,
    val weight: Int,
    val restTime: Int
)

// Ujednolicona reprezentacja ćwiczenia
private data class UnifiedExercise(
    val exerciseName: String,
    val setCount: Int,
    val sets: List<UnifiedSet>
)

class ExerciseTrackingViewModel(
    savedStateHandle: SavedStateHandle,
    private val templateRepository: WorkoutTemplateRepository,
    private val sessionRepository: WorkoutSessionRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.ActiveWorkout>()

    // Jeśli sessionId jest podany → czytamy z WorkoutSession*, inaczej z WorkoutTemplate*
    private val sessionId: Long? = args.sessionId?.toLongOrNull()
    private val templateId: String = args.templateId

    private var exerciseList: List<UnifiedExercise> = emptyList()
    private var currentIndex = 0

    private val _exerciseName = MutableStateFlow("Ładowanie...")
    val exerciseName: StateFlow<String> = _exerciseName.asStateFlow()

    private val _exerciseDescription = MutableStateFlow("")
    val exerciseDescription: StateFlow<String> = _exerciseDescription.asStateFlow()

    private val _currentSet = MutableStateFlow(1)
    val currentSet: StateFlow<Int> = _currentSet.asStateFlow()

    private val _reps = MutableStateFlow(0)
    val reps: StateFlow<Int> = _reps.asStateFlow()

    private val _weight = MutableStateFlow(0)
    val weight: StateFlow<Int> = _weight.asStateFlow()

    private val _restTime = MutableStateFlow(60)
    val restTime: StateFlow<Int> = _restTime.asStateFlow()

    private val _isResting = MutableStateFlow(false)
    val isResting: StateFlow<Boolean> = _isResting.asStateFlow()

    private val _isWorkoutFinished = MutableStateFlow(false)
    val isWorkoutFinished: StateFlow<Boolean> = _isWorkoutFinished.asStateFlow()

    private val _restsCompleted = MutableStateFlow(0)
    val restsCompleted: StateFlow<Int> = _restsCompleted.asStateFlow()

    private var currentExerciseSets: List<UnifiedSet> = emptyList()

    private var lang: String = "pl"

    fun setLang(lang: String) {
        this.lang = lang
        if (sessionId != null) loadFromSession(sessionId)
        else loadFromTemplate(templateId)
    }

    init {
        if (sessionId != null) {
            loadFromSession(sessionId)
        } else {
            loadFromTemplate(templateId)
        }
    }

    // ── Ładowanie z WorkoutTemplate* (sekcja 1 / stara ścieżka) ─────────────

    private fun loadFromTemplate(templateId: String) {
        viewModelScope.launch {
            val id = templateId.toLongOrNull() ?: return@launch
            val entries = templateRepository.getExerciseEntriesForTemplate(id).first()
            exerciseList = entries.map { entry ->
                val sets = templateRepository.getSetsForItem(entry.itemId).first()
                UnifiedExercise(
                    exerciseName = entry.exerciseName,
                    setCount = entry.setCount,
                    sets = sets.map { it.toUnified() }
                )
            }
            if (exerciseList.isNotEmpty()) applyExerciseToUi(0)
        }
    }

    // ── Ładowanie z WorkoutSession* (sekcja 3 / trening z kalendarza) ────────

    private fun loadFromSession(sessionId: Long) {
        viewModelScope.launch {
            val items = sessionRepository.getItemsForSessionOnce(sessionId)
            exerciseList = items.map { item ->
                val exerciseName = runCatching {
                    val exercise = exerciseRepository.getExerciseById(item.exerciseId)
                        .first().firstOrNull()
                    if (lang == "en" && exercise?.nameEn?.isNotBlank() == true)
                        exercise.nameEn else exercise?.name ?: "Ćwiczenie"
                }.getOrElse { "Ćwiczenie" }

                val sets = sessionRepository.getSetsForSessionItemOnce(item.id)
                UnifiedExercise(
                    exerciseName = exerciseName,
                    setCount = sets.size,
                    sets = sets.map { it.toUnified() }
                )
            }
            if (exerciseList.isNotEmpty()) applyExerciseToUi(0)
        }
    }

    // ── Wspólna logika UI ────────────────────────────────────────────────────

    private fun applyExerciseToUi(index: Int) {
        if (index < 0 || index >= exerciseList.size) return
        val entry = exerciseList[index]
        currentExerciseSets = entry.sets
        currentIndex = index

        _currentSet.value = 1
        _restsCompleted.value = 0
        _isResting.value = false
        updateUiForCurrentSet()
        _exerciseName.value = entry.exerciseName
    }

    private fun updateUiForCurrentSet() {
        val set = currentExerciseSets.find { it.setNumber == _currentSet.value }
        if (set != null) {
            _reps.value = set.reps
            _weight.value = set.weight
            _restTime.value = set.restTime
        }
        _restsCompleted.value = 0
        _isResting.value = false
    }

    fun onDoneClick() { _isResting.value = true }

    fun onTimerFinished() {
        val currentEntry = exerciseList[currentIndex]

        if (_currentSet.value < currentEntry.setCount) {
            _currentSet.value += 1
            updateUiForCurrentSet()
        } else {
            if (currentIndex + 1 < exerciseList.size) {
                applyExerciseToUi(currentIndex + 1)
            } else {
                _isWorkoutFinished.value = true
            }
        }
    }

    fun updateReps(newReps: Int) { _reps.value = newReps }
    fun updateWeight(newWeight: Int) { _weight.value = newWeight }
    fun updateRestTime(newRest: Int) { _restTime.value = newRest }
    fun updateDescription(newDesc: String) { _exerciseDescription.value = newDesc }
}

// ── Konwertery ───────────────────────────────────────────────────────────────

private fun WorkoutTemplateSet.toUnified() = UnifiedSet(
    setNumber = setNumber,
    reps = reps,
    weight = weight.toInt(),
    restTime = restTime
)

private fun WorkoutSessionSet.toUnified() = UnifiedSet(
    setNumber = setNumber,
    reps = plannedReps,
    weight = plannedWeight.toInt(),
    restTime = plannedRestTime  // teraz czytamy rzeczywisty czas odpoczynku
)