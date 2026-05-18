package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "workout_templates")
/**
 * Data class representing a workout template.
 *
 * @param name The name of the template.
 * @param createdAt The date the template was created.
 * @param isActive Whether the template is active.
 * @param description Optional description of the template.
 * @param id The unique ID of the template.
 */
data class WorkoutTemplate(
    var name: String,
    var createdAt: Date = Date(),
    var isActive: Boolean = true,
    var description: String? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
