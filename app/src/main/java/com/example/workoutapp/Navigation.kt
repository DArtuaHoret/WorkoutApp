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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.workoutapp.ui.screens.section_1.ExerciseDetailScreen
import com.example.workoutapp.ui.screens.section_1.ExerciseDetailViewModel
import com.example.workoutapp.ui.screens.section_1.ExerciseSearchScreen
import com.example.workoutapp.ui.screens.section_1.ExerciseSearchViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateDetailScreen
import com.example.workoutapp.ui.screens.section_1.TemplateDetailViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateListScreen
import com.example.workoutapp.ui.screens.section_1.TemplateListViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutHistoryScreen
import com.example.workoutapp.ui.screens.section_4.AddMealSearchScreen
import com.example.workoutapp.ui.screens.section_4.BarcodeScannerScreen
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsScreen
import com.example.workoutapp.ui.screens.section_4.ProductDetailScreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate

// --- Type-safe destinations ---
sealed interface Destinations {

    // ── Sekcja 1 – zagnieżdżony graf szablonów ───────────────────────────
    /** Korzeń zagnieżdżonego grafu szablonów – nie jest osobnym screenem. */
    @Serializable data object TemplatesGraph : Destinations

    /** Lista szablonów – startDestination grafu szablonów. */
    @Serializable data object Templates : Destinations

    /** Podgląd / edycja pojedynczego szablonu. */
    @Serializable data class TemplateDetail(
        val id: String,          // pusty string oznacza nowy szablon
        val name: String,
        val description: String = "",
    ) : Destinations

    @Serializable data class ExerciseSearch(
        val templateId: String,
    ) : Destinations

    @Serializable data class ExerciseDetail(
        val templateId: String,
        val exerciseId: String,
        val exerciseName: String,
        val itemId: String = "",
        val note: String = "",
    ) : Destinations


    // ── Sekcja 2 ─────────────────────────────────────────────────────────
    @Serializable data object Workout : Destinations

    // ── Sekcja 3 ─────────────────────────────────────────────────────────
    @Serializable data object History : Destinations

    // ── Sekcja 4 – zagnieżdżony graf diety ───────────────────────────────
    /** Korzeń zagnieżdżonego grafu diety – nie jest osobnym screenem. */
    @Serializable data object DietGraph : Destinations

    /** Lista produktów – startDestination grafu diety. */
    @Serializable data class Diet(val initialQuery: String = "") : Destinations

    @Serializable data object BarcodeScanner : Destinations
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
    BottomNavItem(Destinations.TemplatesGraph, "📋", "Szablony"),
    BottomNavItem(Destinations.Workout,        "🏋️", "Trening"),
    BottomNavItem(Destinations.History,        "📅", "Historia"),
    BottomNavItem(Destinations.DietGraph,      "🍎", "Dieta"),
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
            startDestination = Destinations.TemplatesGraph,
            modifier = Modifier.padding(innerPadding)
        ) {

            // ── Zagnieżdżony graf sekcji 1 ───────────────────────────────
            navigation<Destinations.TemplatesGraph>(
                startDestination = Destinations.Templates,
            ) {
                composable<Destinations.Templates> { backStackEntry ->
                    val viewModel: TemplateListViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory = WorkoutAppViewModelProvider.Factory
                    )
                    val coroutineScope = rememberCoroutineScope()
                    TemplateListScreen(
                        viewModel = viewModel,
                        onTemplateClick = { template ->
                            navController.navigate(
                                Destinations.TemplateDetail(id = template.id, name = template.name)
                            )
                        },
                        onCreateNewClick = {
                            coroutineScope.launch {
                                val id = viewModel.createTemplate()
                                navController.navigate(Destinations.TemplateDetail(id = id, name = ""))
                            }
                        },
                    )
                }


                composable<Destinations.TemplateDetail> { backStackEntry ->
                    val templateDetailViewModel: TemplateDetailViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory = WorkoutAppViewModelProvider.Factory
                    )
                    val dbId by templateDetailViewModel.dbId.collectAsState()

                    TemplateDetailScreen(
                        viewModel = templateDetailViewModel,
                        onBackClick = { navController.popBackStack() },
                        onAddExerciseClick = {
                            navController.navigate(
                                Destinations.ExerciseSearch(templateId = dbId.toString())
                            )
                        },
                        onEditExercise = { exercise ->
                            navController.navigate(
                                Destinations.ExerciseDetail(
                                    templateId   = dbId.toString(),
                                    exerciseId   = exercise.exerciseId,
                                    exerciseName = exercise.name,
                                    itemId       = exercise.id, // ← dodaj
                                    note         = exercise.note,
                                )
                            )
                        },
                    )
                }

                composable<Destinations.ExerciseSearch> { backStackEntry ->
                    ExerciseSearchScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                        onExerciseClick = { exercise ->
                            val templateId = backStackEntry.toRoute<Destinations.ExerciseSearch>().templateId
                            navController.navigate(
                                Destinations.ExerciseDetail(
                                    templateId   = templateId,
                                    exerciseId   = exercise.id,
                                    exerciseName = exercise.name,
                                )
                            )
                        },
                        onAddCustomExercise = {
                            val templateId = backStackEntry.toRoute<Destinations.ExerciseSearch>().templateId
                            navController.navigate(
                                Destinations.ExerciseDetail(
                                    templateId   = templateId,
                                    exerciseId   = "",        // pusty = nowe ćwiczenie
                                    exerciseName = "",
                                )
                            )
                        },
                    )
                }


                composable<Destinations.ExerciseDetail> { backStackEntry ->
                    ExerciseDetailScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                        onSaveClick = {
                            navController.popBackStack<Destinations.TemplateDetail>(inclusive = false)
                        },
                    )
                }


            }

            // ── Sekcja 2 ─────────────────────────────────────────────────
            composable<Destinations.Workout> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Trening – wkrótce", color = Color.White)
                }
            }

            // ── Sekcja 3 ─────────────────────────────────────────────────
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
                startDestination = Destinations.Diet(),
            ) {
                composable<Destinations.Diet> { backStackEntry ->
                    AddMealSearchScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
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
                        onProductQuickAddClick = { /* TODO */ },
                        onAddCustomProductClick = {
                            navController.navigate(Destinations.ProductCreate)
                        },
                        onLibraryClick = {
                            navController.navigate(Destinations.Library)
                        },
                        onScanBarcodeClick = {
                            navController.navigate(Destinations.BarcodeScanner)
                        },
                    )
                }

                composable<Destinations.BarcodeScanner> { backStackEntry ->
                    BarcodeScannerScreen(
                        viewModel = viewModel(
                            viewModelStoreOwner = backStackEntry,
                            factory = WorkoutAppViewModelProvider.Factory
                        ),
                        onBackClick = { navController.popBackStack() },
                        onBarcodeScanned = { barcode ->
                            navController.navigate(Destinations.Diet(initialQuery = barcode)) {
                                popUpTo(Destinations.Diet::class) { inclusive = true }
                            }
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

                composable<Destinations.ProductCreate> { backStackEntry ->
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