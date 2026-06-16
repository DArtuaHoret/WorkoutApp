package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutapp.R
import com.example.workoutapp.WorkoutAppViewModelProvider
import com.example.workoutapp.ui.reusableContents.Section_4.FavoriteProductItemCard
import com.example.workoutapp.ui.reusableContents.Section_4.LibraryTabButton

data class FavoriteProductItem(
    val id: String,
    val name: String,
    val description: String,
    val kcal: String,
    val protein: String,
    val fat: String,
    val carbs: String,
    val isFavorite: Boolean = false,
    val isCustom: Boolean = false,
)

@Composable
fun FavoriteProductsScreen(
    viewModel: FavoriteProductsViewModel = viewModel(factory = WorkoutAppViewModelProvider.Factory),
    onBackClick: () -> Unit,
    onProductClick: (FavoriteProductItem) -> Unit,
    onEditClick: (FavoriteProductItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.offset(x = (-12).dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color.White,
                )
            }
            Text(
                text = stringResource(R.string.library_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-8).dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            LibraryTabButton(
                label = stringResource(R.string.library_tab_favorites),
                selected = uiState is FavoriteProductsUiState.Favorites,
                onClick = { viewModel.onShowFavorites() },
                modifier = Modifier.weight(1f),
            )
            LibraryTabButton(
                label = stringResource(R.string.library_tab_my_products),
                selected = uiState is FavoriteProductsUiState.MyProducts,
                onClick = { viewModel.onShowMyProducts() },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val currentList = when (val state = uiState) {
            is FavoriteProductsUiState.Favorites  -> state.favorites
            is FavoriteProductsUiState.MyProducts -> state.myProducts
        }

        if (currentList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = if (uiState is FavoriteProductsUiState.Favorites)
                            stringResource(R.string.library_empty_favorites_emoji)
                        else
                            stringResource(R.string.library_empty_my_products_emoji),
                        fontSize = 48.sp,
                    )
                    Text(
                        text = if (uiState is FavoriteProductsUiState.Favorites)
                            stringResource(R.string.library_empty_favorites)
                        else
                            stringResource(R.string.library_empty_my_products),
                        color = Color(0xFF888888),
                        fontSize = 16.sp,
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
            ) {
                items(items = currentList, key = { it.id }) { product ->
                    FavoriteProductItemCard(
                        productName = product.name,
                        productDescription = product.description,
                        kcal = product.kcal,
                        protein = product.protein,
                        fat = product.fat,
                        carbs = product.carbs,
                        isFavorite = product.isFavorite,
                        isCustom = product.isCustom,
                        onCardClick = { onProductClick(product) },
                        onEditClick = { onEditClick(product) },
                        onRemoveFavoriteClick = { viewModel.onToggleFavorite(product) },
                        onAddClick = { /* TODO */ }
                    )
                }
            }
        }
    }
}