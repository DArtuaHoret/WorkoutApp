package com.example.workoutapp.ui.screens.section_4

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_1.WorkoutTextField
import com.example.workoutapp.ui.reusableContents.Section_4.ProductCard

data class ProductSearchItem(
    val id: String,
    val name: String,
    val description: String,
)

@Composable
fun AddMealSearchScreen(
    query: String,
    onQueryChange: (String) -> Unit,
    searchResults: List<ProductSearchItem>,
    onProductAddClick: (ProductSearchItem) -> Unit,
    onAddCustomProductClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        WorkoutTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = "Wyszukaj produkt (np. jabłko)...",
            showSearchIcon = true,
            imeAction = ImeAction.Search,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Results list or empty state
        Box(
            modifier = Modifier.weight(1f),
        ) {
            if (query.isNotBlank() && searchResults.isEmpty()) {
                // Empty results state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Brak wyników dla „$query”",
                        color = Color(0xFF888888),
                        fontSize = 14.sp,
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 8.dp),
                ) {
                    items(
                        items = searchResults,
                        key = { it.id },
                    ) { product ->
                        ProductCard(
                            productName = product.name,
                            productDescription = product.description,
                            onAddClick = { onProductAddClick(product) },
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = onAddCustomProductClick,
            label = "DODAJ SWÓJ PRODUKT",
            style = ActionButtonStyle.LightFilled,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}