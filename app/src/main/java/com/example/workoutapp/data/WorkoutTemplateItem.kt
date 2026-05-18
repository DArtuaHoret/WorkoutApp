package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_template_items",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutTemplate::class,
            parentColumns = ["id"],
            childColumns = ["workoutTemplateId"],
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
        Index("workoutTemplateId"),
        Index("exerciseId")
    ]
)
/**
 * Data class representing a single exercise entry within a workout template.
 *
 * @param orderIndex The display order of this item in the template.
 * @param note Optional note for this template item.
 * @param workoutTemplateId FK referencing the parent [WorkoutTemplate].
 * @param exerciseId FK referencing the [Exercise] assigned to this item.
 * @param id The unique ID of the template item.
 */
data class WorkoutTemplateItem(
    var orderIndex: Int,
    var note: String? = null,
    var workoutTemplateId: Long,
    var exerciseId: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
