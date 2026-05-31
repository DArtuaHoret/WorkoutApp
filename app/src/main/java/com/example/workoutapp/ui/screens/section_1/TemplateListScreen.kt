package com.example.workoutapp.ui.screens.section_1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutapp.ui.reusableContents.Section_1.*

data class WorkoutTemplate(
    val id: String,
    val name: String,
)

@Composable
fun TemplateListScreen(
    templates: List<WorkoutTemplate>,
    onTemplateClick: (WorkoutTemplate) -> Unit,
    onCreateNewClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        containerColor = Color.Black,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 24.dp),
            ) {
                item {
                    Text(
                        text = "Szablony",
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp),
                    )
                }

                if (templates.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 60.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(text = "📋", fontSize = 48.sp)
                                Text(
                                    text = "Brak szablonów",
                                    color = Color(0xFF888888),
                                    fontSize = 16.sp,
                                )
                                Text(
                                    text = "Utwórz swój pierwszy plan treningowy.",
                                    color = Color(0xFF555555),
                                    fontSize = 13.sp,
                                )
                            }
                        }
                    }
                } else {
                    items(
                        items = templates,
                        key = { it.id },
                    ) { template ->
                        TemplateCard(
                            templateName = template.name,
                            onClick = { onTemplateClick(template) },
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ActionButton(
                onClick = onCreateNewClick,
                label = "UTWÓRZ NOWY",
                style = ActionButtonStyle.LightFilled,
                icon = Icons.Default.Add,
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}