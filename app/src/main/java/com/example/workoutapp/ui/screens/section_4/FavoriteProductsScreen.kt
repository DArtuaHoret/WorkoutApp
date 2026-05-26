package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_4.FavoriteProductItemCard

data class FavoriteProductItem(
    val id: String,
    val name: String,
    val description: String,
    val kcal: String,
    val protein: String,
    val fat: String,
    val carbs: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteProductsScreen(
    favoriteProducts: List<FavoriteProductItem>,
    onBackClick: () -> Unit,
    onProductClick: (FavoriteProductItem) -> Unit,
    onRemoveFavoriteClick: (FavoriteProductItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ulubione produkty",
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                ),
            )
        },
        containerColor = Color.Black,
    ) { innerPadding ->
        if (favoriteProducts.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = "🤍", fontSize = 48.sp)
                    Text(
                        text = "Brak ulubionych produktów",
                        color = Color(0xFF888888),
                        fontSize = 16.sp,
                    )
                    Text(
                        text = "Dodaj produkt do ulubionych,\nprzeglądając jego szczegóły.",
                        color = Color(0xFF555555),
                        fontSize = 13.sp,
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {
                items(
                    items = favoriteProducts,
                    key = { it.id },
                ) { product ->
                    FavoriteProductItemCard(
                        productName = product.name,
                        productDescription = product.description,
                        kcal = product.kcal,
                        protein = product.protein,
                        fat = product.fat,
                        carbs = product.carbs,
                        onCardClick = { onProductClick(product) },
                        onRemoveFavoriteClick = { onRemoveFavoriteClick(product) },
                    )
                }
            }
        }
    }
}


