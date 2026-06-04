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
//DD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(product: FoodProduct): Long

    // Używane gdy API zwróciło zaktualizowane dane — przenosimy isFavorite na nowy rekord
    @Query("SELECT * FROM food_products WHERE name = :name AND isActive = 1 LIMIT 1")
    suspend fun findActiveByName(name: String): FoodProduct?

    @Query("""
        SELECT * FROM food_products
        WHERE name = :name
        AND calories = :calories
        AND protein = :protein
        AND fat = :fat
        AND carbs = :carbs
        AND isActive = 1
        LIMIT 1
    """)
    suspend fun findExactMatch(
        name: String,
        calories: Double,
        protein: Double,
        fat: Double,
        carbs: Double,
    ): FoodProduct?

    // Soft delete — historia w kalendarzu zostaje nienaruszona
    @Query("UPDATE food_products SET isActive = 0 WHERE id = :productId")
    suspend fun deactivateProduct(productId: Long)

    @Query("UPDATE food_products SET isFavorite = :isFavorite WHERE id = :productId")
    suspend fun setFavorite(productId: Long, isFavorite: Boolean)

    // Ulubione i własne — dla FavoriteProductsScreen
    @Query("SELECT * FROM food_products WHERE (isFavorite = 1 OR isCustom = 1) AND isActive = 1")
    fun getFavoriteAndCustomProducts(): Flow<List<FoodProduct>>




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
