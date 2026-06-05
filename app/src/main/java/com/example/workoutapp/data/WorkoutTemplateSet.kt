package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_template_sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutTemplateItem::class,
            parentColumns = ["id"],
            childColumns = ["workoutTemplateItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutTemplateItemId")]
)
/**
 * Data class representing a planned set within a [WorkoutTemplateItem].
 *
 * @param setNumber The order number of this set (e.g. 1st, 2nd, 3rd set).
 * @param reps The planned number of repetitions for this set.
 * @param weight The planned weight for this set.
 * @param note Optional note for this set.
 * @param workoutTemplateItemId FK referencing the parent [WorkoutTemplateItem].
 * @param id The unique ID of the template set.
 */
data class WorkoutTemplateSet(
    var setNumber: Int,
    var reps: Int,
    var weight: Double,
    var restTime: Int = 60,
    var note: String? = null,
    var workoutTemplateItemId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
