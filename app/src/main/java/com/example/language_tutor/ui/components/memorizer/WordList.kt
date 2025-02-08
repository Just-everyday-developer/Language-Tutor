// ui/components/memorizer/WordsList.kt
package com.example.language_tutor.ui.components.memorizer

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.language_tutor.viewmodel.WordViewModel

@Composable
fun WordsList(viewModel: WordViewModel) {
    val words by viewModel.words.collectAsState()

    if (words.isEmpty()) {
        Text(
            text = "Список слов пуст. Добавьте слова!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    } else {
        LazyColumn {
            items(words) { word ->
                WordCard(word = word, viewModel = viewModel)
            }
        }
    }
}