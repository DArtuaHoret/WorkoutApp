package com.example.workoutapp

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.workoutapp.ui.screens.section_4.AddMealSearchViewModel
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsViewModel
import com.example.workoutapp.ui.screens.section_4.ProductDetailUiState
import com.example.workoutapp.ui.screens.section_4.ProductDetailViewModel

object WorkoutAppViewModelProvider {
    val Factory = viewModelFactory {

        initializer<AddMealSearchViewModel> {   // ← dodaj typ generyczny
            AddMealSearchViewModel()
        }

        initializer<ProductDetailViewModel> {   // ← dodaj typ generyczny
            ProductDetailViewModel(
                savedStateHandle = this.createSavedStateHandle()
            )
        }
        initializer<FavoriteProductsViewModel> {
            FavoriteProductsViewModel(savedStateHandle = this.createSavedStateHandle())
        }
    }
}
