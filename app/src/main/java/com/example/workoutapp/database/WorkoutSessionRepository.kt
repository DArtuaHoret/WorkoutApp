package com.example.workoutapp.database

import com.example.workoutapp.data.WorkoutSession
import com.example.workoutapp.data.WorkoutSessionItem
import com.example.workoutapp.data.WorkoutSessionSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface WorkoutSessionRepository {
    fun getAllSessions(): Flow<List<WorkoutSession>>
    suspend fun getSessionById(sessionId: Long): Flow<List<WorkoutSession>>
    fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>>
    fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>>
    suspend fun saveSession(session: WorkoutSession)
    suspend fun updateSession(session: WorkoutSession)
    suspend fun saveSessionItem(item: WorkoutSessionItem)
    suspend fun updateSessionItem(item: WorkoutSessionItem)
    suspend fun saveSessionSet(set: WorkoutSessionSet)
    suspend fun updateSessionSet(set: WorkoutSessionSet)
}

class WorkoutSessionRepositoryImpl(private val sessionDao: WorkoutSessionDao) : WorkoutSessionRepository {
    override fun getAllSessions(): Flow<List<WorkoutSession>> = sessionDao.getAllWorkoutSessions()

    override suspend fun getSessionById(sessionId: Long): Flow<List<WorkoutSession>> = sessionDao.getWorkoutSessionById(sessionId)

    override fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>> =
        sessionDao.getItemsForSession(sessionId)

    override fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>> =
        sessionDao.getSetsForSessionItem(itemId)

    override suspend fun saveSession(session: WorkoutSession) {
        withContext(Dispatchers.IO) {
            sessionDao.insertWorkoutSession(session)
        }
    }

    override suspend fun updateSession(session: WorkoutSession) {
        withContext(Dispatchers.IO) {
            sessionDao.updateWorkoutSession(session)
        }
    }

    override suspend fun saveSessionItem(item: WorkoutSessionItem) {
        withContext(Dispatchers.IO) {
            sessionDao.insertWorkoutSessionItem(item)
        }
    }

    override suspend fun updateSessionItem(item: WorkoutSessionItem) {
        withContext(Dispatchers.IO) {
            sessionDao.updateWorkoutSessionItem(item)
        }
    }
    override suspend fun saveSessionSet(set: WorkoutSessionSet) {
        withContext(Dispatchers.IO) {
            sessionDao.insertWorkoutSessionSet(set)
        }
    }

    override suspend fun updateSessionSet(set: WorkoutSessionSet) {
        withContext(Dispatchers.IO) {
            sessionDao.updateWorkoutSessionSet(set)
        }
    }
}
