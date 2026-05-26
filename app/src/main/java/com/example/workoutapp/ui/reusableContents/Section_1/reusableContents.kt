package com.example.workoutapp.ui.reusableContents.Section_1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.DirectionsRun

private val FieldShape    = RoundedCornerShape(14.dp)
private val FieldBgColor  = Color(0xFF1A1A1A)
private val BorderColor   = Color.White
private val LabelColor    = Color.White
private val PlaceHolderColor = Color(0xFF888888)


@Composable
fun TemplateCard(
    templateName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = templateName,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = ">",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}


@Preview(name = "TemplateCard", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTemplateCard() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TemplateCard(
                templateName = "PLAN NA MASE (P&P&L)",
                onClick = {},
            )

            TemplateCard(
                templateName = "PLAN NA REDUKCJĘ",
                onClick = {},
            )

            TemplateCard(
                templateName = "PLAN NA SIŁĘ (5x5) - A A A A A A A A A A A",
                onClick = {},
            )

            ActionButton(
                onClick = {},
                label = "DODAJ SZABLON",
                style = ActionButtonStyle.LightFilled,
            )
        }
    }
}

@Composable
fun AddExerciseCard(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonLabel: String = "DODAJ ĆWICZENIE",
) {
    Card(
        onClick = onAddClick,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 130.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            ),

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A1A),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(52.dp)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape,
                    ),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = buttonLabel,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = buttonLabel,
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun ExerciseItemCard(
    exerciseName: String,
    series: String,
    weight: String,
    restTime: String,
    note: String = "",
    imageContent: @Composable BoxScope.() -> Unit = { ExerciseImagePlaceholder() },
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1A1A1A), shape = RoundedCornerShape(14.dp))
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF2C2C2C)),
                contentAlignment = Alignment.Center,
            ) {
                imageContent()
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = exerciseName,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Row {
                        IconButton(onClick = onEditClick, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Edit, "Edytuj", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                        IconButton(onClick = onDeleteClick, modifier = Modifier.size(30.dp)) {
                            Icon(Icons.Default.Delete, "Usuń", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }

                Column  {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Serie: $series", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Ciężar: $weight", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Odpoczynek: $restTime", color = Color(0xFFCCCCCC), fontSize = 14.sp)
                }
            }
        }

        if (note.isNotBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = note, color = Color(0xFFCCCCCC), fontSize = 14.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}


@Composable
fun ExerciseImagePlaceholder() {
    Text(
        text = "🏋️",
        fontSize = 28.sp,
    )
}

@Preview(name = "AddExerciseCard – dark bg", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun AddExerciseCardPreviewDark() {
    MaterialTheme {
        AddExerciseCard(
            onAddClick = {},
            modifier = Modifier
                .padding(16.dp)
                .width(320.dp),
        )
    }
}

@Preview(name = "ExerciseItemCard – with note", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewWithNote() {
    MaterialTheme {
        ExerciseItemCard(
            exerciseName = "Martwy Ciąg z wynoszeniem rąk za głowe",
            series = "3 x 8",
            weight = "120 kg",
            restTime = "02:00",
            note = "Uważaj na technikęUważaj na technikę.Uważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikę.",
            modifier = Modifier.padding(12.dp),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun BothCardsPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AddExerciseCard(onAddClick = {})
        Spacer(modifier = Modifier.height(12.dp))
        ExerciseItemCard(
            exerciseName = "Martwy Ciąg z wynoszeniem rąk za głowe",
            series = "3 x 8",
            weight = "120 kg",
            restTime = "02:00",
            note = "Uważaj na technikę.Uważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikęUważaj na technikę"
        )
    }
}

@Composable
fun WorkoutTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "np. Pompki",
    showSearchIcon: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .border(width = 2.dp, color = BorderColor, shape = FieldShape),
            shape = FieldShape,
            placeholder = {
                Text(text = placeholder, color = PlaceHolderColor, fontSize = 14.sp)
            },
            leadingIcon = if (showSearchIcon) {
                {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Szukaj",
                        tint = PlaceHolderColor,
                        modifier = Modifier.size(20.dp),
                    )
                }
            } else null,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onImeAction()
                },
                onSearch = {
                    focusManager.clearFocus()
                    onImeAction()
                },
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor   = FieldBgColor,
                unfocusedContainerColor = FieldBgColor,
                focusedTextColor        = Color.White,
                unfocusedTextColor      = Color.White,
                cursorColor             = Color.White,
                focusedIndicatorColor   = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
}

@Preview(name = "WorkoutTextField – name input (no icon)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTextFieldName() {
    MaterialTheme {
        WorkoutTextField(
            value = "",
            onValueChange = {},
            placeholder = "np. Pompki",
            showSearchIcon = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "WorkoutTextField – search mode (with icon)", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTextFieldSearch() {
    var query by remember { mutableStateOf("") }

    MaterialTheme {
        WorkoutTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = "Szukaj ćwiczenia...",
            showSearchIcon = true,
            imeAction = ImeAction.Search,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "WorkoutTextField – with text filled", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTextFieldFilled() {
    MaterialTheme {
        WorkoutTextField(
            value = "Martwy Ciąg",
            onValueChange = {},
            placeholder = "np. Pompki",
            showSearchIcon = false,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Composable
fun ExerciseSelectItem(
    exerciseName: String,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageContent: @Composable BoxScope.() -> Unit = { ExerciseImagePlaceholder() },
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(14.dp),
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2C2C2C)),
            contentAlignment = Alignment.Center,
        ) {
            imageContent()
        }

        Text(
            text = exerciseName,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .size(56.dp)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Dodaj $exerciseName",
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}



@Preview(name = "ExerciseSelectItem", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSelectItem() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ExerciseSelectItem(exerciseName = "Martwy Ciąg", onAddClick = {})
            ExerciseSelectItem(exerciseName = "Podciąganie z obciążeniem", onAddClick = {})
            ExerciseSelectItem(exerciseName = "Wyciskanie sztangi na ławce poziomej", onAddClick = {})
        }
    }
}

@Preview(name = "Search + Exercise List", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewSearchWithList() {
    var query by remember { mutableStateOf("") }
    val all = listOf("Martwy Ciąg", "Podciąganie z obciążeniem", "Wyciskanie na ławce", "Przysiad ze sztangą", "Wiosłowanie")
    val filtered = all.filter { it.contains(query, ignoreCase = true) }

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
                placeholder = "Szukaj ćwiczenia...",
                showSearchIcon = true,
                imeAction = ImeAction.Search,
            )
            Spacer(modifier = Modifier.height(4.dp))
            filtered.forEach { name ->
                ExerciseSelectItem(exerciseName = name, onAddClick = {})
            }
        }
    }
}


enum class ActionButtonStyle {
    DarkOutlined,
    LightFilled,
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = Icons.Default.Add,
    height: Dp = 52.dp,
    style: ActionButtonStyle = ActionButtonStyle.DarkOutlined,
) {

    val containerColor =
        if (style == ActionButtonStyle.LightFilled) {
            Color.White
        } else {
            Color(0xFF1A1A1A)
        }

    val contentColor =
        if (style == ActionButtonStyle.LightFilled) {
            Color.Black
        } else {
            Color.White
        }

    val borderColor =
        if (style == ActionButtonStyle.LightFilled) {
            Color.Transparent
        } else {
            Color.White
        }

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(14.dp),
            ),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {

        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = contentColor,
        )
    }
}

@Preview(name = "ActionButton - Add Exercise", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewActionButtonAddExercise() {
    MaterialTheme {
        ActionButton(
            onClick = {},
            label = "DODAJ WŁASNE ĆWICZENIE",
            modifier = Modifier.padding(16.dp),
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MuscleGroupSelector(
    selectedGroups: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Główne Mięśnie:",
    allGroups: List<String> = defaultMuscleGroups,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = LabelColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(6.dp))

        Box {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 2.dp, color = BorderColor, shape = FieldShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (selectedGroups.isEmpty()) {
                        Text(
                            text = "Wybierz grupy mięśni...",
                            color = PlaceHolderColor,
                            fontSize = 14.sp,
                        )
                    } else {
                        selectedGroups.forEach { group ->
                            SuggestionChip(
                                onClick = {
                                    onSelectionChange(selectedGroups - group)
                                },
                                label = {
                                    Text(
                                        text = group,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = Color(0xFF2C2C2C),
                                ),
                                border = SuggestionChipDefaults.suggestionChipBorder(
                                    enabled = true,
                                    borderColor = Color.White,
                                    borderWidth = 1.dp,
                                ),
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Rozwiń",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp),
                    )
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color(0xFF1A1A1A))
                    .border(width = 1.dp, color = BorderColor, shape = RoundedCornerShape(10.dp)),
            ) {
                allGroups.forEach { group ->
                    val isChecked = group in selectedGroups
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = group,
                                color = Color.White,
                                fontSize = 14.sp,
                            )
                        },
                        leadingIcon = {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color.White,
                                    uncheckedColor = Color(0xFF888888),
                                    checkmarkColor = Color.Black,
                                ),
                            )
                        },
                        onClick = {
                            val updated = if (isChecked) selectedGroups - group
                            else selectedGroups + group
                            onSelectionChange(updated)
                        },
                        colors = MenuDefaults.itemColors(
                            textColor = Color.White,
                        ),
                    )
                }
            }
        }
    }
}

