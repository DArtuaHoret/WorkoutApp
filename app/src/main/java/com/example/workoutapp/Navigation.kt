package com.example.workoutapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.workoutapp.ui.screens.SettingsScreen
import com.example.workoutapp.ui.screens.section_1.ExerciseDetailScreen
import com.example.workoutapp.ui.screens.section_1.ExerciseSearchScreen
import com.example.workoutapp.ui.screens.section_1.TemplateDetailScreen
import com.example.workoutapp.ui.screens.section_1.TemplateDetailViewModel
import com.example.workoutapp.ui.screens.section_1.TemplateListScreen
import com.example.workoutapp.ui.screens.section_1.TemplateListViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutHistoryScreen
import com.example.workoutapp.ui.screens.section_3.WorkoutCalendarViewModel
import com.example.workoutapp.ui.screens.section_3.WorkoutStatsScreen
import com.example.workoutapp.ui.screens.section_3.WorkoutStatsViewModel
import com.example.workoutapp.ui.screens.section_4.AddMealSearchScreen
import com.example.workoutapp.ui.screens.section_4.BarcodeScannerScreen
import com.example.workoutapp.ui.screens.section_4.FavoriteProductsScreen
import com.example.workoutapp.ui.screens.section_4.ProductDetailScreen
import com.example.workoutapp.ui.screens.section_2.ExerciseTrackingViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.time.LocalDate

import com.example.workoutapp.ui.screens.section_3.WorkoutDetailsScreen

import com.example.workoutapp.ui.screens.section_3.TemplateExercisesScreen
import com.example.workoutapp.ui.screens.section_3.TemplateExercisesViewModel
import com.example.workoutapp.ui.screens.section_3.SessionExerciseEditScreen
import com.example.workoutapp.ui.screens.section_3.SessionExerciseEditViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
<<<<<<< Updated upstream
import androidx.compose.ui.res.stringResource
=======

import com.example.workoutapp.ui.screens.section_3.WorkoutDetailsViewModel


>>>>>>> Stashed changes


// --- Type-safe destinations ---
sealed interface Destinations {

    @Serializable data object Settings : Destinations

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
    @Serializable data object WorkoutGraph : Destinations // NOWY GRAF
    @Serializable data object Workout : Destinations // Placeholder
    @Serializable data class ActiveWorkout(
        val templateId: String,
        val dateIso: String,
        val sessionId: String? = null
    ) : Destinations

    /** Korzeń grafu historii */
    // ── Sekcja 3 – zagnieżdżony graf historii ────────────────────────────
    @Serializable data object HistoryGraph : Destinations
    @Serializable data object HistoryCalendar : Destinations
    @Serializable data class HistoryStats(
        val startDateIso: String,
        val endDateIso: String
    ) : Destinations
    @Serializable data class HistoryDetails(
        val dateIsoString: String
    ) : Destinations
    @Serializable data class TemplateExercises(
        val templateId: String,
        val templateName: String
    ) : Destinations
    @Serializable data class SessionExerciseEdit(
        val sessionItemId: String,
        val exerciseId: String,
        val exerciseName: String
    ) : Destinations
    /** Korzeń zagnieżdżonego grafu diety – nie jest osobnym screenem. */
    // ── Sekcja 4 – zagnieżdżony graf diety ───────────────────────────────
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
        val canDelete: Boolean = false, // ← NOWE
    ) : Destinations

    /** Tworzenie nowego produktu. */
    @Serializable data class ProductCreate(
        val id: String = "",
        val name: String = "",
        val description: String = "",
        val kcal: String = "",
        val protein: String = "",
        val fat: String = "",
        val carbs: String = "",
        val isEditMode: Boolean = false,    // ← NOWE
        val isFavorite: Boolean = false,    // ← NOWE
    ) : Destinations

    @Serializable data object Library : Destinations
}


