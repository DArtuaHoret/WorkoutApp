package com.example.workoutapp.ui.screens.section_1


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview



@Preview(name = "TemplateListScreen – with items", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTemplateListWithItems() {
    MaterialTheme {
        TemplateListScreen(
            templates = listOf(
                WorkoutTemplate("1", "PLAN NA MASĘ (P&P&L)"),
                WorkoutTemplate("2", "PLAN NA REDUKCJĘ"),
                WorkoutTemplate("3", "PLAN NA SIŁĘ (5x5)"),
                WorkoutTemplate("4", "PLAN NA MASĘEEEE (P&P&L)"),
                WorkoutTemplate("5", "PLAN NA REDUKCJĘEEEE"),
                WorkoutTemplate("6", "PLAN NA SIŁĘEEEEE FOL KIO LKIOK (5x5)"),
            ),
            onTemplateClick = {},
            onCreateNewClick = {},
        )
    }
}

@Preview(name = "TemplateListScreen – empty", showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun PreviewTemplateListEmpty() {
    MaterialTheme {
        TemplateListScreen(
            templates = emptyList(),
            onTemplateClick = {},
            onCreateNewClick = {},
        )
    }
}
