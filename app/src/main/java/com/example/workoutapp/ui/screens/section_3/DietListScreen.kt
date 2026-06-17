package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.workoutapp.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_3.LoggedProductItemCard

data class LoggedProductItem(
    val id: String,
    val name: String,
    val description: String = "",
    val kcal: String,
    val protein: String,
    val fat: String,
    val carbs: String,
    val grams: String = "100",
)

@Composable
fun MealDetailsScreen(
    viewModel: MealDetailsViewModel,
    onBackClick: () -> Unit,
    onProductClick: (LoggedProductItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    MealDetailsContent(
        products = uiState.products,
        dateLabel = uiState.dateLabel,
        onBackClick = onBackClick,
        onProductClick = onProductClick,
        onDeleteClick = viewModel::onDeleteProduct,
        onGramsChange = { product, grams -> viewModel.onGramsChanged(product, grams) },
        modifier = modifier,
    )
}

@Composable
private fun MealDetailsContent(
    products: List<LoggedProductItem>,
    dateLabel: String,
    onBackClick: () -> Unit,
    onProductClick: (LoggedProductItem) -> Unit,
    onDeleteClick: (LoggedProductItem) -> Unit,
    onGramsChange: (LoggedProductItem, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
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
                    contentDescription = stringResource(R.string.close_description),
                    tint = Color.White,
                )
            }

            Text(
                text = stringResource(R.string.meal_details_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-8).dp),
            )

        }

        if (dateLabel.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = dateLabel,
                color = Color(0xFFAAAAAA),
                fontSize = 16.sp,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (products.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.meal_details_empty_emoji),
                        fontSize = 48.sp
                    )

                    Text(
                        text = stringResource(R.string.meal_details_empty_text),
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
                items(items = products, key = { it.id }) { product ->
                    LoggedProductItemCard(
                        productName        = product.name,
                        productDescription = product.description,
                        kcal               = product.kcal,
                        protein            = product.protein,
                        fat                = product.fat,
                        carbs              = product.carbs,
                        grams              = product.grams,
                        onCardClick        = { onProductClick(product) },
                        onDeleteClick      = { onDeleteClick(product) },
                        onGramsChange      = { newGrams -> onGramsChange(product, newGrams) },
                    )
                }
            }
        }
    }
}