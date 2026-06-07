package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.ViewModel
import com.example.workoutapp.OpenFoodBarcodeResponse
import com.example.workoutapp.OpenFoodProductDto
import com.example.workoutapp.OpenFoodRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface BarcodeScannerUiState {
    object Scanning : BarcodeScannerUiState
    object Loading  : BarcodeScannerUiState
    data class Found(val barcode: String, val product: ProductSearchItem) : BarcodeScannerUiState
    data class NotFound(val barcode: String)  : BarcodeScannerUiState
    data class Error(val message: String)     : BarcodeScannerUiState
}

class BarcodeScannerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<BarcodeScannerUiState>(BarcodeScannerUiState.Scanning)
    val uiState: StateFlow<BarcodeScannerUiState> = _uiState.asStateFlow()

    fun onBarcodeDetected(barcode: String) {
        if (_uiState.value is BarcodeScannerUiState.Loading) return
        _uiState.value = BarcodeScannerUiState.Loading
        OpenFoodRetrofitClient.productServiceInstance
            .getProductByBarcode(barcode)
            .enqueue(object : retrofit2.Callback<OpenFoodBarcodeResponse> {
                override fun onResponse(
                    call: retrofit2.Call<OpenFoodBarcodeResponse>,
                    response: retrofit2.Response<OpenFoodBarcodeResponse>,
                ) {
                    val product = response.body()?.product
                    _uiState.value = if (response.isSuccessful && product != null)
                        BarcodeScannerUiState.Found(barcode, product.toSearchItem(barcode))
                    else
                        BarcodeScannerUiState.NotFound(barcode)
                }
                override fun onFailure(call: retrofit2.Call<OpenFoodBarcodeResponse>, t: Throwable) {
                    _uiState.value = BarcodeScannerUiState.Error(t.message ?: "Błąd sieci")
                }
            })
    }

    fun onReset() { _uiState.value = BarcodeScannerUiState.Scanning }
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