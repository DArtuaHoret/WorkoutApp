package com.example.workoutapp.database

import com.example.workoutapp.data.FoodEntry
import com.example.workoutapp.data.FoodProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

interface FoodRepository {

    suspend fun insertProduct(product: FoodProduct): Long
    suspend fun findActiveByName(name: String): FoodProduct?
    suspend fun findExactMatch(name: String, calories: Double, protein: Double, fat: Double, carbs: Double): FoodProduct?
    suspend fun deactivateProduct(productId: Long)
    suspend fun updateProduct(product: FoodProduct)
    suspend fun setFavorite(productId: Long, isFavorite: Boolean)
    fun getFavoriteAndCustomProducts(): Flow<List<FoodProduct>>

    fun getAllActiveProducts(): Flow<List<FoodProduct>>
    suspend fun deleteEntryById(entryId: Long)
    suspend fun updateEntryGrams(entryId: Long, newGrams: Double)

    suspend fun insertEntry(entry: FoodEntry)
    suspend fun updateEntry(entry: FoodEntry)
    suspend fun deleteEntry(entry: FoodEntry)
    fun getEntriesForDate(date: Date): Flow<List<FoodEntry>>
}


class FoodRepositoryImpl(private val foodDao: FoodDao) : FoodRepository {



    override suspend fun insertProduct(product: FoodProduct): Long =
        withContext(Dispatchers.IO) {
            foodDao.insertProduct(product)
        }

    override suspend fun findActiveByName(name: String): FoodProduct? =
        withContext(Dispatchers.IO) {
            foodDao.findActiveByName(name)
        }

    override suspend fun updateProduct(product: FoodProduct) =
        withContext(Dispatchers.IO) { foodDao.updateProduct(product) }

    override suspend fun findExactMatch(
        name: String,
        calories: Double,
        protein: Double,
        fat: Double,
        carbs: Double,
    ): FoodProduct? =
        withContext(Dispatchers.IO) {
            foodDao.findExactMatch(name, calories, protein, fat, carbs)
        }

    override suspend fun deactivateProduct(productId: Long) =
        withContext(Dispatchers.IO) {
            foodDao.deactivateProduct(productId)
        }

    override suspend fun setFavorite(productId: Long, isFavorite: Boolean) =
        withContext(Dispatchers.IO) {
            foodDao.setFavorite(productId, isFavorite)
        }

    override fun getFavoriteAndCustomProducts(): Flow<List<FoodProduct>> =
        foodDao.getFavoriteAndCustomProducts()

    override fun getAllActiveProducts(): Flow<List<FoodProduct>> =
        foodDao.getAllActiveProducts()

    override suspend fun deleteEntryById(entryId: Long) =
        withContext(Dispatchers.IO) {
            val entry = foodDao.getEntryById(entryId) ?: return@withContext
            foodDao.deleteEntry(entry)
        }

    override suspend fun updateEntryGrams(entryId: Long, newGrams: Double) =
        withContext(Dispatchers.IO) {
            val entry = foodDao.getEntryById(entryId) ?: return@withContext
            foodDao.updateEntry(entry.copy(grams = newGrams))
        }




    override suspend fun insertEntry(entry: FoodEntry) =
        withContext(Dispatchers.IO) {
            foodDao.insertEntry(entry)
        }

    override suspend fun updateEntry(entry: FoodEntry) =
        withContext(Dispatchers.IO) {
            foodDao.updateEntry(entry)
        }

    override suspend fun deleteEntry(entry: FoodEntry) =
        withContext(Dispatchers.IO) {
            foodDao.deleteEntry(entry)
        }

    override fun getEntriesForDate(date: Date): Flow<List<FoodEntry>> {
        val calendar = Calendar.getInstance().apply { time = date }

        val startOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val endOfDay = calendar.apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis

        return foodDao.getEntriesForDate(startOfDay, endOfDay)
    }
}