package com.example.workoutapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutapp.data.WorkoutTemplate
import com.example.workoutapp.data.WorkoutTemplateItem
import com.example.workoutapp.data.WorkoutTemplateSet
import kotlinx.coroutines.flow.Flow

data class TemplateExerciseEntry(
    val itemId: Long,
    val exerciseId: Long,
    val exerciseName: String,
    val exerciseNameEn: String,
    val orderIndex: Int,
    val note: String?,
    val setCount: Int,
    val minWeight: Double,
    val maxWeight: Double,
    val minRestTime: Int,
    val maxRestTime: Int,
    val photoUrl: String?,
)

@Dao
interface WorkoutTemplateDao {

    // szablony

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplate(template: WorkoutTemplate): Long

    @Update
    suspend fun updateWorkoutTemplate(template: WorkoutTemplate)

    @Query("UPDATE workout_templates SET isActive = 0 WHERE id = :templateId")
    suspend fun deleteWorkoutTemplate(templateId: Long)

    @Query("SELECT * FROM workout_templates WHERE id = :templateId")
    suspend fun getTemplateById(templateId: Long): WorkoutTemplate?
    @Query("SELECT * FROM workout_templates WHERE isActive = 1 AND name != '' ORDER BY createdAt DESC")
    fun getActiveTemplates(): Flow<List<WorkoutTemplate>>

    @Query("SELECT * FROM workout_template_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Long): WorkoutTemplateItem?

    // cwiki szablonu

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplateItem(item: WorkoutTemplateItem): Long

    @Update
    suspend fun updateWorkoutTemplateItem(item: WorkoutTemplateItem)

    @Query("DELETE FROM workout_template_items WHERE id = :itemId")
    suspend fun deleteWorkoutTemplateItem(itemId: Long)

    @Query("SELECT * FROM workout_template_items WHERE workoutTemplateId = :templateId ORDER BY orderIndex ASC")
    fun getItemsForTemplate(templateId: Long): Flow<List<WorkoutTemplateItem>>

    // serii

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplateSet(set: WorkoutTemplateSet)

    @Update
    suspend fun updateWorkoutTemplateSet(set: WorkoutTemplateSet)

    @Query("DELETE FROM workout_template_sets WHERE workoutTemplateItemId = :itemId")
    suspend fun deleteSetsForItem(itemId: Long)

    @Query("SELECT * FROM workout_template_sets WHERE workoutTemplateItemId = :itemId ORDER BY setNumber")
    fun getSetsForItem(itemId: Long): Flow<List<WorkoutTemplateSet>>

    // ćwiczenia szablonu ze wszystkimi danymi

    @Query("""
        SELECT 
            i.id        AS itemId,
            e.id        AS exerciseId,
            e.name      AS exerciseName,
            e.nameEn    AS exerciseNameEn,
            e.photoUrl  AS photoUrl,
            i.orderIndex,
            i.note,
            COUNT(s.id) AS setCount,
            COALESCE(MIN(s.weight), 0.0)   AS minWeight,
            COALESCE(MAX(s.weight), 0.0)   AS maxWeight,
            COALESCE(MIN(s.restTime), 60)  AS minRestTime,
            COALESCE(MAX(s.restTime), 60)  AS maxRestTime
        FROM workout_template_items i
        JOIN exercises e ON e.id = i.exerciseId
        LEFT JOIN workout_template_sets s ON s.workoutTemplateItemId = i.id
        WHERE i.workoutTemplateId = :templateId
        GROUP BY i.id
        ORDER BY i.orderIndex ASC
    """)
    fun getExerciseEntriesForTemplate(templateId: Long): Flow<List<TemplateExerciseEntry>>
}
