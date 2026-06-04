package com.example.workoutapp.ui.screens.section_4
/*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

private val sampleArgs = ProductDetailArgs(
    id = "1",
    name = "JABŁKO",
    description = "Świeże, słodkie i chrupiące owoce, bogate\nw błonnik i witaminę C.",
    kcal = "52",
    protein = "0.3",
    fat = "0.2",
    carbs = "14",
)

@Preview(name = "ProductDetailScreen – View mode", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductDetailView() {
    MaterialTheme {
        ProductDetailScreen(
            mode = ProductDetailMode.View(sampleArgs),
            onAddToMealClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ProductDetailScreen – Create mode (empty)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductDetailCreateEmpty() {
    MaterialTheme {
        ProductDetailScreen(
            mode = ProductDetailMode.Create,
            onSaveProductClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ProductDetailScreen – Create mode (filled)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductDetailCreateFilled() {
    var name by remember { mutableStateOf("Banan") }
    var desc by remember { mutableStateOf("Dobre źródło potasu") }
    var kcal by remember { mutableStateOf("89") }
    var protein by remember { mutableStateOf("1.1") }
    var fat by remember { mutableStateOf("0.3") }
    var carbs by remember { mutableStateOf("23") }

    MaterialTheme {
        ProductDetailScreen(
            mode = ProductDetailMode.Create,
            productName = name,
            onProductNameChange = { name = it },
            productDescription = desc,
            onProductDescriptionChange = { desc = it },
            kcal = kcal,
            onKcalChange = { kcal = it },
            protein = protein,
            onProteinChange = { protein = it },
            fat = fat,
            onFatChange = { fat = it },
            carbs = carbs,
            onCarbsChange = { carbs = it },
            onSaveProductClick = {},
            onBackClick = {},
        )
    }
}

@Preview(name = "ProductDetailScreen – View, NOT favorite", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewDetailViewNotFavorite() {
    MaterialTheme {
        ProductDetailScreen(
            mode = ProductDetailMode.View(sampleArgs),
            onBackClick = {},
            isFavorite = false,
        )
    }
}

@Preview(name = "ProductDetailScreen – View, IS favorite", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewDetailViewIsFavorite() {
    MaterialTheme {
        ProductDetailScreen(
            mode = ProductDetailMode.View(sampleArgs),
            onBackClick = {},
            isFavorite = true,
        )
    }
}
*/