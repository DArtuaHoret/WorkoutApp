package com.example.workoutapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutapp.data.WorkoutSession
import com.example.workoutapp.data.WorkoutSessionItem
import com.example.workoutapp.data.WorkoutSessionSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {

    // sesja
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSession(session: WorkoutSession): Long

    @Update
    suspend fun updateWorkoutSession(session: WorkoutSession)

    @Query("SELECT * FROM workout_sessions ORDER BY finishedAt DESC")
    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    fun getWorkoutSessionById(sessionId: Long): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId LIMIT 1")
    suspend fun getSessionByIdOnce(sessionId: Long): WorkoutSession?

    @Query("""
        SELECT * FROM workout_sessions 
        WHERE scheduledAt >= :dayStart AND scheduledAt < :dayEnd
        ORDER BY scheduledAt ASC
    """)
    fun getSessionsForDate(dayStart: Long, dayEnd: Long): Flow<List<WorkoutSession>>

    @Query("SELECT DISTINCT scheduledAt FROM workout_sessions WHERE scheduledAt IS NOT NULL")
    fun getAllScheduledTimestamps(): Flow<List<Long>>

    // item sesji
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSessionItem(item: WorkoutSessionItem): Long  // zwraca id

    @Update
    suspend fun updateWorkoutSessionItem(item: WorkoutSessionItem)

    @Query("SELECT * FROM workout_session_items WHERE workoutSessionId = :sessionId ORDER BY orderIndex")
    fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>>

    @Query("SELECT * FROM workout_session_items WHERE workoutSessionId = :sessionId ORDER BY orderIndex")
    suspend fun getItemsForSessionOnce(sessionId: Long): List<WorkoutSessionItem>

    @Query("DELETE FROM workout_session_items WHERE id = :itemId")
    suspend fun deleteSessionItem(itemId: Long)

    // set sesji
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSessionSet(set: WorkoutSessionSet)

    @Update
    suspend fun updateWorkoutSessionSet(set: WorkoutSessionSet)

    @Query("SELECT * FROM workout_session_sets WHERE workoutSessionItemId = :itemId ORDER BY setNumber")
    fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>>

    @Query("SELECT * FROM workout_session_sets WHERE workoutSessionItemId = :itemId ORDER BY setNumber")
    suspend fun getSetsForSessionItemOnce(itemId: Long): List<WorkoutSessionSet>

    @Query("DELETE FROM workout_session_sets WHERE workoutSessionItemId = :itemId")
    suspend fun deleteSetsForSessionItem(itemId: Long)
}