package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.border
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutapp.WorkoutAppViewModelProvider
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_4.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

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
        Spacer(modifier = Modifier.height(24.dp))

        // Nagłówek z przyciskiem wstecz
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick,  modifier = Modifier.offset(x = (-12).dp),) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Wróć",
                    tint = Color.White,
                )
            }

            Text(
                text = when (uiState) {
                    is ProductDetailUiState.Create -> "Dodaj produkt"
                    is ProductDetailUiState.View   -> "Szczegóły produktu"
                },

                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp)
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
                        contentDescription = null,
                        tint = if (viewState.isFavorite) Color(0xFFFF4D4D) else Color.White,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Treść
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
                        NutrientCard(label = "ENERGIA", value = "${args.kcal} KCAL", modifier = Modifier.weight(1f)) {
                            Text("⚡", fontSize = 22.sp)
                        }
                        NutrientCard(label = "BIAŁKO", value = "${args.protein} g", modifier = Modifier.weight(1f)) {
                            Text("💪", fontSize = 22.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        NutrientCard(label = "TŁUSZCZE", value = "${args.fat} g", modifier = Modifier.weight(1f)) {
                            Text("🫙", fontSize = 22.sp)
                        }
                        NutrientCard(label = "WĘGLOWODANY", value = "${args.carbs} g", modifier = Modifier.weight(1f)) {
                            Text("🌾", fontSize = 22.sp)
                        }
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
                        EditableNutrientCard(label = "ENERGIA", value = state.kcal, onValueChange = viewModel::onKcalChange, modifier = Modifier.weight(1f)) {
                            Text("⚡", fontSize = 22.sp)
                        }
                        EditableNutrientCard(label = "BIAŁKO", value = state.protein, onValueChange = viewModel::onProteinChange, modifier = Modifier.weight(1f)) {
                            Text("💪", fontSize = 22.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        EditableNutrientCard(label = "TŁUSZCZE", value = state.fat, onValueChange = viewModel::onFatChange, modifier = Modifier.weight(1f)) {
                            Text("🫙", fontSize = 22.sp)
                        }
                        EditableNutrientCard(label = "WĘGLOWODANY", value = state.carbs, onValueChange = viewModel::onCarbsChange, modifier = Modifier.weight(1f)) {
                            Text("🌾", fontSize = 22.sp)
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
                            label = "ZAPISZ PRODUKT",
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
                                imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Ulubione",
                                tint = if (state.isFavorite) Color(0xFFFF4D4D) else Color.White,
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
