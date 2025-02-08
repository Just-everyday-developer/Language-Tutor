// ui/screens/memorizer/MemorizerScreen.kt
package com.example.language_tutor.ui.screens.memorizer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.language_tutor.viewmodel.WordViewModel
import com.example.language_tutor.ui.components.memorizer.WordInput
import com.example.language_tutor.ui.components.memorizer.WordsList

@Composable
fun MemorizerScreen(navController: NavController, viewModel: WordViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Запоминатор",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        WordInput(viewModel)
        WordsList(viewModel)
    }
}

