package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_products")
/**
 * Data class representing a food product with its nutritional values per 100g.
 *
 * @param name The name of the food product.
 * @param calories The caloric value of the product per 100g.
 * @param protein The protein content of the product per 100g.
 * @param fat The fat content of the product per 100g.
 * @param carbs The carbohydrate content of the product per 100g.
 * @param isActive Whether this product is currently active. False means soft-deleted —
 *                 product is hidden but FoodEntry records referencing it remain valid.
 * @param isFavorite Whether the user marked this product as favourite.
 * @param isCustom Whether this product was created manually by the user.
 * @param id The unique ID of the food product.
 */
data class FoodProduct(
    var name: String,
    var calories: Double,
    var protein: Double,
    var fat: Double,
    var carbs: Double,
    var description: String = "",   // ← NOWE
    var isActive: Boolean = true,
    var isFavorite: Boolean = false,
    var isCustom: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
