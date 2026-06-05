package com.example.workoutapp.database

import com.example.workoutapp.data.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface WorkoutTemplateRepository {
    fun getActiveTemplates(): Flow<List<WorkoutTemplate>>
    fun getItemsForTemplate(templateId: Long): Flow<List<WorkoutTemplateItem>>
    fun getSetsForItem(itemId: Long): Flow<List<WorkoutTemplateSet>>
    fun getExerciseEntriesForTemplate(templateId: Long): Flow<List<TemplateExerciseEntry>>
    suspend fun saveTemplate(template: WorkoutTemplate): Long
    suspend fun updateTemplate(template: WorkoutTemplate)
    suspend fun deleteTemplate(template: WorkoutTemplate)
    suspend fun saveTemplateItem(item: WorkoutTemplateItem): Long
    suspend fun deleteTemplateItem(itemId: Long)
    suspend fun updateTemplateItem(item: WorkoutTemplateItem)
    suspend fun saveTemplateSet(set: WorkoutTemplateSet)
    suspend fun updateTemplateSet(set: WorkoutTemplateSet)
    suspend fun deleteSetsForItem(itemId: Long)
}

class WorkoutTemplateRepositoryImpl(
    private val templateDao: WorkoutTemplateDao
) : WorkoutTemplateRepository {

    override fun getActiveTemplates(): Flow<List<WorkoutTemplate>> =
        templateDao.getActiveTemplates()

    override fun getItemsForTemplate(templateId: Long): Flow<List<WorkoutTemplateItem>> =
        templateDao.getItemsForTemplate(templateId)

    override fun getSetsForItem(itemId: Long): Flow<List<WorkoutTemplateSet>> =
        templateDao.getSetsForItem(itemId)

    override fun getExerciseEntriesForTemplate(templateId: Long): Flow<List<TemplateExerciseEntry>> =
        templateDao.getExerciseEntriesForTemplate(templateId)

    override suspend fun saveTemplate(template: WorkoutTemplate): Long =
        withContext(Dispatchers.IO) {
            templateDao.insertWorkoutTemplate(template)
        }

    override suspend fun updateTemplate(template: WorkoutTemplate) =
        withContext(Dispatchers.IO) {
            templateDao.updateWorkoutTemplate(template)
        }

    override suspend fun deleteTemplate(template: WorkoutTemplate) =
        withContext(Dispatchers.IO) {
            templateDao.deleteWorkoutTemplate(template.id)
        }

    override suspend fun saveTemplateItem(item: WorkoutTemplateItem): Long =
        withContext(Dispatchers.IO) {
            templateDao.insertWorkoutTemplateItem(item)
        }

    override suspend fun deleteTemplateItem(itemId: Long) =
        withContext(Dispatchers.IO) {
            templateDao.deleteWorkoutTemplateItem(itemId)
        }

    override suspend fun updateTemplateItem(item: WorkoutTemplateItem) =
        withContext(Dispatchers.IO) {
            templateDao.updateWorkoutTemplateItem(item)
        }

    override suspend fun saveTemplateSet(set: WorkoutTemplateSet) =
        withContext(Dispatchers.IO) {
            templateDao.insertWorkoutTemplateSet(set)
        }

    override suspend fun updateTemplateSet(set: WorkoutTemplateSet) =
        withContext(Dispatchers.IO) {
            templateDao.updateWorkoutTemplateSet(set)
        }

    override suspend fun deleteSetsForItem(itemId: Long) =
        withContext(Dispatchers.IO) {
            templateDao.deleteSetsForItem(itemId)
        }
}