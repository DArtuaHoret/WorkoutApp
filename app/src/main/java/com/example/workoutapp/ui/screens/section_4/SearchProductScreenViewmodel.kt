package com.example.workoutapp.ui.screens.section_4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed interface AddMealSearchUiState {
    object Idle : AddMealSearchUiState
    object Loading : AddMealSearchUiState
    data class Success(val results: List<ProductSearchItem>) : AddMealSearchUiState
    data class Error(val message: String) : AddMealSearchUiState
}


class AddMealSearchViewModel : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()
    private val _uiState = MutableStateFlow<AddMealSearchUiState>(AddMealSearchUiState.Idle)
    val uiState: StateFlow<AddMealSearchUiState> = _uiState.asStateFlow()

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery

        if (newQuery.isBlank()) {
            _uiState.value = AddMealSearchUiState.Idle
            return
        }

        searchProducts(newQuery)
    }

    /** Trigger a fresh search with the current query (e.g. on IME "Search" action). */
    fun onSearch() {
        val current = _query.value
        if (current.isNotBlank()) searchProducts(current)
    }

    // --- private helpers -------------------------------------------------

    private fun searchProducts(query: String) {
        _uiState.value = AddMealSearchUiState.Loading

        // TODO: replace with real repository / API call, e.g.:
        //   viewModelScope.launch {
        //       try {
        //           val results = productRepository.search(query)
        //           _uiState.value = AddMealSearchUiState.Success(results)
        //       } catch (e: Exception) {
        //           _uiState.value = AddMealSearchUiState.Error(e.message ?: "Błąd wyszukiwania")
        //       }
        //   }

        // Temporary stub – pretend the search returned fake results
        val stub = listOf(
            ProductSearchItem("1", "Jabłko",     "Kcal: 52  | B: 0.3g | T: 0.2g | W: 14g"),
            ProductSearchItem("2", "Banan",      "Kcal: 89  | B: 1.1g | T: 0.3g | W: 23g"),
            ProductSearchItem("3", "Kurczak",    "Kcal: 165 | B: 31g  | T: 3.6g | W: 0g"),
        ).filter { it.name.contains(query, ignoreCase = true) }

        _uiState.value = AddMealSearchUiState.Success(stub)
    }
}