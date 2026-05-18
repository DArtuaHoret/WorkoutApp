package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_products")
/**
 * Data class representing a food product with its nutritional values per 100g.
 *
 * @param name The name of the food product.
 * @param calories The caloric value of the product.
 * @param protein The protein content of the product.
 * @param fat The fat content of the product.
 * @param carbs The carbohydrate content of the product.
 * @param isActive Whether this product is currently active/visible.
 * @param id The unique ID of the food product.
 */
data class FoodProduct(
    var name: String,
    var calories: Double,
    var protein: Double,
    var fat: Double,
    var carbs: Double,
    var isActive: Boolean = true,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
