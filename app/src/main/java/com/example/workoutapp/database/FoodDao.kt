package com.example.workoutapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.workoutapp.data.FoodEntry
import com.example.workoutapp.data.FoodProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    // produkt

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: FoodProduct)

    @Update
    suspend fun updateProduct(product: FoodProduct)

    @Query("SELECT * FROM food_products WHERE isActive = 1")
    fun getActiveProducts(): Flow<List<FoodProduct>>

    @Query("SELECT * FROM food_products WHERE id = :productId")
    fun getProductById(productId: Long): Flow<List<FoodProduct>>

    @Query("SELECT * FROM food_products WHERE name LIKE '%' || :request || '%'")
    fun searchProducts(request: String): Flow<List<FoodProduct>>


    // komponenty

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: FoodEntry)

    @Update
    suspend fun updateEntry(entry: FoodEntry)

    @Delete
    suspend fun deleteEntry(entry: FoodEntry)


    @Query("SELECT * FROM food_entries WHERE eatenAt >= :startOfDay AND eatenAt <= :endOfDay ORDER BY eatenAt DESC")
    fun getEntriesForDate(startOfDay: Long, endOfDay: Long): Flow<List<FoodEntry>>


}
