package com.example.workoutapp.database

import com.example.workoutapp.data.WorkoutSession
import com.example.workoutapp.data.WorkoutSessionItem
import com.example.workoutapp.data.WorkoutSessionSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId

interface WorkoutSessionRepository {
    fun getAllSessions(): Flow<List<WorkoutSession>>
    suspend fun getSessionById(sessionId: Long): Flow<List<WorkoutSession>>
    suspend fun getSessionByIdOnce(sessionId: Long): WorkoutSession?
    fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>>
    suspend fun getItemsForSessionOnce(sessionId: Long): List<WorkoutSessionItem>
    fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>>
    suspend fun getSetsForSessionItemOnce(itemId: Long): List<WorkoutSessionSet>
    suspend fun saveSession(session: WorkoutSession): Long
    suspend fun updateSession(session: WorkoutSession)
    suspend fun saveSessionItem(item: WorkoutSessionItem): Long  // zwraca id
    suspend fun updateSessionItem(item: WorkoutSessionItem)

    suspend fun deleteSession(sessionId: Long)
    suspend fun deleteSessionItem(itemId: Long)
    suspend fun deleteSetsForSessionItem(itemId: Long)
    suspend fun saveSessionSet(set: WorkoutSessionSet)
    suspend fun updateSessionSet(set: WorkoutSessionSet)
    fun getSessionsForDate(date: LocalDate): Flow<List<WorkoutSession>>
    fun getAllScheduledDates(): Flow<Set<LocalDate>>

    fun getSessionsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<WorkoutSession>>
}

class WorkoutSessionRepositoryImpl(private val sessionDao: WorkoutSessionDao) : WorkoutSessionRepository {

    override fun getAllSessions(): Flow<List<WorkoutSession>> =
        sessionDao.getAllWorkoutSessions()

    override suspend fun getSessionById(sessionId: Long): Flow<List<WorkoutSession>> =
        sessionDao.getWorkoutSessionById(sessionId)

    override suspend fun getSessionByIdOnce(sessionId: Long): WorkoutSession? =
        withContext(Dispatchers.IO) { sessionDao.getSessionByIdOnce(sessionId) }

    override fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>> =
        sessionDao.getItemsForSession(sessionId)

    override suspend fun getItemsForSessionOnce(sessionId: Long): List<WorkoutSessionItem> =
        withContext(Dispatchers.IO) { sessionDao.getItemsForSessionOnce(sessionId) }

    override fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>> =
        sessionDao.getSetsForSessionItem(itemId)

    override suspend fun getSetsForSessionItemOnce(itemId: Long): List<WorkoutSessionSet> =
        withContext(Dispatchers.IO) { sessionDao.getSetsForSessionItemOnce(itemId) }

    override suspend fun saveSession(session: WorkoutSession): Long =
        withContext(Dispatchers.IO) { sessionDao.insertWorkoutSession(session) }

    override suspend fun updateSession(session: WorkoutSession) =
        withContext(Dispatchers.IO) { sessionDao.updateWorkoutSession(session) }

    override suspend fun saveSessionItem(item: WorkoutSessionItem): Long =
        withContext(Dispatchers.IO) { sessionDao.insertWorkoutSessionItem(item) }

    override suspend fun updateSessionItem(item: WorkoutSessionItem) =
        withContext(Dispatchers.IO) { sessionDao.updateWorkoutSessionItem(item) }

    override suspend fun deleteSession(sessionId: Long) =
        withContext(Dispatchers.IO) { sessionDao.deleteSession(sessionId) }

    override suspend fun deleteSessionItem(itemId: Long) =
        withContext(Dispatchers.IO) { sessionDao.deleteSessionItem(itemId) }

    override suspend fun deleteSetsForSessionItem(itemId: Long) =
        withContext(Dispatchers.IO) { sessionDao.deleteSetsForSessionItem(itemId) }

    override suspend fun saveSessionSet(set: WorkoutSessionSet) =
        withContext(Dispatchers.IO) { sessionDao.insertWorkoutSessionSet(set) }

    override suspend fun updateSessionSet(set: WorkoutSessionSet) =
        withContext(Dispatchers.IO) { sessionDao.updateWorkoutSessionSet(set) }

    override fun getSessionsForDate(date: LocalDate): Flow<List<WorkoutSession>> {
        val zone = ZoneId.systemDefault()
        val dayStart = date.atStartOfDay(zone).toInstant().toEpochMilli()
        val dayEnd = date.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()
        return sessionDao.getSessionsForDate(dayStart, dayEnd)
    }

    override fun getAllScheduledDates(): Flow<Set<LocalDate>> {
        val zone = ZoneId.systemDefault()
        return sessionDao.getAllScheduledTimestamps().map { timestamps: List<Long> ->
            timestamps.mapNotNull { ts: Long ->
                runCatching {
                    java.time.Instant.ofEpochMilli(ts).atZone(zone).toLocalDate()
                }.getOrNull()
            }.toSet()
        }
    }

    override fun getSessionsBetweenDates(startDate: LocalDate, endDate: LocalDate): Flow<List<WorkoutSession>> {
        val zone = ZoneId.systemDefault()
        val rangeStart = startDate.atStartOfDay(zone).toInstant().toEpochMilli()
        val rangeEnd = endDate.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()
        return sessionDao.getSessionsForDate(rangeStart, rangeEnd)
    }
}