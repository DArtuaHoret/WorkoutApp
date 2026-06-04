package com.example.workoutapp

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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
            TemplateListViewModel()
        }

        initializer<TemplateDetailViewModel> {
            TemplateDetailViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }

        initializer<ExerciseSearchViewModel> {
            ExerciseSearchViewModel()
        }

        initializer<ExerciseDetailViewModel> {
            ExerciseDetailViewModel(
                savedStateHandle = this.createSavedStateHandle()
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