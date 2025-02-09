package com.example.language_tutor.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.language_tutor.ui.screens.*
import com.example.language_tutor.ui.components.AppDrawer
import com.example.language_tutor.ui.components.Header
import com.example.language_tutor.ui.screens.memorizer.MemorizerScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Add this to track current route
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Function to get screen title
    val getScreenTitle = { route: String? ->
        when (route) {
            "main" -> "Каталог курсов"
            "statistics" -> "Статистика"
            "exam" -> "Экзамены"
            "memorizer" -> "Запоминатель"
            else -> "Каталог курсов"
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                onDestinationClicked = { route ->
                    scope.launch {
                        drawerState.close()
                        navController.navigate(route)
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                Header(
                    title = getScreenTitle(currentRoute),
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "main",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("main") { CoursesScreen() }
                composable("statistics") { StatisticsScreen() }
                composable("exam") { ExamScreen() }
                composable("memorizer") { MemorizerScreen(navController) }
            }
        }
    }
}