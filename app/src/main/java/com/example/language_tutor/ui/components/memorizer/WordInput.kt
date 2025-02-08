// ui/components/memorizer/WordInput.kt
package com.example.language_tutor.ui.components.memorizer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.language_tutor.viewmodel.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordInput(viewModel: WordViewModel) {
    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("A1") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = word,
            onValueChange = { word = it },
            label = { Text("Слово") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = translation,
            onValueChange = { translation = it },
            label = { Text("Перевод") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = level,
            onValueChange = { level = it },
            label = { Text("Уровень (A1, A2...)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                if (word.isNotEmpty() && translation.isNotEmpty() && level.isNotEmpty()) {
                    viewModel.addWord(word, translation, level)
                    word = ""
                    translation = ""
                    level = "A1"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Добавить слово")
        }
    }
}