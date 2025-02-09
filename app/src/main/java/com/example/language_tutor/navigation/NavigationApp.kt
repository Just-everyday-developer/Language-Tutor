package com.example.language_tutor.navigation

import com.example.language_tutor.ui.screens.ExamScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import com.example.language_tutor.ui.screens.*
import com.example.language_tutor.ui.components.AppDrawer
import com.example.language_tutor.ui.components.Header

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Statistics : Screen("statistics")
    data object Exam : Screen("exam")
    data object Test : Screen("exam/test/{level}")
    data object Memorizer : Screen("memorizer")
    data object Account : Screen("account")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route?.split("/")?.firstOrNull()

    // Function to get screen title, now includes test screen
    val getScreenTitle = { route: String? ->
        when (route) {
            "main" -> "Каталог курсов"
            "statistics" -> "Статистика"
            "exam" -> "Экзамены"
            "memorizer" -> "Запоминатор"
            "account" -> "Аккаунт"
            else -> "Каталог курсов"
        }
    }

    // Determine if we should show the drawer and header
    val shouldShowDrawer = currentRoute != "exam/test"

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                        navController.navigate(route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            popUpTo("main") { saveState = true }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        },
        gesturesEnabled = shouldShowDrawer
    ) {
        Scaffold(
            topBar = {
                if (shouldShowDrawer) {
                    Header(
                        title = getScreenTitle(currentRoute),
                        onMenuClick = { scope.launch { drawerState.open() } }
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Main.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Main.route) {
                    CoursesScreen()
                }
                composable(Screen.Statistics.route) {
                    StatisticsScreen()
                }
                composable(Screen.Exam.route) {
                    ExamScreen(
                        onStartTest = { level ->
                            navController.navigate("exam/test/$level")
                        }
                    )
                }
                composable(
                    route = Screen.Test.route,
                    arguments = listOf(
                        navArgument("level") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val level = backStackEntry.arguments?.getString("level") ?: "A1"
                    TestScreen(
                        level = level,
                        onBack = { navController.navigateUp() }
                    )
                }
                composable(Screen.Memorizer.route) {
                    MemorizerScreen(navController)
                }
                composable(Screen.Account.route) {
                    AccountScreen(navController)
                }
            }
        }
    }
}