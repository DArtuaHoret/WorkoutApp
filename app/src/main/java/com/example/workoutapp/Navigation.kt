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
import androidx.navigation.NavDestination.Companion.hierarchy // NOWY IMPORT - pozwala wykrywać pod-ekrany
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
import com.example.workoutapp.ui.screens.section_3.WorkoutDetailsScreen
import com.example.workoutapp.ui.screens.section_3.WorkoutSessionData
import com.example.workoutapp.ui.screens.section_3.SessionWithName
import com.example.workoutapp.ui.screens.section_3.TemplateExercisesScreen
import com.example.workoutapp.ui.screens.section_3.TemplateExercisesViewModel
import com.example.workoutapp.ui.screens.section_3.SessionExerciseEditScreen
import com.example.workoutapp.ui.screens.section_3.SessionExerciseEditViewModel
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


// --- Type-safe destinations ---
sealed interface Destinations {

    @Serializable data object Settings : Destinations

    // ── Sekcja 1 – zagnieżdżony graf szablonów ───────────────────────────
    @Serializable data object TemplatesGraph : Destinations
    @Serializable data object Templates : Destinations
    @Serializable data class TemplateDetail(
        val id: String,
        val name: String,
        val description: String = "",
    ) : Destinations
    @Serializable data class ExerciseSearch(val templateId: String) : Destinations
    @Serializable data class ExerciseDetail(
        val templateId: String,
        val exerciseId: String,
        val exerciseName: String,
        val itemId: String = "",
        val note: String = "",
    ) : Destinations

    // ── Sekcja 2 – zagnieżdżony graf treningu (POPRAWKA) ─────────────────
    @Serializable data object WorkoutGraph : Destinations // NOWY GRAF
    @Serializable data object Workout : Destinations // Placeholder
    @Serializable data class ActiveWorkout(
        val templateId: String,
        val dateIso: String,
        val sessionId: String? = null
    ) : Destinations

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

