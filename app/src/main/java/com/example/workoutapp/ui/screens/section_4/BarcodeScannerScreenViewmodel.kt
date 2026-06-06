package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface BarcodeScannerUiState {
    object Scanning : BarcodeScannerUiState
    object Loading : BarcodeScannerUiState
    data class Found(val barcode: String, val product: ProductSearchItem) : BarcodeScannerUiState
    data class NotFound(val barcode: String) : BarcodeScannerUiState
    data class Error(val message: String) : BarcodeScannerUiState
}

class BarcodeScannerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<BarcodeScannerUiState>(BarcodeScannerUiState.Scanning)
    val uiState: StateFlow<BarcodeScannerUiState> = _uiState.asStateFlow()

    fun onBarcodeDetected(barcode: String) {
        if (_uiState.value is BarcodeScannerUiState.Loading) return
        _uiState.value = BarcodeScannerUiState.Loading

        // TODO: zastąp prawdziwym wywołaniem API/repo
        val stub = mapOf(
            "5901234123457" to ProductSearchItem("1", "Chleb żytni", "Kcal: 259 | B: 8g | T: 3g | W: 48g"),
            "4006381333931" to ProductSearchItem("2", "Banan", "Kcal: 89 | B: 1.1g | T: 0.3g | W: 23g"),
        )
        val found = stub[barcode]
        _uiState.value = if (found != null)
            BarcodeScannerUiState.Found(barcode = barcode, product = found)
        else
            BarcodeScannerUiState.NotFound(barcode)
    }

    fun onReset() {
        _uiState.value = BarcodeScannerUiState.Scanning
    }
}