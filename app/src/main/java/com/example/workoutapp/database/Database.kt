package com.example.workoutapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
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

        private val PREPOPULATE_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL("""
            INSERT INTO muscle_groups (id, name) VALUES
            (1, 'Klatka'), (2, 'Plecy'), (3, 'Barki'), (4, 'Biceps'), (5, 'Triceps'),
            (6, 'Brzuch'), (7, 'Nogi'), (8, 'Pośladki'), (9, 'Łydki')
        """.trimIndent())

                // Seed ćwiczeń do testowania ExerciseSearchScreen
                db.execSQL("""
            INSERT INTO exercises (id, name, isCustom, isActive) VALUES
            (1, 'Wyciskanie sztangi', 1, 1),
            (2, 'Rozpiętki', 1, 1),
            (3, 'Wyciskanie hantli', 1, 1),
            (4, 'Wiosłowanie sztangą', 1, 1),
            (5, 'Podciąganie', 1, 1),
            (6, 'Wiosłowanie hantlem', 1, 1),
            (7, 'Przysiad ze sztangą', 1, 1),
            (8, 'Martwy ciąg', 1, 1),
            (9, 'Wypychanie nóg', 1, 1),
            (10, 'Uginanie ramion', 1, 1),
            (11, 'Młotki', 1, 1),
            (12, 'Wyciskanie francuskie', 1, 1),
            (13, 'Pompki na poręczach', 1, 1),
            (14, 'Arnoldki', 1, 1),
            (15, 'Wznosy bokiem', 1, 1)
        """.trimIndent())

                // Powiązania ćwiczeń z grupami mięśniowymi
                // muscle_groups id: 1=Klatka, 2=Plecy, 3=Barki, 4=Biceps, 5=Triceps,
                //                    6=Brzuch, 7=Nogi, 8=Pośladki, 9=Łydki
                db.execSQL("""
            INSERT INTO exercise_muscle_groups (exerciseId, muscleGroupId) VALUES
            (1, 1), (2, 1), (3, 1),
            (4, 2), (5, 2), (6, 2),
            (7, 7), (8, 7), (9, 7),
            (10, 4), (11, 4),
            (12, 5), (13, 5),
            (14, 3), (15, 3)
        """.trimIndent())
            }
        }

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workout_nutrition_db"
                )
                    //.fallbackToDestructiveMigration()
                    .addCallback(PREPOPULATE_CALLBACK)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}