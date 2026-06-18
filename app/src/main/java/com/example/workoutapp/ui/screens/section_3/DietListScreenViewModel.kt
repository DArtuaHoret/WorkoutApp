package com.example.workoutapp.ui.screens.section_3

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.database.FoodRepository
import com.example.workoutapp.ui.reusableContents.Section_1.toLocalNoonMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

data class MealDetailsUiState(
    val products: List<LoggedProductItem> = emptyList(),
    val date: LocalDate? = null,
    val isLoading: Boolean = false,
)

class MealDetailsViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealDetailsUiState(isLoading = true))
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    private val date: Date = Date(
        (savedStateHandle.get<Long>("dateMillis") ?: System.currentTimeMillis()).toLocalNoonMillis()
    )

    private val localDate: LocalDate = date.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    init {
        viewModelScope.launch {
            combine(
                foodRepository.getEntriesForDate(date),
                foodRepository.getAllActiveProducts(),
            ) { entries, products ->
                val productMap = products.associateBy { it.id }
                entries.mapNotNull { entry ->
                    val product = productMap[entry.foodProductId] ?: return@mapNotNull null
                    LoggedProductItem(
                        id          = entry.id.toString(),
                        name        = product.name,
                        description = product.description,
                        grams       = entry.grams.toInt().toString(),
                        kcal        = (product.calories * entry.grams / 100.0).toInt().toString(),
                        protein     = "%.1f".format(product.protein * entry.grams / 100.0),
                        fat         = "%.1f".format(product.fat     * entry.grams / 100.0),
                        carbs       = "%.1f".format(product.carbs   * entry.grams / 100.0),
                    )
                }
            }.collect { items ->
                _uiState.value = MealDetailsUiState(
                    products  = items,
                    date      = localDate,
                    isLoading = false,
                )
            }
        }
    }

    fun onDeleteProduct(product: LoggedProductItem) {
        viewModelScope.launch {
            foodRepository.deleteEntryById(product.id.toLong())
        }
    }

    fun onGramsChanged(product: LoggedProductItem, newGrams: String) {
        val grams = newGrams.toDoubleOrNull()?.takeIf { it > 0.0 } ?: return
        viewModelScope.launch {
            foodRepository.updateEntryGrams(product.id.toLong(), grams)
        }
    }
}