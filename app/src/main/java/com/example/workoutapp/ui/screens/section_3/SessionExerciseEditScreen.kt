package com.example.workoutapp.ui.screens.section_3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButton
import com.example.workoutapp.ui.reusableContents.Section_1.ActionButtonStyle
import com.example.workoutapp.ui.reusableContents.Section_1.ExerciseSetCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionExerciseEditScreen(
    viewModel: SessionExerciseEditViewModel,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
    onDeleteExerciseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val exerciseName by viewModel.exerciseName.collectAsState()
    val sets by viewModel.sets.collectAsState()

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
                    contentDescription = "Powrót",
                    tint = Color.White
                )
            }
            Text(
                text = exerciseName,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(x = (-16).dp).weight(1f)
            )
            IconButton(onClick = {
                viewModel.deleteExercise { onDeleteExerciseClick() }
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Usuń ćwiczenie",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "Serie",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            itemsIndexed(
                items = sets,
                key = { index, _ -> index }
            ) { index, set ->
                ExerciseSetCard(
                    setNumber = index + 1,
                    weight = set.weight,
                    onWeightChange = {
                        viewModel.onSetChange(index, set.copy(weight = it))
                    },
                    reps = set.reps,
                    onRepsChange = {
                        viewModel.onSetChange(index, set.copy(reps = it))
                    },
                    rest = set.restTime,
                    onRestChange = {
                        viewModel.onSetChange(index, set.copy(restTime = it))
                    },
                    onDelete = if (sets.size > 1) ({ viewModel.onDeleteSet(index) }) else null
                )
            }

            item {
                ActionButton(
                    onClick = { viewModel.onAddSet() },
                    label = "DODAJ SERIĘ",
                    style = ActionButtonStyle.DarkOutlined
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ActionButton(
            onClick = { viewModel.save { onSaveClick() } },
            label = "ZAPISZ ZMIANY",
            icon = null,
            style = ActionButtonStyle.LightFilled
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}