package com.example.workoutapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.workoutapp.ui.screens.section_1.TemplateListScreen
import com.example.workoutapp.ui.screens.section_3.WorkoutHistoryScreen
import com.example.workoutapp.ui.screens.section_4.AddMealSearchScreen
import kotlinx.serialization.Serializable
import java.time.LocalDate

// --- Type-safe destinations ---
sealed interface Destinations {
    @Serializable data object Templates : Destinations
    @Serializable data object Workout   : Destinations
    @Serializable data object History   : Destinations
    @Serializable data object Diet      : Destinations
}

// --- Metadane dla bottom bara ---
data class BottomNavItem(
    val destination: Destinations,
    val icon: String,
    val label: String,
)

val bottomNavItems = listOf(
    BottomNavItem(Destinations.Templates, "📋", "Szablony"),
    BottomNavItem(Destinations.Workout,   "🏋️", "Trening"),
    BottomNavItem(Destinations.History,   "📅", "Historia"),
    BottomNavItem(Destinations.Diet,      "🍎", "Dieta"),
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Sprawdzamy aktualną destynację przez route (type-safe)
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1A1A1A)) {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hasRoute(item.destination::class) == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.destination) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text(text = item.icon, fontSize = 20.sp)
                        },
                        label = {
                            Text(item.label, color = Color.White)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color(0xFF888888),
                            indicatorColor = Color(0xFF333333)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->

        var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }
        var workoutDays  by remember { mutableStateOf(setOf<LocalDate>()) }
        var query        by remember { mutableStateOf("") }

        NavHost(
            navController = navController,
            startDestination = Destinations.Templates,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Destinations.Templates> {
                TemplateListScreen(
                    templates = emptyList(),
                    onTemplateClick = {},
                    onCreateNewClick = {}
                )
            }
            composable<Destinations.Workout> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Trening - wkrótce", color = Color.White)
                }
            }
            composable<Destinations.History> {
                WorkoutHistoryScreen(
                    selectedDate = selectedDate,
                    workoutDays = workoutDays,
                    onDateSelected = { selectedDate = it },
                    onBackClick = {},
                    onAssignWorkoutClick = {},
                    onViewStatsClick = {},
                    onViewWorkoutDetailsClick = {}
                )
            }
            composable<Destinations.Diet> {
                AddMealSearchScreen(
                    query = query,
                    onQueryChange = { query = it },
                    searchResults = emptyList(),
                    onProductAddClick = {},
                    onAddCustomProductClick = {}
                )
            }
        }
    }
}