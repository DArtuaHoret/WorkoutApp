package com.example.workoutapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.ExerciseMuscleGroup
import com.example.workoutapp.data.MuscleGroup
import kotlinx.coroutines.flow.Flow

data class ExerciseWithMuscleGroup(
    val exerciseId: Long,
    val exerciseName: String,
    val muscleGroupName: String,
    val isCustom: Boolean,
    val photoUrl: String?,
)

@Dao
interface ExerciseDao {

    // cwiki
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    @Query("UPDATE exercises SET isActive = 0 WHERE id = :exerciseId")
    suspend fun deleteExercise(exerciseId: Long)

    @Query("SELECT * FROM exercises WHERE isActive = 1")
    fun getActiveExercises(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: Long): Flow<List<Exercise>>

    // grupy mies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMuscleGroup(muscleGroup: MuscleGroup)

    @Query("SELECT * FROM muscle_groups")
    fun getAllMuscleGroups(): Flow<List<MuscleGroup>>

    // cwiki + grupy mies
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExerciseMuscleGroupCrossRef(crossRef: ExerciseMuscleGroup)

    @Delete
    suspend fun deleteExerciseMuscleGroupCrossRef(crossRef: ExerciseMuscleGroup)

    @Query("""
    	SELECT e.* FROM exercises e
    	INNER JOIN exercise_muscle_groups emg ON e.id = emg.exerciseId
    	WHERE emg.muscleGroupId = :muscleGroupId
	""")
    fun getExercisesForMuscleGroup(muscleGroupId: Long): Flow<List<Exercise>>

    @Query("""
        SELECT 
            e.id   AS exerciseId,
            e.name AS exerciseName,
            COALESCE(mg.name, 'Inne') AS muscleGroupName,
            e.isCustom AS isCustom,
            e.photoUrl AS photoUrl
        FROM exercises e
        LEFT JOIN exercise_muscle_groups emg ON emg.exerciseId = e.id
        LEFT JOIN muscle_groups mg ON mg.id = emg.muscleGroupId
        WHERE e.isActive = 1
        GROUP BY e.id
        ORDER BY mg.name ASC, e.name ASC
    """)
    fun getActiveExercisesWithMuscleGroup(): Flow<List<ExerciseWithMuscleGroup>>

    @Query("DELETE FROM exercise_muscle_groups WHERE exerciseId = :exerciseId")
    suspend fun deleteAllMuscleGroupsForExercise(exerciseId: Long)

    @Query("""
        SELECT mg.* FROM muscle_groups mg
        INNER JOIN exercise_muscle_groups emg ON mg.id = emg.muscleGroupId
        WHERE emg.exerciseId = :exerciseId
    """)
    fun getMuscleGroupsForExercise(exerciseId: Long): Flow<List<MuscleGroup>>
}
