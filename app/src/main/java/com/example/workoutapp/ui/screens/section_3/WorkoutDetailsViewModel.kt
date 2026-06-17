package com.example.workoutapp.ui.screens.section_3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.database.FoodRepository
import com.example.workoutapp.database.SettingsRepository
import com.example.workoutapp.database.WorkoutSessionRepository
import com.example.workoutapp.database.WorkoutTemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

data class WorkoutSessionData(
    val id: String,
    val workoutName: String,
    val timeRange: String,
    val icon: ImageVector,
    val isCompleted: Boolean,


)

data class WorkoutDetailsUiState(
    val date: LocalDate = LocalDate.now(),
    val workoutSessions: List<WorkoutSessionData> = emptyList(),
    // Mapa sessionId → templateId potrzebna do nawigacji do timera
    val sessionToTemplateId: Map<String, String> = emptyMap(),

    val currentKcal: Float = 2f,
    val totalKcal: Float = 2700f,
    val currentProtein: Float = 53f,
    val totalProtein: Float = 150f,
    val currentFats: Float = 7f,
    val totalFats: Float = 80f,
    val currentCarbs: Float = 80f,
    val totalCarbs: Float = 344f,
)

class WorkoutDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val templateRepository: WorkoutTemplateRepository,
    private val foodRepository: FoodRepository,               // ← dodaj
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.HistoryDetails>()
    val date: LocalDate = LocalDate.parse(args.dateIsoString)

    // Konwersja LocalDate → Date (południe, żeby uniknąć problemów z strefą czasową)
    private val dateAsDate: Date = run {
        val cal = Calendar.getInstance()
        cal.set(date.year, date.monthValue - 1, date.dayOfMonth, 12, 0, 0)
        cal.set(Calendar.MILLISECOND, 0)
        cal.time
    }

    private val _uiState = MutableStateFlow(WorkoutDetailsUiState(date = date))
    val uiState: StateFlow<WorkoutDetailsUiState> = _uiState.asStateFlow()

    init {
        loadSessions()
        loadNutrition()
        loadTargets()
    }

    private fun loadTargets() {
        viewModelScope.launch {
            combine(
                settingsRepository.targetKcal,
                settingsRepository.targetProtein,
                settingsRepository.targetFats,
                settingsRepository.targetCarbs,
            ) { kcal, protein, fats, carbs ->
                listOf(
                    kcal.toFloatOrNull()    ?: 2700f,
                    protein.toFloatOrNull() ?: 150f,
                    fats.toFloatOrNull()    ?: 80f,
                    carbs.toFloatOrNull()   ?: 344f,
                )
            }.collect { (kcal, protein, fats, carbs) ->
                _uiState.value = _uiState.value.copy(
                    totalKcal    = kcal,
                    totalProtein = protein,
                    totalFats    = fats,
                    totalCarbs   = carbs,
                )
            }
        }
    }

    private fun loadNutrition() {
        viewModelScope.launch {
            combine(
                foodRepository.getEntriesForDate(dateAsDate),
                foodRepository.getAllActiveProducts(),
            ) { entries, products ->
                val productMap = products.associateBy { it.id }

                var kcal = 0f
                var protein = 0f
                var fats = 0f
                var carbs = 0f

                entries.forEach { entry ->
                    val product = productMap[entry.foodProductId] ?: return@forEach
                    val ratio = entry.grams / 100.0
                    kcal    += (product.calories * ratio).toFloat()
                    protein += (product.protein  * ratio).toFloat()
                    fats    += (product.fat      * ratio).toFloat()
                    carbs   += (product.carbs    * ratio).toFloat()
                }

                Triple(kcal, protein, Triple(fats, carbs, Unit))
            }.collect { (kcal, protein, rest) ->
                val (fats, carbs, _) = rest
                _uiState.value = _uiState.value.copy(
                    currentKcal    = kcal,
                    currentProtein = protein,
                    currentFats    = fats,
                    currentCarbs   = carbs,
                )
            }
        }
    }

    private fun loadSessions() {
        viewModelScope.launch {
            sessionRepository.getSessionsForDate(date).collect { sessions ->
                val sessionsWithNames = sessions.map { session ->
                    val templateName = session.workoutTemplateId?.let { templateId ->
                        runCatching {
                            templateRepository.getTemplateById(templateId)?.name
                        }.getOrNull()
                    } ?: "Trening"

                    WorkoutSessionData(
                        id = session.id.toString(),
                        workoutName = templateName,
                        timeRange = date.toString(),
                        icon = Icons.Filled.FitnessCenter,
                        isCompleted = session.status == "DONE"
                    )
                }

                val sessionToTemplateId = sessions.associate {
                    it.id.toString() to (it.workoutTemplateId?.toString() ?: "")
                }

                // ← .copy() zamiast nowego WorkoutDetailsUiState(...)
                _uiState.value = _uiState.value.copy(
                    date = date,
                    workoutSessions = sessionsWithNames,
                    sessionToTemplateId = sessionToTemplateId
                )
            }
        }
    }

    fun deleteSession(sessionId: String, onDeleted: () -> Unit) {
        val id = sessionId.toLongOrNull() ?: return
        viewModelScope.launch {
            val items = sessionRepository.getItemsForSessionOnce(id)
            items.forEach { item ->
                sessionRepository.deleteSetsForSessionItem(item.id)
                sessionRepository.deleteSessionItem(item.id)
            }
            sessionRepository.deleteSession(id)
            onDeleted()
        }
    }
}