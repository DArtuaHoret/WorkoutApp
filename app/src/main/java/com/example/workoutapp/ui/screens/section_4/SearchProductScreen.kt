package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutapp.R
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_1.WorkoutTextField
import com.example.workoutapp.ui.reusableContents.Section_4.MealDatePickerDialog
import com.example.workoutapp.ui.reusableContents.Section_4.ProductCard

data class ProductSearchItem(
    val id: String,
    val name: String,
    val description: String,
    val kcal: String = "",
    val protein: String = "",
    val fat: String = "",
    val carbs: String = "",
)

@Composable
fun AddMealSearchScreen(
    viewModel: AddMealSearchViewModel = viewModel(),
    onProductCardClick: (ProductSearchItem) -> Unit,
    onProductQuickAddClick: (ProductSearchItem, Long) -> Unit,
    onScanBarcodeClick: () -> Unit,
    onAddCustomProductClick: () -> Unit,
    onLibraryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()



    if (showDatePicker) {
        MealDatePickerDialog(
            onDateSelected = { millis ->
                viewModel.confirmDateAndAddProduct(millis)
            },
            onDismiss = {
                viewModel.dismissDatePicker()
            }
        )
    }

    val searchResults = when (val state = uiState) {
        is AddMealSearchUiState.Success -> state.results
        else -> emptyList()
    }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.diet_title),
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp),
        )

        WorkoutTextField(
            value = query,
            onValueChange = viewModel::onQueryChange,
            placeholder = stringResource(R.string.diet_search_placeholder),
            showSearchIcon = true,
            imeAction = ImeAction.Search,
        )

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = onScanBarcodeClick,
            label = stringResource(R.string.diet_scan_barcode),
            icon = Icons.Default.QrCodeScanner,
            style = ActionButtonStyle.DarkOutlined,
        )

        Spacer(modifier = Modifier.height(8.dp))

        ActionButton(
            onClick = onLibraryClick,
            label = stringResource(R.string.diet_library),
            icon = Icons.Default.Bookmarks,
            style = ActionButtonStyle.DarkOutlined,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.weight(1f)) {
            when (uiState) {
                is AddMealSearchUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.diet_loading),
                            color = Color(0xFF888888),
                            fontSize = 14.sp,
                        )
                    }
                }

                is AddMealSearchUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = (uiState as AddMealSearchUiState.Error).message,
                            color = Color(0xFF888888),
                            fontSize = 14.sp,
                        )
                    }
                }

                is AddMealSearchUiState.Success -> {
                    if (searchResults.isEmpty() && query.isNotBlank()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.diet_no_results_for,
                                    query
                                ),
                                color = Color(0xFF888888),
                                fontSize = 14.sp,
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 8.dp),
                        ) {
                            items(items = searchResults, key = { it.id }) { product ->
                                ProductCard(
                                    productName = product.name,
                                    productDescription = product.description,
                                    onCardClick = { onProductCardClick(product) },
                                    onAddClick = {
                                        viewModel.showDatePickerForProduct(product)
                                    }
                                )
                            }
                        }
                    }
                }

                else -> Unit
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = onAddCustomProductClick,
            label = stringResource(R.string.diet_add_custom),
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
