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
    suspend fun insertWorkoutSession(session: WorkoutSession)

    @Update
    suspend fun updateWorkoutSession(session: WorkoutSession)

    @Query("SELECT * FROM workout_sessions ORDER BY finishedAt DESC")
    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_sessions WHERE id = :sessionId")
    fun getWorkoutSessionById(sessionId: Long): Flow<List<WorkoutSession>>

    // item sesji
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSessionItem(item: WorkoutSessionItem)

    @Update
    suspend fun updateWorkoutSessionItem(item: WorkoutSessionItem)
    @Query("SELECT * FROM workout_session_items WHERE workoutSessionId = :sessionId ORDER BY orderIndex")
    fun getItemsForSession(sessionId: Long): Flow<List<WorkoutSessionItem>>

    // set sesji
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutSessionSet(set: WorkoutSessionSet)

    @Update
    suspend fun updateWorkoutSessionSet(set: WorkoutSessionSet)

    @Query("SELECT * FROM workout_session_sets WHERE workoutSessionItemId = :itemId ORDER BY setNumber")
    fun getSetsForSessionItem(itemId: Long): Flow<List<WorkoutSessionSet>>
}
