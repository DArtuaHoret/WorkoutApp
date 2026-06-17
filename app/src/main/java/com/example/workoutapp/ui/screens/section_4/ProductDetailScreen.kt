package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_4.*
import androidx.compose.foundation.shape.RoundedCornerShape

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
    data class View(val args: ProductDetailArgs) : ProductDetailMode
    data object Create : ProductDetailMode
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = viewModel(factory = WorkoutAppViewModelProvider.Factory),
    onBackClick: () -> Unit,
    onSaveProductClick: () -> Unit = {},
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
                text = when (uiState) {
                    is ProductDetailUiState.Create -> stringResource(R.string.product_detail_title_create)
                    is ProductDetailUiState.View   -> stringResource(R.string.product_detail_title_view)
                },
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )

            if (uiState is ProductDetailUiState.View) {
                Spacer(modifier = Modifier.weight(1f))
                val viewState = uiState as ProductDetailUiState.View
                IconButton(onClick = viewModel::onFavoriteClick) {
                    Icon(
                        imageVector = if (viewState.isFavorite)
                            Icons.Default.Favorite
                        else
                            Icons.Default.FavoriteBorder,
                        contentDescription = stringResource(R.string.product_detail_favorite),
                        tint = if (viewState.isFavorite) Color(0xFFFF4D4D) else Color.White,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            when (val state = uiState) {
                is ProductDetailUiState.View -> {
                    val args = state.args

                    ProductDetailHeaderCard(
                        productName = args.name,
                        productDescription = args.description,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        NutrientCard(
                            label = stringResource(R.string.nutrient_energy),
                            value = "${args.kcal} KCAL",
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("⚡", fontSize = 22.sp)
                        }
                        NutrientCard(
                            label = stringResource(R.string.nutrient_protein),
                            value = "${args.protein} g",
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("💪", fontSize = 22.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        NutrientCard(
                            label = stringResource(R.string.nutrient_fat),
                            value = "${args.fat} g",
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("🫙", fontSize = 22.sp)
                        }
                        NutrientCard(
                            label = stringResource(R.string.nutrient_carbs),
                            value = "${args.carbs} g",
                            modifier = Modifier.weight(1f),
                        ) {
                            Text("🌾", fontSize = 22.sp)
                        }
                    }

                    Text(
                        text = "canDelete: ${state.canDelete}",
                        color = Color.Red
                    )
                    if (state.canDelete) {

                        ActionButton(
                            onClick = { viewModel.deleteProduct { onBackClick() } },
                            label = stringResource(R.string.product_detail_delete),
                            icon = Icons.Default.Delete,
                            style = ActionButtonStyle.DangerFilled,
                        )
                    }
                }

                is ProductDetailUiState.Create -> {
                    EditableProductDetailHeaderCard(
                        productName = state.productName,
                        onProductNameChange = viewModel::onProductNameChange,
                        productDescription = state.productDescription,
                        onProductDescriptionChange = viewModel::onProductDescriptionChange,
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EditableNutrientCard(
                            label = stringResource(R.string.nutrient_energy),
                            value = state.kcal,
                            onValueChange = viewModel::onKcalChange,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.nutrient_icon_energy), fontSize = 22.sp)
                        }
                        EditableNutrientCard(
                            label = stringResource(R.string.nutrient_protein),
                            value = state.protein,
                            onValueChange = viewModel::onProteinChange,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.nutrient_icon_protein), fontSize = 22.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EditableNutrientCard(
                            label = stringResource(R.string.nutrient_fat),
                            value = state.fat,
                            onValueChange = viewModel::onFatChange,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.nutrient_icon_fat), fontSize = 22.sp)
                        }
                        EditableNutrientCard(
                            label = stringResource(R.string.nutrient_carbs),
                            value = state.carbs,
                            onValueChange = viewModel::onCarbsChange,
                            modifier = Modifier.weight(1f),
                        ) {
                            Text(stringResource(R.string.nutrient_icon_carbs), fontSize = 22.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ActionButton(
                            onClick = {
                                viewModel.onSaveProductClick()
                                onSaveProductClick()
                            },
                            label = stringResource(R.string.product_detail_save),
                            style = ActionButtonStyle.LightFilled,
                            modifier = Modifier.weight(1f),
                        )
                        IconButton(
                            onClick = viewModel::onToggleCreateFavorite,
                            modifier = Modifier
                                .size(52.dp)
                                .border(2.dp, Color.White, RoundedCornerShape(14.dp)),
                        ) {
                            Icon(
                                imageVector = if (state.isFavorite)
                                    Icons.Default.Favorite
                                else
                                    Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(R.string.product_detail_favorite),
                                tint = if (state.isFavorite) Color(0xFFFF4D4D) else Color.White,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}