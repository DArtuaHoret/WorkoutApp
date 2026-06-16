package com.example.workoutapp

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.example.workoutapp.database.AppDatabase
import com.example.workoutapp.database.ExerciseRepositoryImpl
import com.example.workoutapp.database.FoodRepositoryImpl
import com.example.workoutapp.database.SettingsRepository
import com.example.workoutapp.database.WorkoutSessionRepositoryImpl
import com.example.workoutapp.database.WorkoutTemplateRepositoryImpl

class WorkoutApp : Application(), ImageLoaderFactory {
    val exerciseRepository by lazy {
        ExerciseRepositoryImpl(AppDatabase.getDatabase(this).exerciseDao())
    }

    val workoutTemplateRepository by lazy {
        WorkoutTemplateRepositoryImpl(AppDatabase.getDatabase(this).workoutTemplateDao())
    }

    val foodRepository by lazy {
        FoodRepositoryImpl(AppDatabase.getDatabase(this).foodDao())
    }

    val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(this)
    }

    val workoutSessionRepository by lazy {
        WorkoutSessionRepositoryImpl(AppDatabase.getDatabase(this).workoutSessionDao())
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
    }
}