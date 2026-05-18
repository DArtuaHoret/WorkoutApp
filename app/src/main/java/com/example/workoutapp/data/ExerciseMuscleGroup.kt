package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "exercise_muscle_groups",
    primaryKeys = ["exerciseId", "muscleGroupId"],
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MuscleGroup::class,
            parentColumns = ["id"],
            childColumns = ["muscleGroupId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("exerciseId"),
        Index("muscleGroupId")
    ]
)
/**
 * Junction / cross-reference table linking [Exercise] to [MuscleGroup].
 * Represents a many-to-many relationship: one exercise can target multiple
 * muscle groups, and one muscle group can belong to multiple exercises.
 *
 * @param exerciseId FK referencing [Exercise].
 * @param muscleGroupId FK referencing [MuscleGroup].
 */
data class ExerciseMuscleGroup(
    val exerciseId: Long,
    val muscleGroupId: Long
)
