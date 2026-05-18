package com.example.workoutapp.database

import com.example.workoutapp.data.FoodEntry
import com.example.workoutapp.data.FoodProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

interface FoodRepository {
    fun getActiveProducts(): Flow<List<FoodProduct>>
    fun searchProducts(query: String): Flow<List<FoodProduct>>
    fun getProductById(id: Long): Flow<List<FoodProduct>>
    suspend fun saveProduct(product: FoodProduct)
    suspend fun updateProduct(product: FoodProduct)
    fun getEntriesForDate(date: Date): Flow<List<FoodEntry>>
    suspend fun saveEntry(entry: FoodEntry)
    suspend fun updateEntry(entry: FoodEntry)
    suspend fun deleteEntry(entry: FoodEntry)

}



class FoodRepositoryImpl(private val foodDao: FoodDao) : FoodRepository {

    override fun getActiveProducts(): Flow<List<FoodProduct>> =
        foodDao.getActiveProducts()

    override fun searchProducts(query: String): Flow<List<FoodProduct>> =
        foodDao.searchProducts(query)

    override fun getProductById(id: Long): Flow<List<FoodProduct>> =
        foodDao.getProductById(id)

    override suspend fun saveProduct(product: FoodProduct) {
        withContext(Dispatchers.IO) {
            foodDao.insertProduct(product)
        }
    }

    override suspend fun updateProduct(product: FoodProduct) {
        withContext(Dispatchers.IO) {
            foodDao.updateProduct(product)
        }
    }

    // zakres czasowy dnia
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

    override suspend fun saveEntry(entry: FoodEntry) {
        withContext(Dispatchers.IO) {
            foodDao.insertEntry(entry)
        }
    }

    override suspend fun updateEntry(entry: FoodEntry) {
        withContext(Dispatchers.IO) {
            foodDao.updateEntry(entry)
        }
    }

    override suspend fun deleteEntry(entry: FoodEntry) {
        withContext(Dispatchers.IO) {
            foodDao.deleteEntry(entry)
        }
    }
}
