package com.example.workoutapp.database

import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.ExerciseMuscleGroup
import com.example.workoutapp.data.MuscleGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface ExerciseRepository {
    fun getActiveExercises(): Flow<List<Exercise>>
    fun getAllMuscleGroups(): Flow<List<MuscleGroup>>
    fun getExerciseById(exerciseId: Long): Flow<List<Exercise>>
    suspend fun saveExercise(exercise: Exercise)
    suspend fun deleteExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun saveMuscleGroup(muscleGroup: MuscleGroup)
    suspend fun linkExerciseToMuscleGroup(exerciseId: Long, muscleGroupId: Long)
    suspend fun unlinkExerciseFromMuscleGroup(exerciseId: Long, muscleGroupId: Long)
    fun getExercisesForMuscleGroup(muscleGroupId: Long): Flow<List<Exercise>>
}

class ExerciseRepositoryImpl(private val exerciseDao: ExerciseDao) : ExerciseRepository {
    override fun getActiveExercises(): Flow<List<Exercise>> = exerciseDao.getActiveExercises()

    override fun getAllMuscleGroups(): Flow<List<MuscleGroup>> = exerciseDao.getAllMuscleGroups()

    override fun getExerciseById(exerciseId: Long): Flow<List<Exercise>> = exerciseDao.getExerciseById(exerciseId)

    override suspend fun saveExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(exercise)
        }
    }
    override suspend fun updateExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.updateExercise(exercise)
        }
    }

    override suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise(exercise.id)
        }
    }

    override suspend fun saveMuscleGroup(muscleGroup: MuscleGroup) {
        withContext(Dispatchers.IO) {
            exerciseDao.insertMuscleGroup(muscleGroup)
        }
    }

    override suspend fun linkExerciseToMuscleGroup(exerciseId: Long, muscleGroupId: Long) {
        val comVal = ExerciseMuscleGroup(exerciseId, muscleGroupId)
        withContext(Dispatchers.IO) {
            exerciseDao.insertExerciseMuscleGroupCrossRef(comVal)
        }
    }

    override suspend fun unlinkExerciseFromMuscleGroup(exerciseId: Long, muscleGroupId: Long) {
        val comVal = ExerciseMuscleGroup(exerciseId, muscleGroupId)
        withContext(Dispatchers.IO) {
        exerciseDao.deleteExerciseMuscleGroupCrossRef(comVal)
            }
    }

    override fun getExercisesForMuscleGroup(muscleGroupId: Long): Flow<List<Exercise>> =
        exerciseDao.getExercisesForMuscleGroup(muscleGroupId)
}
