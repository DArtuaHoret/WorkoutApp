package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_session_items",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = ["id"],
            childColumns = ["workoutSessionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("workoutSessionId"),
        Index("exerciseId")
    ]
)
/**
 * Data class representing a single exercise entry within a [WorkoutSession].
 *
 * @param orderIndex The display order of this item in the session.
 * @param plannedSets The number of sets planned for this exercise.
 * @param note Optional note for this session item.
 * @param workoutSessionId FK referencing the parent [WorkoutSession].
 * @param exerciseId FK referencing the [Exercise] being performed.
 * @param id The unique ID of the session item.
 */
data class WorkoutSessionItem(
    var orderIndex: Int,
    var plannedSets: Int,
    var note: String? = null,
    var workoutSessionId: Long,
    var exerciseId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
