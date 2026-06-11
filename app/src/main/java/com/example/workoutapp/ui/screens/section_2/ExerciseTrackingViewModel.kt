package com.example.workoutapp.ui.screens.section_2


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.database.WorkoutTemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseTrackingViewModel(
    savedStateHandle: SavedStateHandle,
    private val templateRepository: WorkoutTemplateRepository
) : ViewModel() {

    // Pobranie argumentów przekazanych w nawigacji (templateId oraz dateIso)
    private val args = savedStateHandle.toRoute<Destinations.ActiveWorkout>()

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

    init {
        loadFirstExercise(args.templateId)
    }

    private fun loadFirstExercise(templateId: String) {
        viewModelScope.launch {
            val id = templateId.toLongOrNull() ?: return@launch

            // DOCELOWA LOGIKA POBRANIA Z BAZY:
            // val exercises = templateRepository.getExerciseEntriesForTemplate(id).first()
            // if (exercises.isNotEmpty()) {
            //     val first = exercises.first()
            //     _exerciseName.value = first.exerciseName
            //     _exerciseDescription.value = first.note ?: ""
            //     // tu wyciągasz parametry dla pierwszej serii (reps, weight, restTime) na podstawie id ćwiczenia
            // }

            // MOCK - W tym momencie wstawiam tymczasowe dane pierwszej serii z szablonu,
            // byś mogła od razu przetestować UI. Zastąpisz je kodem wyżej.
            _exerciseName.value = "Wyciskanie sztangi leżąc"
            _exerciseDescription.value = "Pamiętaj o retrakcji łopatek i kontrolowaniu oddechu."
            _currentSet.value = 1
            _reps.value = 8
            _weight.value = 60
            _restTime.value = 90
        }
    }

    // Funkcje do aktualizacji stanu wpisywanego przez użytkownika na ekranie
    fun updateReps(newReps: Int) { _reps.value = newReps }
    fun updateWeight(newWeight: Int) { _weight.value = newWeight }
    fun updateRestTime(newRest: Int) { _restTime.value = newRest }
    fun updateDescription(newDesc: String) { _exerciseDescription.value = newDesc }

    fun onDoneClick() {
        // Uruchomienie timera przerwy
        _isResting.value = true
    }

    fun onTimerFinished() {
        // Zakończenie przerwy i przejście do kolejnej serii
        _isResting.value = false
        _currentSet.value += 1
    }
}