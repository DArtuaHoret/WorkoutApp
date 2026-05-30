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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseSearchScreen(
    exercises: List<ExerciseOption>,
    onBackClick: () -> Unit,
    onAddExercise: (ExerciseOption) -> Unit,
    onAddCustomExercise: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf("") }

    val filtered = remember(query, exercises) {
        if (query.isBlank()) exercises
        else exercises.filter { it.name.contains(query, ignoreCase = true) }
    }

    // Group by muscle group; preserve insertion order
    val grouped = remember(filtered) {
        filtered.groupBy { it.muscleGroup }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Wyszukiwanie ćwiczeń",
                        color = Color.White,
                        fontSize = 22.sp,
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
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
            ) {
                ActionButton(
                    onClick = onAddCustomExercise,
                    label = "DODAJ WŁASNE ĆWICZENIE",
                    icon = Icons.Default.Add,
                    style = ActionButtonStyle.DarkOutlined,
                )
            }
        },
        containerColor = Color.Black,
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            // Search field
            item {
                WorkoutTextField(
                    value = query,
                    onValueChange = { query = it },
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
                            fontSize = 15.sp
                        )
                    }
                }
            } else {
                grouped.forEach { (muscleGroup, groupExercises) ->
                    // Muscle group header
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

                    // Exercises in this group
                    items(
                        items = groupExercises,
                        key = { it.id },
                    ) { exercise ->
                        ExerciseSelectItem(
                            exerciseName = exercise.name,
                            onAddClick = { onAddExercise(exercise) },
                        )
                    }
                }
            }

            // Bottom spacing so last item isn't hidden behind bottom bar
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}
