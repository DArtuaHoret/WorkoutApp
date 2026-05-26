package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// ─── PREVIEWS ────────────────────────────────────────────────────────────────

private val sampleFavorites = listOf(
    FavoriteProductItem("1", "Łosoś pieczony", "Dobre źródło kwasów Omega-3 FFFFF FFFFFFFFFFFF FFFFFFFF", "208", "20", "13", "0"),
    FavoriteProductItem("2", "Jabłko",         "Bogate w błonnik i witaminę C", "52",  "0.3", "0.2", "14"),
    FavoriteProductItem("3", "Chleb żytni",    "Złożone węglowodany",          "259", "8.5", "3.3", "48"),
    FavoriteProductItem("4", "Banan",          "",          "89",  "1.1", "0.3", "23"),
)

@Preview(name = "FavoriteProductsScreen – with items", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFavoriteScreenWithItems() {
    MaterialTheme {
        FavoriteProductsScreen(
            favoriteProducts = sampleFavorites,
            onBackClick = {},
            onProductClick = {},
            onRemoveFavoriteClick = {},
        )
    }
}

@Preview(name = "FavoriteProductsScreen – empty state", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFavoriteScreenEmpty() {
    MaterialTheme {
        FavoriteProductsScreen(
            favoriteProducts = emptyList(),
            onBackClick = {},
            onProductClick = {},
            onRemoveFavoriteClick = {},
        )
    }
}

