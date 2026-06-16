package com.example.workoutapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.workoutapp.ui.screens.SettingsViewModel
import com.example.workoutapp.ui.screens.section_1.ExerciseDetailViewModel
import com.example.workoutapp.ui.screens.section_1.ExerciseSearchViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateDetailViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateListViewModel
import com.example.workoutapp.ui.screens.section_2.ExerciseTrackingViewModel
import com.example.workoutapp.ui.screens.section_3.TemplateExercisesViewModel
import com.example.workoutapp.ui.screens.section_3.SessionExerciseEditViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutCalendarViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutStatsViewModel
import com.example.workoutapp.ui.screens.section_4.AddMealSearchViewModel
import com.example.workoutapp.ui.screens.section_4.BarcodeScannerViewModel
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsViewModel
import com.example.workoutapp.ui.screens.section_4.ProductDetailViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutDetailsViewModel

object WorkoutAppViewModelProvider {
    val Factory = viewModelFactory {

        initializer<TemplateListViewModel> {
            TemplateListViewModel(
                templateRepository = workoutApp().workoutTemplateRepository,
            )
        }

        initializer<TemplateDetailViewModel> {
            TemplateDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                templateRepository = workoutApp().workoutTemplateRepository,
            )
        }

        initializer<SettingsViewModel> {
            SettingsViewModel(
                settingsRepository = workoutApp().settingsRepository,
            )
        }

        initializer<ExerciseSearchViewModel> {
            ExerciseSearchViewModel(
                exerciseRepository = workoutApp().exerciseRepository,
            )
        }

        initializer<ExerciseDetailViewModel> {
            ExerciseDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                exerciseRepository = workoutApp().exerciseRepository,
                templateRepository = workoutApp().workoutTemplateRepository,
            )
        }

        initializer<AddMealSearchViewModel> {
            AddMealSearchViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer<ProductDetailViewModel> {
            ProductDetailViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                foodRepository   = workoutApp().foodRepository,
            )
        }

        initializer<FavoriteProductsViewModel> {
            FavoriteProductsViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                foodRepository   = workoutApp().foodRepository,
            )
        }

        initializer<BarcodeScannerViewModel> {
            BarcodeScannerViewModel()
        }

        initializer<WorkoutStatsViewModel> {
            WorkoutStatsViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer<WorkoutCalendarViewModel> {
            WorkoutCalendarViewModel(
                templateRepository = workoutApp().workoutTemplateRepository,
                sessionRepository = workoutApp().workoutSessionRepository
            )
        }

        initializer<TemplateExercisesViewModel> {
            TemplateExercisesViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                sessionRepository = workoutApp().workoutSessionRepository,
                exerciseRepository = workoutApp().exerciseRepository
            )
        }

        initializer<SessionExerciseEditViewModel> {
            SessionExerciseEditViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                sessionRepository = workoutApp().workoutSessionRepository,
                exerciseRepository = workoutApp().exerciseRepository
            )
        }

        initializer<ExerciseTrackingViewModel> {
            ExerciseTrackingViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                templateRepository = workoutApp().workoutTemplateRepository,
                sessionRepository = workoutApp().workoutSessionRepository,
                exerciseRepository = workoutApp().exerciseRepository
            )
        }

        initializer<WorkoutDetailsViewModel> {
            WorkoutDetailsViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                sessionRepository = workoutApp().workoutSessionRepository,
                templateRepository = workoutApp().workoutTemplateRepository
            )
        }
    }
}

fun CreationExtras.workoutApp(): WorkoutApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WorkoutApp)