/** Domyślna lista grup mięśniowych — podmień na dane z Room gdy będą gotowe. */
val defaultMuscleGroups = listOf(
    "Klatka", "Plecy", "Barki", "Biceps", "Triceps",
    "Brzuch", "Nogi", "Pośladki", "Łydki",
)



@Preview(name = "MuscleGroupSelector – empty", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewMuscleGroupEmpty() {
    MaterialTheme {
        MuscleGroupSelector(
            selectedGroups = emptySet(),
            onSelectionChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "MuscleGroupSelector – selected", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewMuscleGroupSelected() {
    MaterialTheme {
        MuscleGroupSelector(
            selectedGroups = setOf("Klatka", "Triceps"),
            onSelectionChange = {},
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(name = "Form example – both fields together", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewBothFields() {
    var name by remember { mutableStateOf("") }
    var muscles by remember { mutableStateOf(setOf("Klatka", "Triceps")) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            WorkoutTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "np. Pompki",
                showSearchIcon = false,
            )
            MuscleGroupSelector(
                selectedGroups = muscles,
                onSelectionChange = { muscles = it },
            )
        }
    }
}



@Composable
fun AdjustableRow(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    step: Int = 1,
    unit: String = "",
    minValue: Int = 0,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            color = Color(0xFFCCCCCC),
            fontSize = 12.sp,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Row(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 6.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {


                IconButton(
                    onClick = {
                        if (value > minValue) {
                            onValueChange(value - step)
                        }
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Text(
                        text = "−",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


                Text(
                    text = when {
                        unit == "s" -> {
                            val minutes = value / 60
                            val seconds = value % 60
                            String.format("%02d:%02d", minutes, seconds)
                        }

                        unit == "kg" -> "$value kg"

                        else -> value.toString()
                    },
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )


                IconButton(
                    onClick = {
                        onValueChange(value + step)
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Text(
                        text = "+",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseSetCard(
    setNumber: Int,
    weight: Int,
    onWeightChange: (Int) -> Unit,
    reps: Int,
    onRepsChange: (Int) -> Unit,
    rest: Int,
    onRestChange: (Int) -> Unit,
    onDelete: (() -> Unit)? = null,
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
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "SERIA $setNumber",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )

                if (onDelete != null) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Usuń",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }


            AdjustableRow(
                label = "CIĘŻAR",
                value = weight,
                onValueChange = onWeightChange,
                step = 5,
                unit = "kg",
            )


            AdjustableRow(
                label = "POWTÓRZENIA",
                value = reps,
                onValueChange = onRepsChange,
                step = 1,
                unit = "",
                minValue = 1,
            )


            AdjustableRow(
                label = "ODPOCZYNEK",
                value = rest,
                onValueChange = onRestChange,
                step = 15,
                unit = "s",
            )
        }
    }
}


@Preview(name = "ExerciseSetCard", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSetCard() {
    var weight by remember { mutableStateOf(60) }
    var reps by remember { mutableStateOf(8) }
    var rest by remember { mutableStateOf(60) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ExerciseSetCard(
                setNumber = 1,
                weight = weight,
                onWeightChange = { weight = it },
                reps = reps,
                onRepsChange = { reps = it },
                rest = rest,
                onRestChange = { rest = it },
            )
        }
    }
}

@Preview(name = "ExerciseSetCard with delete", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewExerciseSetCardWithDelete() {
    var weight by remember { mutableStateOf(80) }
    var reps by remember { mutableStateOf(5) }
    var rest by remember { mutableStateOf(90) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ExerciseSetCard(
                setNumber = 2,
                weight = weight,
                onWeightChange = { weight = it },
                reps = reps,
                onRepsChange = { reps = it },
                rest = rest,
                onRestChange = { rest = it },
                onDelete = {},
            )
        }
    }
}

@Preview(name = "Multiple sets", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewMultipleSets() {
    var set1Weight by remember { mutableStateOf(60) }
    var set1Reps by remember { mutableStateOf(8) }
    var set1Rest by remember { mutableStateOf(60) }

    var set2Weight by remember { mutableStateOf(70) }
    var set2Reps by remember { mutableStateOf(6) }
    var set2Rest by remember { mutableStateOf(90) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ExerciseSetCard(
                setNumber = 1,
                weight = set1Weight,
                onWeightChange = { set1Weight = it },
                reps = set1Reps,
                onRepsChange = { set1Reps = it },
                rest = set1Rest,
                onRestChange = { set1Rest = it },
            )

            ExerciseSetCard(
                setNumber = 2,
                weight = set2Weight,
                onWeightChange = { set2Weight = it },
                reps = set2Reps,
                onRepsChange = { set2Reps = it },
                rest = set2Rest,
                onRestChange = { set2Rest = it },
                onDelete = {},
            )

                    ActionButton(
                        onClick = {},
                        label = "DODAJ SERIĘ",
                    )
            ActionButton(
                onClick = {},
                label = "ZAPISZ ĆWICZENIE",
                icon = null,
                style = ActionButtonStyle.LightFilled,
            )

        }
    }
}

