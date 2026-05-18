package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "food_entries",
    foreignKeys = [
        ForeignKey(
            entity = FoodProduct::class,
            parentColumns = ["id"],
            childColumns = ["foodProductId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("foodProductId")]
)
/**
 * Data class representing a single food entry logged by the user.
 *
 * @param grams The amount of the product consumed in grams.
 * @param eatenAt The date when the product was consumed.
 * @param mealType The type of meal (e.g. "BREAKFAST", "LUNCH", "DINNER", "SNACK").
 * @param foodProductId FK referencing the consumed [FoodProduct].
 * @param id The unique ID of the food entry.
 */
data class FoodEntry(
    var grams: Double,
    var eatenAt: Date = Date(),
    var mealType: String? = null,
    var foodProductId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
