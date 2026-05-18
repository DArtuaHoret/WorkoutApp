package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "muscle_groups")
/**
 * Data class representing a muscle group (e.g. Chest, Back, Legs).
 *
 * @param name The name of the muscle group.
 * @param id The unique ID of the muscle group.
 */
data class MuscleGroup(
    var name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
