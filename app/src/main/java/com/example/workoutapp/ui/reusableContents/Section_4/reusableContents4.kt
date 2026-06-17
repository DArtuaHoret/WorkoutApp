package com.example.workoutapp.ui.reusableContents.Section_4

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.ui.res.stringResource
import com.example.workoutapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_1.WorkoutTextField

private val FieldShape    = RoundedCornerShape(14.dp)
private val FieldBgColor  = Color(0xFF1A1A1A)
private val BorderColor   = Color.White
private val LabelColor    = Color.White
private val PlaceHolderColor = Color(0xFF888888)


@Composable
fun ProductCard(
    productName: String,
    productDescription: String,
    onCardClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            )
            .clickable { onCardClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {


            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = productName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                if (productDescription.isNotBlank()) {
                    Text(
                        text = productDescription,
                        color = Color(0xFF888888),
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))


            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(36.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(
                        R.string.add_product_description,
                        productName
                    ),
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}



@Preview(name = "ProductCard – search field", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductSearchField() {
    var query by remember { mutableStateOf("") }
    MaterialTheme {
        WorkoutTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = "Wyszukaj produkt (np. jabłko)...",
            showSearchIcon = true,
            imeAction = ImeAction.Search,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "ProductCard – single", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductCardSingle() {
    MaterialTheme {
        ProductCard(
            productName = "Jabłko",
            productDescription = "Bogate w błonnik i witaminę C",
            onAddClick = {},
            onCardClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "ProductCard – list with search", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductCardList() {
    var query by remember { mutableStateOf("") }
    val products = listOf(
        Pair("Jabłko",         "Bogate w błonnik i witaminę C"),
        Pair("Łosoś pieczony", "Dobre źródło kwasów Omega-3"),
        Pair("Chleb żytni",    "Złożone węglowodany"),
        Pair("Banan",          "Dobre źródło potasu"),
    )
    val filtered = products.filter { it.first.contains(query, ignoreCase = true) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            WorkoutTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = stringResource(R.string.product_name_placeholder),
                showSearchIcon = true,
                imeAction = ImeAction.Search,
            )

            Spacer(modifier = Modifier.height(4.dp))

            filtered.forEach { (name, desc) ->
                ProductCard(
                    productName = name,
                    productDescription = desc,
                    onAddClick = {},
                    onCardClick = {},
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            ActionButton(
                onClick = {},
                label = "DODAJ SWÓJ PRODUKT",
                style = ActionButtonStyle.LightFilled,
            )
        }
    }
}




//-----------------------------------------------
//-----------------------------------------------
//--------------------------------------------------



@Composable
fun ProductDetailHeaderCard(
    productName: String,
    productDescription: String = "",
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = productName,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
            )

            if (productDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = productDescription,
                    color = Color(0xFFCCCCCC),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}


@Composable
fun NutrientCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    imageContent: @Composable BoxScope.() -> Unit = {},
) {
    Card(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center,
            ) {
                imageContent()
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = label,
                    color = Color(0xFFCCCCCC),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
                Text(
                    text = value,
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    }
}




@Preview(name = "ProductDetailHeaderCard", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewProductDetailHeaderCard() {
    MaterialTheme {
        ProductDetailHeaderCard(
            productName = "JABŁKO",
            productDescription = "Świeże, słodkie i chrupiące owoce, bogate\nw błonnik i witaminę C.",
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "NutrientCard – single", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewNutrientCardSingle() {
    MaterialTheme {
        NutrientCard(
            label = "BIAŁKO",
            value = "0.3 g",
            modifier = Modifier
                .padding(16.dp)
                .width(160.dp),
        ) { Text("💪", fontSize = 22.sp) }
    }
}

@Preview(name = "Full product detail screen", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFullProductDetail() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProductDetailHeaderCard(
                productName = "JABŁKO",
                productDescription = "Świeże, słodkie i chrupiące owoce, bogate\nw błonnik i witaminę C.",
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                NutrientCard(
                    label = "ENERGIA:",
                    value = "52 KCAL",
                    modifier = Modifier.weight(1f),
                ) { Text("⚡", fontSize = 22.sp) }

                NutrientCard(
                    label = "BIAŁKO",
                    value = "0.3 g",
                    modifier = Modifier.weight(1f),
                ) { Text("💪", fontSize = 22.sp) }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                NutrientCard(
                    label = "TŁUSZCZE",
                    value = "0.2 g",
                    modifier = Modifier.weight(1f),
                ) { Text("🫙", fontSize = 22.sp) }

                NutrientCard(
                    label = "WĘGLOWODANY",
                    value = "14 g",
                    modifier = Modifier.weight(1f),
                ) { Text("🌾", fontSize = 22.sp) }
            }

        }
    }
}




@Composable
fun EditableProductDetailHeaderCard(
    productName: String,
    onProductNameChange: (String) -> Unit,
    productDescription: String,
    onProductDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            WorkoutTextField(
                value = productName,
                onValueChange = onProductNameChange,
                placeholder = stringResource(R.string.product_name_placeholder),
            )
            WorkoutTextField(
                value = productDescription,
                onValueChange = onProductDescriptionChange,
                placeholder = stringResource(R.string.product_description_placeholder)
            )
        }
    }
}


@Composable
fun EditableNutrientCard(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imageContent: @Composable BoxScope.() -> Unit = {},
) {
    Card(
        modifier = modifier
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2C2C2C)),
                contentAlignment = Alignment.Center,
            ) {
                imageContent()
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = label,
                    color = Color(0xFFCCCCCC),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
                TextField(
                    value = value,
                    onValueChange = { input ->

                        if (input.all { it.isDigit() || it == '.' || it == ',' }) {
                            onValueChange(input)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = BorderColor, shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    placeholder = {
                        Text(text = "0", color = PlaceHolderColor, fontSize = 18.sp)
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next,
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor   = Color(0xFF2C2C2C),
                        unfocusedContainerColor = Color(0xFF2C2C2C),
                        focusedTextColor        = Color.White,
                        unfocusedTextColor      = Color.White,
                        cursorColor             = Color.White,
                        focusedIndicatorColor   = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                )
            }
        }
    }
}



@Preview(name = "EditableProductDetailHeaderCard – empty", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewEditableHeaderEmpty() {
    MaterialTheme {
        EditableProductDetailHeaderCard(
            productName = "",
            onProductNameChange = {},
            productDescription = "",
            onProductDescriptionChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "EditableProductDetailHeaderCard – filled", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewEditableHeaderFilled() {
    MaterialTheme {
        EditableProductDetailHeaderCard(
            productName = "Jabłko",
            onProductNameChange = {},
            productDescription = "Świeże, słodkie i chrupiące owoce.",
            onProductDescriptionChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "EditableNutrientCard – single", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewEditableNutrientSingle() {
    var value by remember { mutableStateOf("52") }
    MaterialTheme {
        EditableNutrientCard(
            label = "- ENERGIA:",
            value = value,
            onValueChange = { value = it },
            modifier = Modifier
                .padding(16.dp)
                .width(180.dp),
        ) { Text("⚡", fontSize = 22.sp) }
    }
}

@Preview(name = "Full editable product form", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFullEditableProductForm() {
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var kcal by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var fat by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            EditableProductDetailHeaderCard(
                productName = name,
                onProductNameChange = { name = it },
                productDescription = desc,
                onProductDescriptionChange = { desc = it },
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                EditableNutrientCard(
                    label = "ENERGIA:",
                    value = kcal,
                    onValueChange = { kcal = it },
                    modifier = Modifier.weight(1f),
                ) { Text("⚡", fontSize = 22.sp) }

                EditableNutrientCard(
                    label = "BIAŁKO",
                    value = protein,
                    onValueChange = { protein = it },
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
                    onValueChange = { fat = it },
                    modifier = Modifier.weight(1f),
                ) { Text("🫙", fontSize = 22.sp) }

                EditableNutrientCard(
                    label = "WĘGLOWODANY",
                    value = carbs,
                    onValueChange = { carbs = it },
                    modifier = Modifier.weight(1f),
                ) { Text("🌾", fontSize = 22.sp) }
            }

            ActionButton(
                onClick = {},
                label = "ZAPISZ PRODUKT",
                icon = null,
                style = ActionButtonStyle.LightFilled,
            )
        }
    }
}

@Composable
fun NutrientChip(
    label: String,
    value: String,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            color = Color(0xFF888888),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = value,
            color = Color(0xFFCCCCCC),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun FavoriteProductItemCard(
    productName: String,
    productDescription: String = "",
    kcal: String,
    protein: String,
    fat: String,
    carbs: String,
    isFavorite: Boolean = true,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onRemoveFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCustom: Boolean = false,
    onAddClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(14.dp))
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(14.dp))
            .clickable(onClick = onCardClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = productName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                if (productDescription.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = productDescription,
                        color = Color(0xFF888888),
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }

            if (isCustom) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_product_description),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }


            IconButton(
                onClick = onRemoveFavoriteClick,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorite_product_description),
                    tint = if (isFavorite) Color(0xFFFF4D4D) else Color(0xFF888888),
                    modifier = Modifier.size(20.dp),
                )
            }

        }

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                NutrientChip(label = "Kcal", value = kcal)
                NutrientChip(label = "Białko", value = "${protein}g")
                NutrientChip(label = "Tłuszcze", value = "${fat}g")
                NutrientChip(label = "Węgl.", value = "${carbs}g")
            }

            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(32.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp),
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_description),
                    tint = Color.White,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}
/*
@Preview(name = "FavoriteProductItemCard – with description", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFavoriteCardWithDesc() {
    MaterialTheme {
        FavoriteProductItemCard(
            productName = "Łosoś pieczony",
            productDescription = "Dobre źródło kwasów Omega-3",
            kcal = "208",
            protein = "20",
            fat = "13",
            carbs = "0",
            onCardClick = {},
            onRemoveFavoriteClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "FavoriteProductItemCard – no description", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewFavoriteCardNoDesc() {
    MaterialTheme {
        FavoriteProductItemCard(
            productName = "Jabłko",
            productDescription = "",
            kcal = "52",
            protein = "0.3",
            fat = "0.2",
            carbs = "14",
            onCardClick = {},
            onRemoveFavoriteClick = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

*/

@Composable
fun LibraryTabButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color.White else Color(0xFF1A1A1A),
            contentColor   = if (selected) Color.Black else Color.White,
        ),
        border = if (!selected) BorderStroke(2.dp, Color.White) else null,
    ) {
        Text(text = label, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDatePickerDialog(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis()
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                datePickerState.selectedDateMillis?.let { onDateSelected(it) }
            }) { Text(stringResource(R.string.ok)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Anuluj") }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
