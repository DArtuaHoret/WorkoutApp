package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_4.*

// Dane produktu przekazywane przez nawigację
data class ProductDetailArgs(
    val id: String,
    val name: String,
    val description: String,
    val kcal: String,
    val protein: String,
    val fat: String,
    val carbs: String,
)

sealed interface ProductDetailMode {
    // Podgląd istniejącego produktu – wymagane args
    data class View(val args: ProductDetailArgs) : ProductDetailMode
    // Tworzenie nowego produktu – pola puste, edytowalne
    data object Create : ProductDetailMode
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    mode: ProductDetailMode,
    // View mode
    onAddToMealClick: () -> Unit = {},
    onBackClick: () -> Unit,
    isFavorite: Boolean = false,               // ← NOWE
    onFavoriteClick: () -> Unit = {},
    // Create mode
    productName: String = "",
    onProductNameChange: (String) -> Unit = {},
    productDescription: String = "",
    onProductDescriptionChange: (String) -> Unit = {},
    kcal: String = "",
    onKcalChange: (String) -> Unit = {},
    protein: String = "",
    onProteinChange: (String) -> Unit = {},
    fat: String = "",
    onFatChange: (String) -> Unit = {},
    carbs: String = "",
    onCarbsChange: (String) -> Unit = {},
    onSaveProductClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (mode) {
                            is ProductDetailMode.Create -> "Dodaj produkt"
                            is ProductDetailMode.View   -> "Szczegóły produktu"
                        },
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Wróć",
                            tint = Color.White,
                        )
                    }
                },
                // ── Serduszko – tylko w trybie View ──────────────────────
                actions = {
                    if (mode is ProductDetailMode.View) {
                        IconButton(onClick = onFavoriteClick) {
                            Icon(
                                imageVector = if (isFavorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = if (isFavorite) "Usuń z ulubionych" else "Dodaj do ulubionych",
                                tint = if (isFavorite) Color(0xFFFF4D4D) else Color.White,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                ),
            )
        },
        containerColor = Color.Black,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)               // ← respektuje wysokość TopAppBar
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            when (mode) {

                // ── TRYB PODGLĄDU ─────────────────────────────────────────────
                is ProductDetailMode.View -> {
                    val args = mode.args

                    ProductDetailHeaderCard(
                        productName = args.name,
                        productDescription = args.description,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        NutrientCard(
                            label = "ENERGIA",
                            value = "${args.kcal} KCAL",
                            modifier = Modifier.weight(1f),
                        ) { Text("⚡", fontSize = 22.sp) }

                        NutrientCard(
                            label = "BIAŁKO",
                            value = "${args.protein} g",
                            modifier = Modifier.weight(1f),
                        ) { Text("💪", fontSize = 22.sp) }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        NutrientCard(
                            label = "TŁUSZCZE",
                            value = "${args.fat} g",
                            modifier = Modifier.weight(1f),
                        ) { Text("🫙", fontSize = 22.sp) }

                        NutrientCard(
                            label = "WĘGLOWODANY",
                            value = "${args.carbs} g",
                            modifier = Modifier.weight(1f),
                        ) { Text("🌾", fontSize = 22.sp) }
                    }

                    ActionButton(
                        onClick = onAddToMealClick,
                        label = "DODAJ DO POSIŁKU",
                        icon = null,
                        style = ActionButtonStyle.LightFilled,
                    )
                }

                // ── TRYB TWORZENIA ────────────────────────────────────────────
                is ProductDetailMode.Create -> {

                    EditableProductDetailHeaderCard(
                        productName = productName,
                        onProductNameChange = onProductNameChange,
                        productDescription = productDescription,
                        onProductDescriptionChange = onProductDescriptionChange,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EditableNutrientCard(
                            label = "ENERGIA",
                            value = kcal,
                            onValueChange = onKcalChange,
                            modifier = Modifier.weight(1f),
                        ) { Text("⚡", fontSize = 22.sp) }

                        EditableNutrientCard(
                            label = "BIAŁKO",
                            value = protein,
                            onValueChange = onProteinChange,
                            modifier = Modifier.weight(1f),
                        ) { Text("💪", fontSize = 22.sp) }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EditableNutrientCard(
                            label = "TŁUSZCZE",
                            value = fat,
                            onValueChange = onFatChange,
                            modifier = Modifier.weight(1f),
                        ) { Text("🫙", fontSize = 22.sp) }

                        EditableNutrientCard(
                            label = "WĘGLOWODANY",
                            value = carbs,
                            onValueChange = onCarbsChange,
                            modifier = Modifier.weight(1f),
                        ) { Text("🌾", fontSize = 22.sp) }
                    }

                    ActionButton(
                        onClick = onSaveProductClick,
                        label = "ZAPISZ PRODUKT",
                        icon = null,
                        style = ActionButtonStyle.LightFilled,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}