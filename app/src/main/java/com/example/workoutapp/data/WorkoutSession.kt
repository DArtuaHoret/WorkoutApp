package com.example.workoutapp.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "workout_sessions",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutTemplate::class,
            parentColumns = ["id"],
            childColumns = ["workoutTemplateId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("workoutTemplateId")]
)
/**
 * Data class representing an actual workout session performed by the user.
 *
 * @param status The current status of the session (e.g. "PLANNED", "IN_PROGRESS", "DONE").
 * @param scheduledAt The date the session was scheduled for.
 * @param startedAt The date/time when the session was started.
 * @param finishedAt The date/time when the session was completed.
 * @param note Optional note for the session.
 * @param workoutTemplateId Optional FK referencing the [WorkoutTemplate] this session is based on.
 * @param id The unique ID of the session.
 */
data class WorkoutSession(
    var status: String,
    var scheduledAt: Date? = null,
    var startedAt: Date? = null,
    var finishedAt: Date? = null,
    var note: String? = null,
    var workoutTemplateId: Long? = null,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)
