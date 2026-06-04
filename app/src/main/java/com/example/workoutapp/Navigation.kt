package com.example.workoutapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.workoutapp.ui.screens.section_1.TemplateListScreen
import com.example.workoutapp.ui.screens.section_3.WorkoutHistoryScreen
import com.example.workoutapp.ui.screens.section_4.AddMealSearchScreen
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsScreen
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsViewModel
import com.example.workoutapp.ui.screens.section_4.ProductDetailArgs
import com.example.workoutapp.ui.screens.section_4.ProductDetailScreen
import com.example.workoutapp.ui.screens.section_4.ProductDetailUiState
import kotlinx.serialization.Serializable
import java.time.LocalDate

// --- Type-safe destinations ---
sealed interface Destinations {
    @Serializable data object Templates : Destinations
    @Serializable data object Workout   : Destinations
    @Serializable data object History   : Destinations

    // ── Sekcja 4 – zagnieżdżony graf ─────────────────────────────────────
    /** Korzeń zagnieżdżonego grafu diety – nie jest osobnym screenem. */
    @Serializable data object DietGraph : Destinations

    /** Lista produktów – startDestination grafu diety. */
    @Serializable data object Diet : Destinations

    /** Podgląd istniejącego produktu. */
    @Serializable data class ProductDetail(
        val id: String,
        val name: String,
        val description: String,
        val kcal: String,
        val protein: String,
        val fat: String,
        val carbs: String,
    ) : Destinations

    /** Tworzenie nowego produktu. */
    @Serializable data object ProductCreate : Destinations

    @Serializable data object Library : Destinations
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
    BottomNavItem(Destinations.DietGraph, "🍎", "Dieta"),
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
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
                        icon = { Text(text = item.icon, fontSize = 20.sp) },
                        label = { Text(item.label, color = Color.White) },
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

        NavHost(
            navController = navController,
            startDestination = Destinations.Templates,
            modifier = Modifier
                .padding(innerPadding)
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

            // ── Zagnieżdżony graf sekcji 4 ───────────────────────────────
            navigation<Destinations.DietGraph>(
                startDestination = Destinations.Diet,
            ) {
                composable<Destinations.Diet> { backStackEntry ->
                    AddMealSearchScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory   // ← dodaj fabrykę
                        ),
                        onProductCardClick = { product ->
                            navController.navigate(
                                Destinations.ProductDetail(
                                    id          = product.id,
                                    name        = product.name,
                                    description = product.description,
                                    kcal        = "",
                                    protein     = "",
                                    fat         = "",
                                    carbs       = "",
                                )
                            )
                        },
                        onProductQuickAddClick = { product ->      // klik w + → np. dodaj bezpośrednio do posiłku
                            // TODO: logika szybkiego dodania
                        },
                        onScanBarcodeClick = {},
                        onAddCustomProductClick = {
                            navController.navigate(Destinations.ProductCreate)
                        },
                        onLibraryClick = {
                            navController.navigate(Destinations.Library)
                        },
                    )
                }
                composable<Destinations.ProductDetail> { backStackEntry ->
                    ProductDetailScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                    )
                }
                composable<Destinations.ProductCreate> {  backStackEntry ->
                    ProductDetailScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                        onSaveProductClick = { navController.popBackStack() },
                    )
                }

                composable<Destinations.Library> { backStackEntry ->
                    FavoriteProductsScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                        onProductClick = { product ->
                            navController.navigate(
                                Destinations.ProductDetail(
                                    id          = product.id,
                                    name        = product.name,
                                    description = product.description,
                                    kcal        = product.kcal,
                                    protein     = product.protein,
                                    fat         = product.fat,
                                    carbs       = product.carbs,
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}