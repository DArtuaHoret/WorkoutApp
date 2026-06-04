package com.example.workoutapp.ui.screens.section_1



import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.*


data class ExerciseOption(
    val id: String,
    val name: String,
    val muscleGroup: String,
)

@Composable
fun ExerciseSearchScreen(
    viewModel: ExerciseSearchViewModel,
    onBackClick: () -> Unit,
    onExerciseClick: (ExerciseOption) -> Unit,
    onAddCustomExercise: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val query             by viewModel.query.collectAsState()
    val filteredExercises by viewModel.filteredExercises.collectAsState()

    val grouped = remember(filteredExercises) {
        filteredExercises.groupBy { it.muscleGroup }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Nagłówek
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
                    contentDescription = "Wróć",
                    tint = Color.White,
                )
            }
            Text(
                text = "Wyszukiwanie ćwiczeń",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Lista
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            item {
                WorkoutTextField(
                    value = query,
                    onValueChange = viewModel::onQueryChange,
                    placeholder = "np. Przysiad, Wyciskanie",
                    showSearchIcon = true,
                )
            }

            if (grouped.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Brak wyników dla \"$query\"",
                            color = Color(0xFF888888),
                            fontSize = 15.sp,
                        )
                    }
                }
            } else {
                grouped.forEach { (muscleGroup, groupExercises) ->
                    item(key = "header_$muscleGroup") {
                        Text(
                            text = muscleGroup.uppercase(),
                            color = Color(0xFF888888),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5.sp,
                            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        )
                    }
                    items(
                        items = groupExercises,
                        key = { it.id },
                    ) { exercise ->
                        ExerciseSelectItem(
                            exerciseName = exercise.name,
                            onAddClick = { onExerciseClick(exercise) },
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        // Przycisk na dole
        Spacer(modifier = Modifier.height(12.dp))
        ActionButton(
            onClick = onAddCustomExercise,
            label = "DODAJ WŁASNE ĆWICZENIE",
            icon = Icons.Default.Add,
            style = ActionButtonStyle.DarkOutlined,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

