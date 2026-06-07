package com.example.workoutapp

import android.app.Application
import com.example.workoutapp.database.AppDatabase
import com.example.workoutapp.database.ExerciseRepositoryImpl
import com.example.workoutapp.database.FoodRepositoryImpl
import com.example.workoutapp.database.WorkoutTemplateRepositoryImpl

class WorkoutApp : Application() {
    val exerciseRepository by lazy {
        ExerciseRepositoryImpl(AppDatabase.getDatabase(this).exerciseDao())
    }

    val workoutTemplateRepository by lazy {
        WorkoutTemplateRepositoryImpl(AppDatabase.getDatabase(this).workoutTemplateDao())
    }

    val foodRepository by lazy {
        FoodRepositoryImpl(AppDatabase.getDatabase(this).foodDao())
    }
    override fun onCreate() {
        super.onCreate()
        //    storage = ReminderStorage(this) // stara wersja (dla porównania, jak było wcześniej bez repozytorium)
    }
}

