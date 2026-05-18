package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
/**
 * Data class representing an exercise.
 *
 * @param name The name of the exercise.
 * @param isCustom Whether this exercise was created by the user (true) or is built-in (false).
 * @param isActive Whether this exercise is currently active/visible.
 * @param description Optional description of the exercise.
 * @param photoUrl Optional URL to a photo illustrating the exercise.
 * @param id The unique ID of the exercise.
 */
data class Exercise(
    var name: String,
    var isCustom: Boolean = false,
    var isActive: Boolean = true,
    var description: String? = null,
    var photoUrl: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