// --- Metadane dla bottom bara ---
data class BottomNavItem(
    val destination: Destinations,
    val icon: String,
    val label: String,
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(Destinations.TemplatesGraph, stringResource(R.string.nav_templates_icon), stringResource(R.string.nav_templates_label)),
        BottomNavItem(Destinations.Workout,        stringResource(R.string.nav_workout_icon),   stringResource(R.string.nav_workout_label)),
        BottomNavItem(Destinations.HistoryGraph,   stringResource(R.string.nav_history_icon),   stringResource(R.string.nav_history_label)),
        BottomNavItem(Destinations.DietGraph,      stringResource(R.string.nav_diet_icon),      stringResource(R.string.nav_diet_label)),
        BottomNavItem(Destinations.Settings,       stringResource(R.string.nav_settings_icon),  stringResource(R.string.nav_settings_label)),
    )

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

            composable<Destinations.Settings> {
                SettingsScreen(
                    viewModel = viewModel(factory = WorkoutAppViewModelProvider.Factory),
                    onBackClick = { navController.popBackStack() },
                )
            }

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
            navigation<Destinations.WorkoutGraph>(startDestination = Destinations.Workout) {

                composable<Destinations.Workout> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Ustaw trening w kalendarzu", color = Color.White)
                    }
                }

                composable<Destinations.ActiveWorkout> { backStackEntry ->
                    val viewModel: ExerciseTrackingViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory = WorkoutAppViewModelProvider.Factory
                    )

                    val exerciseName by viewModel.exerciseName.collectAsState()
                    val exerciseDescription: String by viewModel.exerciseDescription.collectAsState()
                    val currentSet by viewModel.currentSet.collectAsState()
                    val reps by viewModel.reps.collectAsState()
                    val weight by viewModel.weight.collectAsState()
                    val restTime by viewModel.restTime.collectAsState()
                    val isResting by viewModel.isResting.collectAsState()
                    val restsCompleted by viewModel.restsCompleted.collectAsState()
                    val isWorkoutFinished by viewModel.isWorkoutFinished.collectAsState()

                    com.example.workoutapp.ui.screens.active_workout.ActiveWorkoutScreen(
                        exerciseName = exerciseName,
                        exerciseDescription = exerciseDescription,
                        currentSet = currentSet,
                        reps = reps,
                        weight = weight,
                        restTime = restTime,
                        isResting = isResting,
                        restsCompleted = restsCompleted,
                        isWorkoutFinished = isWorkoutFinished,
                        onBackClick = { navController.popBackStack() },
                        onRepsChange = viewModel::updateReps,
                        onWeightChange = viewModel::updateWeight,
                        onRestTimeChange = viewModel::updateRestTime,
                        onDoneClick = viewModel::onDoneClick,
                        onTimerFinished = viewModel::onTimerFinished,
                        onSaveDescription = viewModel::updateDescription
                    )
                }
            }


            // ── Zagnieżdżony graf sekcji 3 (HISTORIA) ──────────────────
            navigation<Destinations.HistoryGraph>(startDestination = Destinations.HistoryCalendar) {

                composable<Destinations.HistoryCalendar> { backStackEntry ->
                    val viewModel: WorkoutCalendarViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory
                    )

                    LaunchedEffect(selectedDate) {
                        selectedDate?.let { viewModel.loadSessionsForDate(it) }
                    }

                    WorkoutHistoryScreen(
                        viewModel = viewModel,
                        selectedDate = selectedDate,
                        onDateSelected = { selectedDate = it },
                        onBackClick = {},
                        onAssignWorkoutClick = { templateId ->
                            selectedDate?.let { date ->
                                viewModel.assignTemplateToDate(templateId, date)
                                navController.navigate(Destinations.HistoryDetails(dateIsoString = date.toString()))
                            }
                        },
                        onViewStatsClick = { startDate, endDate ->
                            navController.navigate(
                                Destinations.HistoryStats(
                                    startDateIso = startDate.toString(),
                                    endDateIso = endDate.toString()
                                )
                            )
                        },
                        onViewWorkoutDetailsClick = {
                            selectedDate?.let { date ->
                                navController.navigate(Destinations.HistoryDetails(dateIsoString = date.toString()))
                            }
                        }
                    )
                }

                composable<Destinations.HistoryDetails> { backStackEntry ->
                    val viewModel: WorkoutDetailsViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory
                    )

                    WorkoutDetailsScreen(
                        viewModel = viewModel,
                        onBackClick = { navController.popBackStack() },
                        onViewExercisesClick = { sessionId ->
                            if (sessionId.isNotEmpty()) {
                                val uiState = viewModel.uiState.value
                                val sessionName = uiState.workoutSessions
                                    .find { it.id == sessionId }?.workoutName ?: ""
                                navController.navigate(
                                    Destinations.TemplateExercises(
                                        templateId = sessionId,
                                        templateName = sessionName
                                    )
                                )
                            }
                        },
                        onStartWorkoutClick = { sessionId, templateId ->
                            if (templateId.isNotEmpty()) {
                                navController.navigate(
                                    Destinations.ActiveWorkout(
                                        templateId = templateId,
                                        dateIso = viewModel.date.toString(),
                                        sessionId = sessionId
                                    )
                                ) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        }
                    )
                }

                composable<Destinations.TemplateExercises> { backStackEntry ->
                    val viewModel: TemplateExercisesViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory = WorkoutAppViewModelProvider.Factory
                    )
                    TemplateExercisesScreen(
                        viewModel = viewModel,
                        onEditExercise = { sessionItemId, exerciseId, exerciseName ->
                            navController.navigate(
                                Destinations.SessionExerciseEdit(
                                    sessionItemId = sessionItemId,
                                    exerciseId = exerciseId,
                                    exerciseName = exerciseName
                                )
                            )
                        },
                        onBackClick = { navController.popBackStack() }
                    )
                }

                composable<Destinations.SessionExerciseEdit> { backStackEntry ->
                    val viewModel: SessionExerciseEditViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry,
                        factory = WorkoutAppViewModelProvider.Factory
                    )
                    SessionExerciseEditScreen(
                        viewModel = viewModel,
                        onBackClick = { navController.popBackStack() },
                        onSaveClick = { navController.popBackStack() },
                        onDeleteExerciseClick = { navController.popBackStack() }
                    )
                }

                composable<Destinations.HistoryStats> { backStackEntry ->
                    val viewModel: WorkoutStatsViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory
                    )

                    WorkoutStatsScreen(
                        viewModel = viewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                }
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
                                    kcal        = product.kcal,
                                    protein     = product.protein,
                                    fat         = product.fat,
                                    carbs       = product.carbs,
                                    canDelete   = false,
                                )
                            )
                        },
                        onProductQuickAddClick = { /* TODO */ },
                        onAddCustomProductClick = {
                            navController.navigate(Destinations.ProductCreate())  // ← poprawne
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
                                    canDelete   = true, // ← z biblioteki można usuwać
                                )
                            )
                        },
                        onEditClick = { product ->
                            navController.navigate(
                                Destinations.ProductCreate(
                                    id          = product.id,
                                    name        = product.name,
                                    description = product.description,
                                    kcal        = product.kcal,
                                    protein     = product.protein,
                                    fat         = product.fat,
                                    carbs       = product.carbs,
                                    isEditMode  = true,                 // ← NOWE
                                    isFavorite  = product.isFavorite,   // ← NOWE
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}