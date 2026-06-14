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

                // Grupy mięśniowe
                db.execSQL("""
            INSERT INTO muscle_groups (id, name) VALUES
            (1,'Klatka'),(2,'Plecy'),(3,'Barki'),(4,'Biceps'),(5,'Triceps'),
            (6,'Brzuch'),(7,'Nogi'),(8,'Pośladki'),(9,'Łydki')
        """.trimIndent())

                // Ćwiczenia
                db.execSQL("""
            INSERT INTO exercises (id, name, isCustom, isActive, photoUrl) VALUES
            (1,'Wyciskanie sztangi na ławce poziomej',1,1,'exercise_0025'),
            (2,'Wyciskanie hantli na ławce poziomej',1,1,'exercise_0289'),
            (3,'Wyciskanie sztangi na ławce skośnej (głową do góry)',1,1,'exercise_0047'),
            (4,'Wyciskanie hantli na ławce skośnej (głową do góry)',1,1,'exercise_0314'),
            (5,'Wyciskanie sztangi na ławce skośnej (głową w dół)',1,1,'exercise_0033'),
            (6,'Wyciskanie hantli wąskim uchwytem',1,1,'exercise_1731'),
            (7,'Rozpiętki z hantlami na ławce poziomej',1,1,'exercise_0308'),
            (8,'Rozpiętki z hantlami na ławce skośnej (głową do góry)',1,1,'exercise_0319'),
            (9,'Rozpiętki na maszynie butterfly',1,1,'exercise_0185'),
            (10,'Rozpiętki z wykorzystaniem wyciągów',1,1,'exercise_0179'),
            (11,'Pompki na poręczach równoległych',1,1,'exercise_0251'),
            (12,'Pompki klasyczne',1,1,'exercise_0662'),
            (13,'Wyciskanie w suwnicy Smitha na ławce poziomej',1,1,'exercise_0748'),
            (14,'Wyciskanie w suwnicy Smitha na ławce skośnej (głową do góry)',1,1,'exercise_0757'),
            (15,'Wznosy ramion z wykorzystaniem dolnego wyciągu',1,1,'exercise_0162'),
            (16,'Podciąganie na drążku nachwytem (szeroko)',1,1,'exercise_1429'),
            (18,'Wiosłowanie sztangą w opadzie tułowia',1,1,'exercise_0027'),
            (19,'Wiosłowanie hantlem w opadzie (jednorącz)',1,1,'exercise_0292'),
            (20,'Wiosłowanie hantlami na ławce',1,1,'exercise_0327'),
            (21,'Wiosłowanie na wyciągu dolnym',1,1,'exercise_0861'),
            (22,'Ściąganie drążka wyciągu górnego do klatki (szeroki nachwyt)',1,1,'exercise_0150'),
            (23,'Ściąganie drążka wyciągu górnego za głowę',1,1,'exercise_1325'),
            (24,'Ściąganie linki wyciągu dolnego jednorącz',1,1,'exercise_3563'),
            (25,'Martwy ciąg klasyczny (sztanga)',1,1,'exercise_0032'),
            (26,'Szrugsy (wzruszanie ramion)',1,1,'exercise_0095'),
            (27,'Prostowanie tułowia na ławce rzymskiej',1,1,'exercise_0489'),
            (28,'Unoszenie tułowia w leżeniu na brzuchu (superman)',1,1,'exercise_1352'),
            (29,'Przysiad ze sztangą na barkach (klasyczny)',1,1,'exercise_0043'),
            (30,'Przysiad przedni (sztanga na przednich barkach)',1,1,'exercise_0042'),
            (31,'Przysiad bułgarski (z tylną nogą na ławce)',1,1,'exercise_2368'),
            (32,'Martwy ciąg na prostych nogach',1,1,'exercise_0432'),
            (33,'Martwy ciąg rumuński',1,1,'exercise_0085'),
            (34,'Wykroki ze sztangą lub hantlami',1,1,'exercise_0054'),
            (35,'Wypychanie ciężaru na suwnicy',1,1,'exercise_0739'),
            (36,'Prostowanie nóg na maszynie (siedząc)',1,1,'exercise_0585'),
            (37,'Uginanie nóg na maszynie',1,1,'exercise_0586'),
            (38,'Wspięcia na palce stojąc (łydki)',1,1,'exercise_1373'),
            (39,'Wspięcia na palce siedząc (łydki)',1,1,'exercise_1371'),
            (40,'Przysiad sumo (szeroki rozstaw stóp)',1,1,'exercise_3142'),
            (41,'Mostek na jednej nodze',1,1,'exercise_3645'),
            (42,'Odwodzenie nogi w wyciągu',1,1,'exercise_0710'),
            (43,'Przysiad na jednej nodze (pistolet)',1,1,'exercise_0544'),
            (44,'Wyciskanie sztangi sprzed klatki',1,1,'exercise_1457'),
            (45,'Wyciskanie hantli siedząc',1,1,'exercise_0405'),
            (47,'Unoszenie hantli bokiem (bark boczny)',1,1,'exercise_0334'),
            (48,'Unoszenie hantli w opadzie tułowia (bark tylny)',1,1,'exercise_2292'),
            (49,'Unoszenie sztangi przed siebie (bark przedni)',1,1,'exercise_0041'),
            (50,'Arnoldki (wyciskanie hantli z rotacją)',1,1,'exercise_2137'),
            (51,'Face pull (wyciąg górny - linka do twarzy)',1,1,'exercise_0233'),
            (52,'Unoszenie ramion z linkami bokiem (wyciąg)',1,1,'exercise_0178'),
            (53,'Wiosłowanie sztangą pod brodę (wąski nachwyt)',1,1,'exercise_0120'),
            (54,'Unoszenie ramienia w bok na ławce',1,1,'exercise_0326'),
            (56,'Uginanie ramion ze sztangą prostą (stojąc)',1,1,'exercise_0031'),
            (57,'Uginanie ramion ze sztangą łamaną',1,1,'exercise_0447'),
            (58,'Uginanie ramion z hantlami stojąc (naprzemiennie)',1,1,'exercise_0285'),
            (59,'Uginanie ramion z hantlami stojąc (jednocześnie)',1,1,'exercise_0294'),
            (60,'Uginanie ramion z hantlami na ławce skośnej',1,1,'exercise_0318'),
            (61,'Uginanie ramion z hantlem w opadzie (jednorącz, z podparciem)',1,1,'exercise_0297'),
            (62,'Uginanie ramion z linkami wyciągu dolnego',1,1,'exercise_0868'),
            (63,'Uginanie ramion z hantlem młotkowe',1,1,'exercise_0298'),
            (64,'Uginanie ramion na modlitewniku z hantlami',1,1,'exercise_0372'),
            (65,'Uginanie ramion na modlitewniku ze sztangą',1,1,'exercise_0070'),
            (66,'Wyciskanie francuskie sztangi leżąc',1,1,'exercise_0061'),
            (67,'Wyciskanie francuskie hantli leżąc',1,1,'exercise_0351'),
            (68,'Prostowanie ramienia z linką wyciągu górnego',1,1,'exercise_0241'),
            (69,'Prostowanie ramienia z linką górną - jednorącz',1,1,'exercise_1723'),
            (70,'Pompki na poręczach równoległych (triceps)',1,1,'exercise_0814'),
            (71,'Pompki wąskie (dłonie przy sobie)',1,1,'exercise_0259'),
            (72,'Wyciskanie hantli za głową w siadzie',1,1,'exercise_2188'),
            (73,'Prostowanie ramienia z hantlem w opadzie tułowia',1,1,'exercise_0354'),
            (74,'Prostowanie ramienia z hantlem w opadzie',1,1,'exercise_0333'),
            (75,'Prostowanie ramion na wyciągu dolnym',1,1,'exercise_0173'),
            (76,'Brzuszki',1,1,'exercise_0274'),
            (77,'Unoszenie nóg w leżeniu',1,1,'exercise_0620'),
            (78,'Unoszenie nóg w zwisie na drążku',1,1,'exercise_0472'),
            (80,'Deska boczna',1,1,'exercise_3544'),
            (81,'Rowerek',1,1,'exercise_0972'),
            (82,'Nożyce',1,1,'exercise_0459'),
            (83,'Brzuszki odwrócone',1,1,'exercise_0872'),
            (84,'V-up (jednoczesne unoszenie tułowia i nóg)',1,1,'exercise_1014'),
            (85,'Deska dynamiczna',1,1,'exercise_3665'),
            (86,'Mountain climbers',1,1,'exercise_0630'),
            (88,'Brzuszki na piłce lekarskiej',1,1,'exercise_2297'),
            (89,'Allachy',1,1,'exercise_0175')
        """.trimIndent())

                // Powiązania z grupami mięśniowymi
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
                    //.fallbackToDestructiveMigration()
                    .addCallback(PREPOPULATE_CALLBACK)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}