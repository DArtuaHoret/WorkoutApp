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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.R
import com.example.workoutapp.ui.reusableContents.Section_1.*

data class ExerciseOption(
    val id: String,
    val name: String,
    val muscleGroup: String,
    val isCustom: Boolean = false,
    val photoUrl: String? = null,
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
    val showOnlyCustom    by viewModel.showOnlyCustom.collectAsState()

    val grouped = remember(filteredExercises) {
        filteredExercises.groupBy { it.muscleGroup }
    }

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
                text = stringResource(R.string.exercise_search_title),
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .offset(x = (-16).dp)
                    .weight(1f),
            )
            FilterChip(
                selected = showOnlyCustom,
                onClick = viewModel::onToggleShowOnlyCustom,
                label = {
                    Text(
                        text = stringResource(R.string.exercise_search_filter_custom),
                        fontSize = 13.sp,
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF3D3D3D),
                    selectedLabelColor = Color.White,
                    containerColor = Color(0xFF1E1E1E),
                    labelColor = Color(0xFF888888),
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = showOnlyCustom,
                    borderColor = Color(0xFF3D3D3D),
                    selectedBorderColor = Color(0xFF555555),
                ),
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            item {
                WorkoutTextField(
                    value = query,
                    onValueChange = viewModel::onQueryChange,
                    placeholder = stringResource(R.string.exercise_search_placeholder),
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
                            text = if (showOnlyCustom) {
                                stringResource(R.string.exercise_search_empty_custom, query)
                            } else {
                                stringResource(R.string.exercise_search_empty, query)
                            },
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
                        Box {
                            ExerciseSelectItem(
                                exerciseName = exercise.name,
                                photoUrl = exercise.photoUrl,
                                onAddClick = { onExerciseClick(exercise) },
                            )
                            if (!exercise.isCustom) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 4.dp, end = 5.dp)
                                        .background(
                                            color = Color(0xFF3D3D3D),
                                            shape = RoundedCornerShape(6.dp),
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = Color(0xFF555555),
                                            shape = RoundedCornerShape(6.dp),
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp),
                                ) {
                                    Text(
                                        text = stringResource(R.string.exercise_search_custom_badge),
                                        color = Color(0xFFCCCCCC),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }

        Spacer(modifier = Modifier.height(12.dp))
        ActionButton(
            onClick = onAddCustomExercise,
            label = stringResource(R.string.add_custom_exercise),
            icon = Icons.Default.Add,
            style = ActionButtonStyle.DarkOutlined,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}