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

@Dao
interface WorkoutTemplateDao {

    // szablon
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplate(template: WorkoutTemplate)

    @Update
    suspend fun updateWorkoutTemplate(template: WorkoutTemplate)

    @Query("UPDATE workout_templates SET isActive = 0 WHERE id = :templateId")
    suspend fun deleteWorkoutTemplate(templateId: Long)

    @Query("SELECT * FROM workout_templates WHERE isActive = 1 ORDER BY createdAt DESC")
    fun getActiveTemplates(): Flow<List<WorkoutTemplate>>


    // item szablonu
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplateItem(item: WorkoutTemplateItem)

    @Update
    suspend fun updateWorkoutTemplateItem(item: WorkoutTemplateItem)

    @Query("SELECT * FROM workout_template_items WHERE workoutTemplateId = :templateId ORDER BY orderIndex ASC")
    fun getItemsForTemplate(templateId: Long): Flow<List<WorkoutTemplateItem>>

    // set
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutTemplateSet(set: WorkoutTemplateSet)

    @Update
    suspend fun updateWorkoutTemplateSet(set: WorkoutTemplateSet)

    @Query("SELECT * FROM workout_template_sets WHERE workoutTemplateItemId = :itemId ORDER BY setNumber")
    fun getSetsForItem(itemId: Long): List<WorkoutTemplateSet>
}
