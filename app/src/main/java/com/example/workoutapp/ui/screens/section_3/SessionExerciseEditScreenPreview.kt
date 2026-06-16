/*package com.example.workoutapp.ui.screens.section_3

import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// --- Mocki dla celów podglądu ---
class FakeSessionExerciseEditViewModel : SessionExerciseEditViewModel(...) {
    override val exerciseName: StateFlow<String> = MutableStateFlow("Wyciskanie na ławce")
    override val sets: StateFlow<List<ExerciseSet>> = MutableStateFlow(
        listOf(
            ExerciseSet(weight = "60", reps = "10", restTime = "90"),
            ExerciseSet(weight = "65", reps = "8", restTime = "120")
        )
    )

    // Nadpisz metody na puste implementacje, aby uniknąć błędów
    override fun onAddSet() {}
    override fun onDeleteSet(index: Int) {}
    override fun onSetChange(index: Int, set: ExerciseSet) {}
    override fun save(onComplete: () -> Unit) {}
    override fun deleteExercise(onComplete: () -> Unit) {}
}

@Preview(showBackground = true)
@Composable
fun SessionExerciseEditScreenPreview() {
    SessionExerciseEditScreen(
        viewModel = FakeSessionExerciseEditViewModel(),
        onBackClick = {},
        onSaveClick = {},
        onDeleteExerciseClick = {}
    )
}*/