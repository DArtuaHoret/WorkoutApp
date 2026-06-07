package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.data.FoodProduct
import com.example.workoutapp.database.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface FavoriteProductsUiState {
    data class Favorites(val favorites: List<FavoriteProductItem> = emptyList())   : FavoriteProductsUiState
    data class MyProducts(val myProducts: List<FavoriteProductItem> = emptyList()) : FavoriteProductsUiState
}

class FavoriteProductsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,
) : ViewModel() {

    private val _showFavorites = MutableStateFlow(true)
    private val _uiState = MutableStateFlow<FavoriteProductsUiState>(FavoriteProductsUiState.Favorites())

    val uiState: StateFlow<FavoriteProductsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_showFavorites, foodRepository.getFavoriteAndCustomProducts()) { showFavorites, list ->
                if (showFavorites)
                    FavoriteProductsUiState.Favorites(list.filter { it.isFavorite }.map { it.toFavoriteItem() })
                else
                    FavoriteProductsUiState.MyProducts(list.filter { it.isCustom }.map { it.toFavoriteItem() })
            }.collect { _uiState.value = it }
        }
    }

    fun onShowFavorites()  { _showFavorites.value = true }
    fun onShowMyProducts() { _showFavorites.value = false }

    fun onToggleFavorite(product: FavoriteProductItem) {
        viewModelScope.launch {
            foodRepository.setFavorite(product.id.toLong(), !product.isFavorite)
        }
    }
}

private fun FoodProduct.toFavoriteItem() = FavoriteProductItem(
    id          = id.toString(),
    name        = name,
    description = description,
    kcal        = calories.toInt().toString(),
    protein     = protein.toString(),
    fat         = fat.toString(),
    carbs       = carbs.toString(),
    isFavorite  = isFavorite,
    isCustom    = isCustom,
)