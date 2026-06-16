package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.data.FoodProduct
import com.example.workoutapp.database.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProductDetailUiState {
    data class View(
        val args: ProductDetailArgs,
        val isFavorite: Boolean = false,
        val canDelete: Boolean = false,
    ) : ProductDetailUiState

    data class Create(
        val productName: String        = "",
        val productDescription: String = "",
        val kcal: String               = "",
        val protein: String            = "",
        val fat: String                = "",
        val carbs: String              = "",
        val isFavorite: Boolean        = false,
        val editId: String?            = null,
    ) : ProductDetailUiState
}

class ProductDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(buildInitialState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        val current = _uiState.value as? ProductDetailUiState.View
        if (current != null) {
            viewModelScope.launch {
                val existing = foodRepository.findActiveByName(current.args.name)
                if (existing != null) _uiState.value = current.copy(isFavorite = existing.isFavorite)
            }
        }
    }



    fun onFavoriteClick() {
        val current = _uiState.value as? ProductDetailUiState.View ?: return
        val newFavorite = !current.isFavorite
        _uiState.value = current.copy(isFavorite = newFavorite)
        viewModelScope.launch {
            val args = current.args
            val existing = foodRepository.findExactMatch(
                name     = args.name,
                calories = args.kcal.toDoubleOrNull() ?: 0.0,
                protein  = args.protein.toDoubleOrNull() ?: 0.0,
                fat      = args.fat.toDoubleOrNull() ?: 0.0,
                carbs    = args.carbs.toDoubleOrNull() ?: 0.0,
            )
            val productId = existing?.id ?: foodRepository.insertProduct(
                FoodProduct(
                    name     = args.name,
                    calories = args.kcal.toDoubleOrNull() ?: 0.0,
                    protein  = args.protein.toDoubleOrNull() ?: 0.0,
                    fat      = args.fat.toDoubleOrNull() ?: 0.0,
                    carbs    = args.carbs.toDoubleOrNull() ?: 0.0,
                )
            )
            foodRepository.setFavorite(productId, newFavorite)
        }
    }



    fun onProductNameChange(value: String)        = updateCreate { copy(productName = value) }
    fun onProductDescriptionChange(value: String) = updateCreate { copy(productDescription = value) }
    fun onKcalChange(value: String)               = updateCreate { copy(kcal = value) }
    fun onProteinChange(value: String)            = updateCreate { copy(protein = value) }
    fun onFatChange(value: String)                = updateCreate { copy(fat = value) }
    fun onCarbsChange(value: String)              = updateCreate { copy(carbs = value) }
    fun onToggleCreateFavorite()                  = updateCreate { copy(isFavorite = !isFavorite) }

    fun onSaveProductClick() {
        val current = _uiState.value as? ProductDetailUiState.Create ?: return
        viewModelScope.launch {
            val editId = current.editId?.toLongOrNull()
            val product = FoodProduct(
                id          = editId ?: 0L,
                name        = current.productName,
                description = current.productDescription,
                calories    = current.kcal.toDoubleOrNull() ?: 0.0,
                protein     = current.protein.toDoubleOrNull() ?: 0.0,
                fat         = current.fat.toDoubleOrNull() ?: 0.0,
                carbs       = current.carbs.toDoubleOrNull() ?: 0.0,
                isCustom    = true,
                isFavorite  = current.isFavorite,
            )
            if (editId != null) foodRepository.updateProduct(product)
            else foodRepository.insertProduct(product)
        }
    }



    private fun updateCreate(block: ProductDetailUiState.Create.() -> ProductDetailUiState.Create) {
        val current = _uiState.value as? ProductDetailUiState.Create ?: return
        _uiState.value = current.block()
    }

    fun deleteProduct(onDeleted: () -> Unit) {
        val current = _uiState.value as? ProductDetailUiState.View ?: return
        viewModelScope.launch {
            val product = foodRepository.findActiveByName(current.args.name) ?: return@launch
            foodRepository.deactivateProduct(product.id)
            onDeleted()
        }
    }

    private fun buildInitialState(): ProductDetailUiState {
        val id          = savedStateHandle.get<String>("id")          ?: ""
        val name        = savedStateHandle.get<String>("name")        ?: ""
        val description = savedStateHandle.get<String>("description") ?: ""
        val kcal        = savedStateHandle.get<String>("kcal")        ?: ""
        val protein     = savedStateHandle.get<String>("protein")     ?: ""
        val fat         = savedStateHandle.get<String>("fat")         ?: ""
        val carbs       = savedStateHandle.get<String>("carbs")       ?: ""
        val isEditMode  = savedStateHandle.get<Boolean>("isEditMode") ?: false
        val isFavorite  = savedStateHandle.get<Boolean>("isFavorite") ?: false
        val canDelete = savedStateHandle.get<Boolean>("canDelete") ?: false

        return when {
            isEditMode -> ProductDetailUiState.Create(
                productName        = name,
                productDescription = description,
                kcal               = kcal,
                protein            = protein,
                fat                = fat,
                carbs              = carbs,
                editId             = id.takeIf { it.isNotEmpty() },
                isFavorite         = isFavorite,
            )
            id.isNotEmpty() -> ProductDetailUiState.View(
                args = ProductDetailArgs(id, name, description, kcal, protein, fat, carbs),
                canDelete = canDelete,

            )
            else -> ProductDetailUiState.Create()
        }
    }
}