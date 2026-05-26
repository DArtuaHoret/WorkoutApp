package com.example.workoutapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.workoutapp.data.*
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}

@Database(
    entities = [
        // Wszystkie encje muszą być tu wymienione
        Exercise::class,
        MuscleGroup::class,
        ExerciseMuscleGroup::class,
        WorkoutTemplate::class,
        WorkoutTemplateItem::class,
        WorkoutTemplateSet::class,
        WorkoutSession::class,
        WorkoutSessionItem::class,
        WorkoutSessionSet::class,
        FoodProduct::class,
        FoodEntry::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun workoutTemplateDao(): WorkoutTemplateDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun foodDao(): FoodDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workout_nutrition_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}