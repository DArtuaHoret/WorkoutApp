package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface FavoriteProductsUiState {
    data class Favorites(
        val favorites: List<FavoriteProductItem> = emptyList(),
    ) : FavoriteProductsUiState

    data class MyProducts(
        val myProducts: List<FavoriteProductItem> = emptyList(),
    ) : FavoriteProductsUiState
}

class FavoriteProductsViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavoriteProductsUiState>(
        FavoriteProductsUiState.Favorites(
            favorites = listOf(
                FavoriteProductItem("1", "Jabłko", "Świeże owoce", "52", "0.3", "0.2", "14"),
                FavoriteProductItem("2", "Banan", "Bogate w potas", "89", "1.1", "0.3", "23"),
            ),
        )
    )
    val uiState: StateFlow<FavoriteProductsUiState> = _uiState.asStateFlow()

    fun onShowFavorites() {
        _uiState.value = FavoriteProductsUiState.Favorites(
            favorites = listOf(
                FavoriteProductItem("1", "Jabłko", "Świeże owoce", "52", "0.3", "0.2", "14"),
                FavoriteProductItem("2", "Banan", "Bogate w potas", "89", "1.1", "0.3", "23"),
            )
        )
    }

    fun onShowMyProducts() {
        _uiState.value = FavoriteProductsUiState.MyProducts(
            myProducts = listOf(
                FavoriteProductItem("3", "Mój shake", "Własna receptura", "320", "30", "8", "25"),
            )
        )
    }

    fun onRemoveFavorite(product: FavoriteProductItem) {
        val current = _uiState.value as? FavoriteProductsUiState.Favorites ?: return
        _uiState.value = current.copy(
            favorites = current.favorites.filter { it.id != product.id }
        )
    }
}