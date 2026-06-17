package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.data.WorkoutSession
import com.example.workoutapp.data.WorkoutSessionItem
import com.example.workoutapp.data.WorkoutSessionSet
import com.example.workoutapp.database.WorkoutSessionRepository
import com.example.workoutapp.database.WorkoutTemplateRepository
import com.example.workoutapp.ui.reusableContents.Section_3.TemplateSelectionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

data class SessionWithName(
    val session: WorkoutSession,
    val templateName: String
)

class WorkoutCalendarViewModel(
    private val templateRepository: WorkoutTemplateRepository,
    private val sessionRepository: WorkoutSessionRepository
) : ViewModel() {

    val availableTemplates: StateFlow<List<TemplateSelectionItem>> =
        templateRepository.getActiveTemplates()
            .map { list ->
                list.map { template ->
                    TemplateSelectionItem(id = template.id.toString(), name = template.name)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    val scheduledDates: StateFlow<Set<LocalDate>> =
        sessionRepository.getAllScheduledDates()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptySet()
            )

    private val _sessionsForSelectedDate = MutableStateFlow<List<SessionWithName>>(emptyList())
    val sessionsForSelectedDate: StateFlow<List<SessionWithName>> = _sessionsForSelectedDate

    fun assignTemplateToDate(templateId: String, date: LocalDate) {
        viewModelScope.launch {
            val tmplId = templateId.toLongOrNull() ?: return@launch
            val template = templateRepository.getTemplateById(tmplId) ?: return@launch

            val scheduledAt = Date(
                date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )

            // 1. Utwórz WorkoutSession
            val sessionId = sessionRepository.saveSession(
                WorkoutSession(
                    status = "PLANNED",
                    scheduledAt = scheduledAt,
                    note = template.description,
                    workoutTemplateId = tmplId
                )
            )

            // 2. Skopiuj każdy WorkoutTemplateItem → WorkoutSessionItem
            val templateItems = templateRepository.getItemsForTemplate(tmplId).first()
            templateItems.forEach { templateItem ->
                val sessionItemId = sessionRepository.saveSessionItem(
                    WorkoutSessionItem(
                        workoutSessionId = sessionId,
                        exerciseId = templateItem.exerciseId,
                        orderIndex = templateItem.orderIndex,
                        plannedSets = 0,
                        note = templateItem.note
                    )
                )

                // 3. Skopiuj każdy WorkoutTemplateSet → WorkoutSessionSet (z restTime!)
                val templateSets = templateRepository.getSetsForItem(templateItem.id).first()
                templateSets.forEach { templateSet ->
                    sessionRepository.saveSessionSet(
                        WorkoutSessionSet(
                            workoutSessionItemId = sessionItemId,
                            setNumber = templateSet.setNumber,
                            plannedReps = templateSet.reps,
                            plannedWeight = templateSet.weight,
                            plannedRestTime = templateSet.restTime  // ← kopiujemy restTime
                        )
                    )
                }
            }

            loadSessionsForDate(date)
        }
    }

    fun loadSessionsForDate(date: LocalDate) {
        viewModelScope.launch {
            sessionRepository.getSessionsForDate(date).collect { sessions ->
                val sessionsWithNames = sessions.map { session ->
                    val templateName = session.workoutTemplateId?.let { templateId ->
                        runCatching {
                            templateRepository.getTemplateById(templateId)?.name
                        }.getOrNull()
                    } ?: "Trening"
                    SessionWithName(session = session, templateName = templateName)
                }
                _sessionsForSelectedDate.value = sessionsWithNames
            }
        }
    }
}