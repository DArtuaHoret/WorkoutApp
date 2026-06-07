package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.workoutapp.Destinations
import com.example.workoutapp.OpenFoodBarcodeResponse
import com.example.workoutapp.OpenFoodProductDto
import com.example.workoutapp.OpenFoodRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AddMealSearchUiState {
    object Idle    : AddMealSearchUiState
    object Loading : AddMealSearchUiState
    data class Success(val results: List<ProductSearchItem>) : AddMealSearchUiState
    data class Error(val message: String)                    : AddMealSearchUiState
}

class AddMealSearchViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val args = savedStateHandle.toRoute<Destinations.Diet>()

    private val _query   = MutableStateFlow(args.initialQuery)
    private val _uiState = MutableStateFlow<AddMealSearchUiState>(AddMealSearchUiState.Idle)

    val query:   StateFlow<String>              = _query.asStateFlow()
    val uiState: StateFlow<AddMealSearchUiState> = _uiState.asStateFlow()

    init {
        if (args.initialQuery.isNotBlank()) fetchByBarcode(args.initialQuery)
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) { _uiState.value = AddMealSearchUiState.Idle; return }
        if (newQuery.length >= 8) fetchByBarcode(newQuery)
    }

    fun onSearch() { /* TODO: text search */ }

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