    // ── Sekcja 4 – zagnieżdżony graf diety ───────────────────────────────
    @Serializable data object DietGraph : Destinations
    @Serializable data class Diet(val initialQuery: String = "") : Destinations
    @Serializable data object BarcodeScanner : Destinations
    @Serializable data class ProductDetail(
        val id: String, val name: String, val description: String,
        val kcal: String, val protein: String, val fat: String, val carbs: String,
    ) : Destinations
    @Serializable data class ProductCreate(
        val id: String = "", val name: String = "", val description: String = "",
        val kcal: String = "", val protein: String = "", val fat: String = "", val carbs: String = "",
        val isEditMode: Boolean = false, val isFavorite: Boolean = false,
    ) : Destinations
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
<<<<<<< Updated upstream
    BottomNavItem(Destinations.Workout,        "🏋️", "Trening"),
=======
    BottomNavItem(Destinations.WorkoutGraph,   "🏋️", "Trening"), // POPRAWKA: Odwołuje się do głównego grafu!
>>>>>>> Stashed changes
    BottomNavItem(Destinations.HistoryGraph,   "📅", "Historia"),
    BottomNavItem(Destinations.DietGraph,      "🍎", "Dieta"),
    BottomNavItem(Destinations.Settings,       "⚙️", "Ustawienia"),  // ← NOWE
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val isWorkoutScreen = currentDestination?.route?.contains("ActiveWorkout") == true
    var pendingBottomNavDestination by remember { mutableStateOf<Destinations?>(null) }

    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1A1A1A)) {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.destination::class) } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                // PRZECHWYCENIE: Sprawdzamy naszą niezawodną zmienną isWorkoutScreen
                                if (isWorkoutScreen) {
                                    // Zatrzymujemy przejście i wywołujemy okno
                                    pendingBottomNavDestination = item.destination
                                } else {
                                    // Standardowe przejście między zakładkami dla pozostałych ekranów
                                    navController.navigate(item.destination) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
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

        NavHost(
            navController = navController,
            startDestination = Destinations.TemplatesGraph,
            modifier = Modifier.padding(innerPadding)
        ) {

<<<<<<< Updated upstream
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
=======
            // ── Zagnieżdżony graf sekcji 1 (SZABLONY) ──────────────────
            navigation<Destinations.TemplatesGraph>(startDestination = Destinations.Templates) {
>>>>>>> Stashed changes
                composable<Destinations.Templates> { backStackEntry ->
                    val viewModel: TemplateListViewModel = viewModel(
                        viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory
                    )
                    val coroutineScope = rememberCoroutineScope()
                    TemplateListScreen(
                        viewModel = viewModel,
                        onTemplateClick = { template -> navController.navigate(Destinations.TemplateDetail(id = template.id, name = template.name)) },
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
                        viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory
                    )
                    val dbId by templateDetailViewModel.dbId.collectAsState()

                    TemplateDetailScreen(
                        viewModel = templateDetailViewModel,
                        onBackClick = { navController.popBackStack() },
                        onAddExerciseClick = { navController.navigate(Destinations.ExerciseSearch(templateId = dbId.toString())) },
                        onEditExercise = { exercise ->
                            navController.navigate(
                                Destinations.ExerciseDetail(
                                    templateId = dbId.toString(), exerciseId = exercise.exerciseId,
                                    exerciseName = exercise.name, itemId = exercise.id, note = exercise.note,
                                )
                            )
                        },
                    )
                }

                composable<Destinations.ExerciseSearch> { backStackEntry ->
                    ExerciseSearchScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onBackClick = { navController.popBackStack() },
                        onExerciseClick = { exercise ->
                            val templateId = backStackEntry.toRoute<Destinations.ExerciseSearch>().templateId
                            navController.navigate(Destinations.ExerciseDetail(templateId = templateId, exerciseId = exercise.id, exerciseName = exercise.name))
                        },
                        onAddCustomExercise = {
                            val templateId = backStackEntry.toRoute<Destinations.ExerciseSearch>().templateId
                            navController.navigate(Destinations.ExerciseDetail(templateId = templateId, exerciseId = "", exerciseName = ""))
                        },
                    )
                }

                composable<Destinations.ExerciseDetail> { backStackEntry ->
                    ExerciseDetailScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onBackClick = { navController.popBackStack() },
                        onSaveClick = { navController.popBackStack<Destinations.TemplateDetail>(inclusive = false) },
                    )
                }
            }

            // ── Zagnieżdżony graf sekcji 2 (TRENING) - POPRAWIONY ──────
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
                    val templates by viewModel.availableTemplates.collectAsState()
                    val scheduledDates by viewModel.scheduledDates.collectAsState()

                    // Ładuj sesje dla wybranego dnia przy każdej zmianie daty
                    LaunchedEffect(selectedDate) {
                        selectedDate?.let { viewModel.loadSessionsForDate(it) }
                    }

                    WorkoutHistoryScreen(
                        selectedDate = selectedDate,
                        workoutDays = scheduledDates,
                        availableTemplates = templates,
                        onDateSelected = { selectedDate = it },
                        onBackClick = {},
                        onAssignWorkoutClick = { templateId ->
                            selectedDate?.let { date ->
                                // Zapisz przypisanie i przejdź do listy szczegółów
                                viewModel.assignTemplateToDate(templateId, date)
                                navController.navigate(Destinations.HistoryDetails(dateIsoString = date.toString()))
                            }
                        },
                        onViewStatsClick = { startDate, endDate ->
                            navController.navigate(Destinations.HistoryStats(startDateIso = startDate.toString(), endDateIso = endDate.toString()))
                        },
                        onViewWorkoutDetailsClick = {
                            selectedDate?.let { date ->
                                navController.navigate(Destinations.HistoryDetails(dateIsoString = date.toString()))
                            }
                        }
                    )
                }

                composable<Destinations.HistoryDetails> { backStackEntry ->
                    val args = backStackEntry.toRoute<Destinations.HistoryDetails>()
                    val date = LocalDate.parse(args.dateIsoString)

                    val calendarEntry = remember(backStackEntry) {
                        navController.getBackStackEntry(Destinations.HistoryCalendar)
                    }
                    val calendarViewModel: WorkoutCalendarViewModel = viewModel(
                        viewModelStoreOwner = calendarEntry, factory = WorkoutAppViewModelProvider.Factory
                    )
                    val sessions by calendarViewModel.sessionsForSelectedDate.collectAsState()

                    // id = session.id — potrzebne do nawigacji do listy ćwiczeń
                    val sessionData = sessions.map { sessionWithName ->
                        WorkoutSessionData(
                            id = sessionWithName.session.id.toString(),
                            workoutName = sessionWithName.templateName,
                            timeRange = date.toString(),
                            icon = androidx.compose.material.icons.Icons.Filled.FitnessCenter,
                            isCompleted = sessionWithName.session.status == "DONE"
                        )
                    }

                    // Mapa sessionId → templateId (potrzebna do timera)
                    val sessionToTemplateId = sessions.associate {
                        it.session.id.toString() to (it.session.workoutTemplateId?.toString() ?: "")
                    }

                    WorkoutDetailsScreen(
                        date = date,
                        workoutSessions = sessionData,
                        onBackClick = { navController.popBackStack() },
                        onWorkoutClick = {},
                        onViewExercisesClick = { sessionId ->
                            if (sessionId.isNotEmpty()) {
                                val sessionName = sessionData
                                    .find { it.id == sessionId }?.workoutName ?: ""
                                navController.navigate(
                                    Destinations.TemplateExercises(
                                        templateId = sessionId,
                                        templateName = sessionName
                                    )
                                )
                            }
                        },
                        onStartWorkoutClick = { sessionId ->
                            val templateId = sessionToTemplateId[sessionId] ?: ""
                            if (templateId.isNotEmpty()) {
                                navController.navigate(
                                    Destinations.ActiveWorkout(
                                        templateId = templateId,
                                        dateIso = date.toString(),
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
                    val uiState by viewModel.uiState.collectAsState()
                    val args = backStackEntry.toRoute<Destinations.HistoryStats>()
                    val startDate = java.time.LocalDate.parse(args.startDateIso)
                    val endDate = java.time.LocalDate.parse(args.endDateIso)

                    com.example.workoutapp.ui.screens.section_3.WorkoutStatsScreen(
                        startDate = startDate, endDate = endDate, totalDays = uiState.totalDays,
                        completedWorkouts = uiState.completedWorkouts, muscleDistribution = uiState.muscleDistribution,
                        averageTimeInSeconds = uiState.averageTimeInSeconds,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }

            // ── Zagnieżdżony graf sekcji 4 (DIETA) ───────────────────────
            navigation<Destinations.DietGraph>(startDestination = Destinations.Diet()) {
                composable<Destinations.Diet> { backStackEntry ->
                    AddMealSearchScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onProductCardClick = { product ->
                            navController.navigate(Destinations.ProductDetail(id = product.id, name = product.name, description = product.description, kcal = product.kcal, protein = product.protein, fat = product.fat, carbs = product.carbs))
                        },
                        onProductQuickAddClick = { /* TODO */ },
                        onAddCustomProductClick = { navController.navigate(Destinations.ProductCreate()) },
                        onLibraryClick = { navController.navigate(Destinations.Library) },
                        onScanBarcodeClick = { navController.navigate(Destinations.BarcodeScanner) },
                    )
                }

                composable<Destinations.BarcodeScanner> { backStackEntry ->
                    BarcodeScannerScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
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
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onBackClick = { navController.popBackStack() },
                    )
                }

                composable<Destinations.ProductCreate> { backStackEntry ->
                    ProductDetailScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onBackClick = { navController.popBackStack() },
                        onSaveProductClick = { navController.popBackStack() },
                    )
                }

                composable<Destinations.Library> { backStackEntry ->
                    FavoriteProductsScreen(
                        viewModel = viewModel(viewModelStoreOwner = backStackEntry, factory = WorkoutAppViewModelProvider.Factory),
                        onBackClick = { navController.popBackStack() },
                        onProductClick = { product ->
                            navController.navigate(Destinations.ProductDetail(id = product.id, name = product.name, description = product.description, kcal = product.kcal, protein = product.protein, fat = product.fat, carbs = product.carbs))
                        },
                        onEditClick = { product ->
                            navController.navigate(Destinations.ProductCreate(id = product.id, name = product.name, description = product.description, kcal = product.kcal, protein = product.protein, fat = product.fat, carbs = product.carbs, isEditMode = true, isFavorite = product.isFavorite))
                        },
                    )
                }
            }
        }
        if (pendingBottomNavDestination != null) {
            com.example.workoutapp.ui.reusableContents.Section_2.CenteredExitConfirmationDialog(
                onConfirm = {
                    val destinationToNavigate = pendingBottomNavDestination!!
                    pendingBottomNavDestination = null

                    // Faktyczne wykonanie przeskoku po zatwierdzeniu
                    navController.navigate(destinationToNavigate) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onDismiss = {
                    // Anulowanie zmiany zakładki
                    pendingBottomNavDestination = null
                }
            )
        }
    }
}