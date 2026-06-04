package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ---------------------------------------------------------------------------
// UI State
// ---------------------------------------------------------------------------

sealed interface ProductDetailUiState {
    data class View(
        val args: ProductDetailArgs,
        val isFavorite: Boolean = false,
    ) : ProductDetailUiState

    data class Create(
        val productName: String = "",
        val productDescription: String = "",
        val kcal: String = "",
        val protein: String = "",
        val fat: String = "",
        val carbs: String = "",
    ) : ProductDetailUiState
}

// ---------------------------------------------------------------------------
// ViewModel
// ---------------------------------------------------------------------------

class ProductDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(
        if (savedStateHandle.contains("id")) {
            ProductDetailUiState.View(
                args = ProductDetailArgs(
                    id          = savedStateHandle["id"]          ?: "",
                    name        = savedStateHandle["name"]        ?: "",
                    description = savedStateHandle["description"] ?: "",
                    kcal        = savedStateHandle["kcal"]        ?: "",
                    protein     = savedStateHandle["protein"]     ?: "",
                    fat         = savedStateHandle["fat"]         ?: "",
                    carbs       = savedStateHandle["carbs"]       ?: "",
                )
            )
        } else {
            ProductDetailUiState.Create()
        }
    )
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    // ── Tryb View ───────────────────────────────────────────────────────────

    fun onFavoriteClick() {
        val current = _uiState.value as? ProductDetailUiState.View ?: return
        _uiState.value = current.copy(isFavorite = !current.isFavorite)
    }

    // ── Tryb Create ─────────────────────────────────────────────────────────

    fun onProductNameChange(value: String) = updateCreate { copy(productName = value) }
    fun onProductDescriptionChange(value: String) = updateCreate { copy(productDescription = value) }
    fun onKcalChange(value: String) = updateCreate { copy(kcal = value) }
    fun onProteinChange(value: String) = updateCreate { copy(protein = value) }
    fun onFatChange(value: String) = updateCreate { copy(fat = value) }
    fun onCarbsChange(value: String) = updateCreate { copy(carbs = value) }

    fun onSaveProductClick() {
        val current = _uiState.value as? ProductDetailUiState.Create ?: return
        // TODO: walidacja + zapis do repozytorium
        //   viewModelScope.launch {
        //       productRepository.save(current.toProduct())
        //   }
    }

    // ── Helper ──────────────────────────────────────────────────────────────

    private fun updateCreate(block: ProductDetailUiState.Create.() -> ProductDetailUiState.Create) {
        val current = _uiState.value as? ProductDetailUiState.Create ?: return
        _uiState.value = current.block()
    }
}