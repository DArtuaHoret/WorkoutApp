package com.example.workoutapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.workoutapp.database.ExerciseRepositoryImpl
import com.example.workoutapp.ui.screens.section_1.ExerciseDetailViewModel
import com.example.workoutapp.ui.screens.section_1.ExerciseSearchViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateDetailViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateListViewModel
import com.example.workoutapp.ui.screens.section_4.AddMealSearchViewModel
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsViewModel
import com.example.workoutapp.ui.screens.section_4.ProductDetailViewModel

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
            AddMealSearchViewModel()
        }

        initializer<ProductDetailViewModel> {
            ProductDetailViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer<FavoriteProductsViewModel> {
            FavoriteProductsViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}

fun CreationExtras.workoutApp(): WorkoutApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WorkoutApp)