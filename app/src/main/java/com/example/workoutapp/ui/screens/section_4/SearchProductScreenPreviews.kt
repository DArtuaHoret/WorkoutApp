package com.example.workoutapp.ui.screens.section_4
/*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

// ─── PREVIEWS ────────────────────────────────────────────────────────────────

private val sampleProducts = listOf(
    ProductSearchItem("1", "Jabłko",         "BBBB Dobre Omega-3 FFF FFF FFF FFFDobre Omega-3 FFF FFF FFF FFF"),
    ProductSearchItem("2", "Łosoś pieczony", "Dobre Omega-3 FFF FFF FFF FFF FFF FFF"),
    ProductSearchItem("3", "Chleb żytni",    "Złożone węglowodany"),
    ProductSearchItem("4", "Banan",          ""),
)

@Preview(name = "AddMealSearchScreen – empty (no query)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSearchScreenEmpty() {
    MaterialTheme {
        AddMealSearchScreen(
            query = "",
            onQueryChange = {},
            searchResults = emptyList(),
            onProductAddClick = {},
            onAddCustomProductClick = {},
        )
    }
}

@Preview(name = "AddMealSearchScreen – with results", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSearchScreenWithResults() {
    var query by remember { mutableStateOf("a") }
    val filtered = sampleProducts.filter { it.name.contains(query, ignoreCase = true) }
    MaterialTheme {
        AddMealSearchScreen(
            query = query,
            onQueryChange = { query = it },
            searchResults = filtered,
            onProductAddClick = {},
            onAddCustomProductClick = {},
        )
    }
}

@Preview(name = "AddMealSearchScreen – no results found", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSearchScreenNoResults() {
    MaterialTheme {
        AddMealSearchScreen(
            query = "x",
            onQueryChange = {},
            searchResults = emptyList(),
            onProductAddClick = {},
            onAddCustomProductClick = {},
        )
    }
}

*/