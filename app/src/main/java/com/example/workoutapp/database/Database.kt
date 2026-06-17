package com.example.workoutapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
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
    version = 3,
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

        // Migracja dodająca kolumnę plannedRestTime do workout_session_sets
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE workout_session_sets ADD COLUMN plannedRestTime INTEGER NOT NULL DEFAULT 60"
                )
            }
        }

        private val PREPOPULATE_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                db.execSQL("""
            INSERT INTO muscle_groups (id, name) VALUES
            (1,'Klatka'),(2,'Plecy'),(3,'Barki'),(4,'Biceps'),(5,'Triceps'),
            (6,'Brzuch'),(7,'Nogi'),(8,'Pośladki'),(9,'Łydki')
        """.trimIndent())

                db.execSQL("""
                    INSERT INTO exercises (id, name, nameEn, isCustom, isActive, photoUrl) VALUES
                    (1,'Wyciskanie sztangi na ławce poziomej','Barbell Bench Press',1,1,'exercise_0025'),
                    (2,'Wyciskanie hantli na ławce poziomej','Dumbbell Bench Press',1,1,'exercise_0289'),
                    (3,'Wyciskanie sztangi na ławce skośnej (głową do góry)','Incline Barbell Bench Press',1,1,'exercise_0047'),
                    (4,'Wyciskanie hantli na ławce skośnej (głową do góry)','Incline Dumbbell Bench Press',1,1,'exercise_0314'),
                    (5,'Wyciskanie sztangi na ławce skośnej (głową w dół)','Decline Barbell Bench Press',1,1,'exercise_0033'),
                    (6,'Wyciskanie hantli wąskim uchwytem','Close-Grip Dumbbell Press',1,1,'exercise_1731'),
                    (7,'Rozpiętki z hantlami na ławce poziomej','Dumbbell Flyes',1,1,'exercise_0308'),
                    (8,'Rozpiętki z hantlami na ławce skośnej (głową do góry)','Incline Dumbbell Flyes',1,1,'exercise_0319'),
                    (9,'Rozpiętki na maszynie butterfly','Butterfly Machine Flyes',1,1,'exercise_0185'),
                    (10,'Rozpiętki z wykorzystaniem wyciągów','Cable Crossover Flyes',1,1,'exercise_0179'),
                    (11,'Pompki na poręczach równoległych','Parallel Bar Dips',1,1,'exercise_0251'),
                    (12,'Pompki klasyczne','Push-Ups',1,1,'exercise_0662'),
                    (13,'Wyciskanie w suwnicy Smitha na ławce poziomej','Smith Machine Bench Press',1,1,'exercise_0748'),
                    (14,'Wyciskanie w suwnicy Smitha na ławce skośnej (głową do góry)','Smith Machine Incline Bench Press',1,1,'exercise_0757'),
                    (15,'Wznosy ramion z wykorzystaniem dolnego wyciągu','Low Cable Front Raise',1,1,'exercise_0162'),
                    (16,'Podciąganie na drążku nachwytem (szeroko)','Wide-Grip Pull-Up',1,1,'exercise_1429'),
                    (18,'Wiosłowanie sztangą w opadzie tułowia','Barbell Bent-Over Row',1,1,'exercise_0027'),
                    (19,'Wiosłowanie hantlem w opadzie (jednorącz)','One-Arm Dumbbell Row',1,1,'exercise_0292'),
                    (20,'Wiosłowanie hantlami na ławce','Chest-Supported Dumbbell Row',1,1,'exercise_0327'),
                    (21,'Wiosłowanie na wyciągu dolnym','Seated Cable Row',1,1,'exercise_0861'),
                    (22,'Ściąganie drążka wyciągu górnego do klatki (szeroki nachwyt)','Wide-Grip Lat Pulldown',1,1,'exercise_0150'),
                    (23,'Ściąganie drążka wyciągu górnego za głowę','Behind-the-Neck Lat Pulldown',1,1,'exercise_1325'),
                    (24,'Ściąganie linki wyciągu dolnego jednorącz','Single-Arm Cable Pulldown',1,1,'exercise_3563'),
                    (25,'Martwy ciąg klasyczny (sztanga)','Conventional Deadlift',1,1,'exercise_0032'),
                    (26,'Szrugsy (wzruszanie ramion)','Barbell Shrugs',1,1,'exercise_0095'),
                    (27,'Prostowanie tułowia na ławce rzymskiej','Back Extension',1,1,'exercise_0489'),
                    (28,'Unoszenie tułowia w leżeniu na brzuchu (superman)','Superman',1,1,'exercise_1352'),
                    (29,'Przysiad ze sztangą na barkach (klasyczny)','Barbell Back Squat',1,1,'exercise_0043'),
                    (30,'Przysiad przedni (sztanga na przednich barkach)','Front Squat',1,1,'exercise_0042'),
                    (31,'Przysiad bułgarski (z tylną nogą na ławce)','Bulgarian Split Squat',1,1,'exercise_2368'),
                    (32,'Martwy ciąg na prostych nogach','Stiff-Leg Deadlift',1,1,'exercise_0432'),
                    (33,'Martwy ciąg rumuński','Romanian Deadlift',1,1,'exercise_0085'),
                    (34,'Wykroki ze sztangą lub hantlami','Barbell / Dumbbell Lunges',1,1,'exercise_0054'),
                    (35,'Wypychanie ciężaru na suwnicy','Leg Press',1,1,'exercise_0739'),
                    (36,'Prostowanie nóg na maszynie (siedząc)','Leg Extension',1,1,'exercise_0585'),
                    (37,'Uginanie nóg na maszynie','Leg Curl',1,1,'exercise_0586'),
                    (38,'Wspięcia na palce stojąc (łydki)','Standing Calf Raise',1,1,'exercise_1373'),
                    (39,'Wspięcia na palce siedząc (łydki)','Seated Calf Raise',1,1,'exercise_1371'),
                    (40,'Przysiad sumo (szeroki rozstaw stóp)','Sumo Squat',1,1,'exercise_3142'),
                    (41,'Mostek na jednej nodze','Single-Leg Glute Bridge',1,1,'exercise_3645'),
                    (42,'Odwodzenie nogi w wyciągu','Cable Hip Abduction',1,1,'exercise_0710'),
                    (43,'Przysiad na jednej nodze (pistolet)','Pistol Squat',1,1,'exercise_0544'),
                    (44,'Wyciskanie sztangi sprzed klatki','Barbell Overhead Press',1,1,'exercise_1457'),
                    (45,'Wyciskanie hantli siedząc','Seated Dumbbell Shoulder Press',1,1,'exercise_0405'),
                    (47,'Unoszenie hantli bokiem (bark boczny)','Dumbbell Lateral Raise',1,1,'exercise_0334'),
                    (48,'Unoszenie hantli w opadzie tułowia (bark tylny)','Bent-Over Rear Delt Raise',1,1,'exercise_2292'),
                    (49,'Unoszenie sztangi przed siebie (bark przedni)','Barbell Front Raise',1,1,'exercise_0041'),
                    (50,'Arnoldki (wyciskanie hantli z rotacją)','Arnold Press',1,1,'exercise_2137'),
                    (51,'Face pull (wyciąg górny - linka do twarzy)','Face Pull',1,1,'exercise_0233'),
                    (52,'Unoszenie ramion z linkami bokiem (wyciąg)','Cable Lateral Raise',1,1,'exercise_0178'),
                    (53,'Wiosłowanie sztangą pod brodę (wąski nachwyt)','Upright Row',1,1,'exercise_0120'),
                    (54,'Unoszenie ramienia w bok na ławce','Bench Dumbbell Lateral Raise',1,1,'exercise_0326'),
                    (56,'Uginanie ramion ze sztangą prostą (stojąc)','Barbell Curl',1,1,'exercise_0031'),
                    (57,'Uginanie ramion ze sztangą łamaną','EZ-Bar Curl',1,1,'exercise_0447'),
                    (58,'Uginanie ramion z hantlami stojąc (naprzemiennie)','Alternating Dumbbell Curl',1,1,'exercise_0285'),
                    (59,'Uginanie ramion z hantlami stojąc (jednocześnie)','Simultaneous Dumbbell Curl',1,1,'exercise_0294'),
                    (60,'Uginanie ramion z hantlami na ławce skośnej','Incline Dumbbell Curl',1,1,'exercise_0318'),
                    (61,'Uginanie ramion z hantlem w opadzie (jednorącz, z podparciem)','Concentration Curl',1,1,'exercise_0297'),
                    (62,'Uginanie ramion z linkami wyciągu dolnego','Low Cable Curl',1,1,'exercise_0868'),
                    (63,'Uginanie ramion z hantlem młotkowe','Hammer Curl',1,1,'exercise_0298'),
                    (64,'Uginanie ramion na modlitewniku z hantlami','Preacher Dumbbell Curl',1,1,'exercise_0372'),
                    (65,'Uginanie ramion na modlitewniku ze sztangą','Preacher Barbell Curl',1,1,'exercise_0070'),
                    (66,'Wyciskanie francuskie sztangi leżąc','Lying Barbell Tricep Extension',1,1,'exercise_0061'),
                    (67,'Wyciskanie francuskie hantli leżąc','Lying Dumbbell Tricep Extension',1,1,'exercise_0351'),
                    (68,'Prostowanie ramienia z linką wyciągu górnego','Cable Tricep Pushdown',1,1,'exercise_0241'),
                    (69,'Prostowanie ramienia z linką górną - jednorącz','Single-Arm Cable Pushdown',1,1,'exercise_1723'),
                    (70,'Pompki na poręczach równoległych (triceps)','Tricep Dips',1,1,'exercise_0814'),
                    (71,'Pompki wąskie (dłonie przy sobie)','Diamond Push-Ups',1,1,'exercise_0259'),
                    (72,'Wyciskanie hantli za głową w siadzie','Overhead Dumbbell Tricep Extension',1,1,'exercise_2188'),
                    (73,'Prostowanie ramienia z hantlem w opadzie tułowia','Dumbbell Kickback',1,1,'exercise_0354'),
                    (74,'Prostowanie ramienia z hantlem w opadzie','Bent-Over Dumbbell Kickback',1,1,'exercise_0333'),
                    (75,'Prostowanie ramion na wyciągu dolnym','Low Cable Tricep Extension',1,1,'exercise_0173'),
                    (76,'Brzuszki','Crunch',1,1,'exercise_0274'),
                    (77,'Unoszenie nóg w leżeniu','Lying Leg Raise',1,1,'exercise_0620'),
                    (78,'Unoszenie nóg w zwisie na drążku','Hanging Leg Raise',1,1,'exercise_0472'),
                    (80,'Deska boczna','Side Plank',1,1,'exercise_3544'),
                    (81,'Rowerek','Bicycle Crunch',1,1,'exercise_0972'),
                    (82,'Nożyce','Scissors',1,1,'exercise_0459'),
                    (83,'Brzuszki odwrócone','Reverse Crunch',1,1,'exercise_0872'),
                    (84,'V-up (jednoczesne unoszenie tułowia i nóg)','V-Up',1,1,'exercise_1014'),
                    (85,'Deska dynamiczna','Dynamic Plank',1,1,'exercise_3665'),
                    (86,'Mountain climbers','Mountain Climbers',1,1,'exercise_0630'),
                    (88,'Brzuszki na piłce lekarskiej','Medicine Ball Crunch',1,1,'exercise_2297'),
                    (89,'Allachy','Windshield Wipers',1,1,'exercise_0175')
                """.trimIndent())

                db.execSQL("""
            INSERT INTO exercise_muscle_groups (exerciseId, muscleGroupId) VALUES
            (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),
            (16,2),(18,2),(19,2),(20,2),(21,2),(22,2),(23,2),(24,2),(25,2),(26,2),(27,2),(28,2),
            (29,7),(30,7),(31,7),(31,8),(32,7),(33,7),(33,8),(34,7),(35,7),(36,7),(37,7),
            (38,9),(39,9),
            (40,7),(40,8),(41,8),(42,8),(43,7),
            (44,3),(45,3),(47,3),(48,3),(49,3),(50,3),(51,3),(52,3),(53,3),(54,3),
            (56,4),(57,4),(58,4),(59,4),(60,4),(61,4),(62,4),(63,4),(64,4),(65,4),
            (66,5),(67,5),(68,5),(69,5),(70,5),(71,5),(72,5),(73,5),(74,5),(75,5),
            (76,6),(77,6),(78,6),(80,6),(81,6),(82,6),(83,6),(84,6),(85,6),(86,6),(88,6),(89,6)
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
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .addCallback(PREPOPULATE_CALLBACK)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}