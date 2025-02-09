package com.example.language_tutor.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.language_tutor.data.model.Word
import com.example.language_tutor.viewmodel.MemorizerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemorizerScreen(navController: NavHostController, viewModel: MemorizerViewModel = viewModel()) {
    val words by viewModel.words.collectAsState()
    var newWord by remember { mutableStateOf("") }
    var newTranslation by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Словарь") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Добавить новое слово",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            OutlinedTextField(
                value = newWord,
                onValueChange = { newWord = it },
                label = { Text("Слово") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newTranslation,
                onValueChange = { newTranslation = it },
                label = { Text("Перевод") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (newWord.isNotBlank() && newTranslation.isNotBlank()) {
                        viewModel.addWord(Word(word = newWord, translation = newTranslation))
                        newWord = ""
                        newTranslation = ""
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Добавить слово", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Список слов",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )

            LazyColumn {
                items(words) { word ->
                    WordItem(word, viewModel)
                }
            }
        }
    }
}

@Composable
fun WordItem(word: Word, viewModel: MemorizerViewModel) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = word.word, fontSize = 18.sp, fontWeight = MaterialTheme.typography.bodyLarge.fontWeight)
                Text(text = word.translation, fontSize = 16.sp, color = Color.Gray)
            }

            IconButton(onClick = { viewModel.toggleLearned(word) }) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Отметить как выученное",
                    tint = if (word.isLearned) Color.Green else Color.Gray
                )
            }

            IconButton(onClick = { viewModel.deleteWord(word.id) }) {
                Icon(Icons.Filled.Delete, contentDescription = "Удалить", tint = Color.Red)
            }
        }
    }
}
