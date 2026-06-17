package com.example.workoutapp.ui.screens.section_3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.database.WorkoutSessionRepository
import com.example.workoutapp.database.WorkoutTemplateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class WorkoutSessionData(
    val id: String,
    val workoutName: String,
    val timeRange: String,
    val icon: ImageVector,
    val isCompleted: Boolean
)

data class WorkoutDetailsUiState(
    val date: LocalDate = LocalDate.now(),
    val workoutSessions: List<WorkoutSessionData> = emptyList(),
    // Mapa sessionId → templateId potrzebna do nawigacji do timera
    val sessionToTemplateId: Map<String, String> = emptyMap()
)

class WorkoutDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionRepository: WorkoutSessionRepository,
    private val templateRepository: WorkoutTemplateRepository
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.HistoryDetails>()
    val date: LocalDate = LocalDate.parse(args.dateIsoString)

    private val _uiState = MutableStateFlow(WorkoutDetailsUiState(date = date))
    val uiState: StateFlow<WorkoutDetailsUiState> = _uiState.asStateFlow()

    init {
        loadSessions()
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

                _uiState.value = WorkoutDetailsUiState(
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