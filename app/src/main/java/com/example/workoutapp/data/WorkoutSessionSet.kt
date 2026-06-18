package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_session_sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionItem::class,
            parentColumns = ["id"],
            childColumns = ["workoutSessionItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutSessionItemId")]
)
/**
 * Data class representing a single performed set within a [WorkoutSessionItem].
 *
 * @param setNumber The order number of this set (e.g. 1st, 2nd, 3rd set).
 * @param plannedReps The number of repetitions planned for this set.
 * @param actualReps The number of repetitions actually performed.
 * @param plannedWeight The weight planned for this set.
 * @param actualWeight The weight actually used for this set.
 * @param isDone Whether this set has been completed.
 * @param note Optional note for this set.
 * @param workoutSessionItemId FK referencing the parent [WorkoutSessionItem].
 * @param id The unique ID of the session set.
 */
data class WorkoutSessionSet(
    var setNumber: Int,
    var plannedReps: Int,
    var actualReps: Int = 0,
    var plannedWeight: Double,
    var actualWeight: Double = 0.0,
    var plannedRestTime: Int = 60,
    var isDone: Boolean = false,
    var note: String? = null,
    var workoutSessionItemId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

