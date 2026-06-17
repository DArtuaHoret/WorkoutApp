package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.OpenFoodBarcodeResponse
import com.example.workoutapp.OpenFoodProductDto
import com.example.workoutapp.OpenFoodRetrofitClient
import com.example.workoutapp.data.FoodEntry
import com.example.workoutapp.data.FoodProduct
import com.example.workoutapp.database.FoodRepository
import com.example.workoutapp.ui.reusableContents.Section_1.toLocalNoonMillis
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

sealed interface AddMealSearchUiState {
    object Idle    : AddMealSearchUiState
    object Loading : AddMealSearchUiState
    data class Success(val results: List<ProductSearchItem>) : AddMealSearchUiState
    data class Error(val message: String)                    : AddMealSearchUiState
}

class AddMealSearchViewModel(
    savedStateHandle: SavedStateHandle,
    private val foodRepository: FoodRepository,  // DODAJ
) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.Diet>()

    private val _query   = MutableStateFlow(args.initialQuery)
    private val _uiState = MutableStateFlow<AddMealSearchUiState>(AddMealSearchUiState.Idle)

    val query:   StateFlow<String>              = _query.asStateFlow()
    val uiState: StateFlow<AddMealSearchUiState> = _uiState.asStateFlow()

    private val _pendingProduct = MutableStateFlow<ProductSearchItem?>(null)
    private val _showDatePicker = MutableStateFlow(false)
    val pendingProduct: StateFlow<ProductSearchItem?> = _pendingProduct.asStateFlow()
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    // NOWA: metoda do otwierania dialogu daty
    fun showDatePickerForProduct(product: ProductSearchItem) {
        _pendingProduct.value = product
        _showDatePicker.value = true
    }

    // NOWA: metoda do zamykania dialogu daty
    fun dismissDatePicker() {
        _pendingProduct.value = null
        _showDatePicker.value = false
    }

    // NOWA: metoda do potwierdzenia daty i dodania produktu
    fun confirmDateAndAddProduct(dateMillis: Long) {
        val product = _pendingProduct.value
        if (product != null) {
            quickAddProduct(product, dateMillis) {
                dismissDatePicker()
            }
        } else {
            dismissDatePicker()
        }
    }

    init {
        if (args.initialQuery.isNotBlank()) fetchByBarcode(args.initialQuery)
    }

    fun quickAddProduct(product: ProductSearchItem, dateMillis: Long, onDone: () -> Unit) {
        viewModelScope.launch {
            val existing = foodRepository.findExactMatch(
                name     = product.name,
                calories = product.kcal.toDoubleOrNull() ?: 0.0,
                protein  = product.protein.toDoubleOrNull() ?: 0.0,
                fat      = product.fat.toDoubleOrNull() ?: 0.0,
                carbs    = product.carbs.toDoubleOrNull() ?: 0.0,
            )
            val productId = existing?.id ?: foodRepository.insertProduct(
                FoodProduct(
                    name = product.name,
                    description = product.description,
                    calories = product.kcal.toDoubleOrNull() ?: 0.0,
                    protein = product.protein.toDoubleOrNull() ?: 0.0,
                    fat = product.fat.toDoubleOrNull() ?: 0.0,
                    carbs = product.carbs.toDoubleOrNull() ?: 0.0,
                )
            )
            foodRepository.insertEntry(
                FoodEntry(
                    foodProductId = productId,
                    grams = 100.0,
                    eatenAt = Date(dateMillis.toLocalNoonMillis()),
                )
            )
            onDone()
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) { _uiState.value = AddMealSearchUiState.Idle; return }
        if (newQuery.length >= 8) fetchByBarcode(newQuery)
    }



    private fun fetchByBarcode(barcode: String) {
        _uiState.value = AddMealSearchUiState.Loading
        OpenFoodRetrofitClient.productServiceInstance
            .getProductByBarcode(barcode)
            .enqueue(object : retrofit2.Callback<OpenFoodBarcodeResponse> {
                override fun onResponse(
                    call: retrofit2.Call<OpenFoodBarcodeResponse>,
                    response: retrofit2.Response<OpenFoodBarcodeResponse>,
                ) {
                    val product = response.body()?.product
                    _uiState.value = if (response.isSuccessful && product != null)
                        AddMealSearchUiState.Success(listOf(product.toSearchItem(barcode)))
                    else
                        AddMealSearchUiState.Success(emptyList())
                }
                override fun onFailure(call: retrofit2.Call<OpenFoodBarcodeResponse>, t: Throwable) {
                    _uiState.value = AddMealSearchUiState.Error(t.message ?: "Błąd sieci")
                }
            })
    }
}

private fun OpenFoodProductDto.toSearchItem(barcode: String) = ProductSearchItem(
    id          = barcode,
    name        = productName ?: "Nieznany produkt ($barcode)",
    description = if (nutriments == null) "Brak danych odżywczych" else "",
    kcal        = nutriments?.kcal100g?.toInt()?.toString() ?: "",
    protein     = nutriments?.protein100g?.toString() ?: "",
    fat         = nutriments?.fat100g?.toString() ?: "",
    carbs       = nutriments?.carbs100g?.toString() ?: "",
)

