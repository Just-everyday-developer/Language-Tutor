package com.example.test.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.test.data.Word
import com.example.test.viewmodel.WordViewModel

@Composable
fun MemorizerScreen(navController: NavController, viewModel: WordViewModel = viewModel()) {
    val words by viewModel.words.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "Запоминатор",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        WordInput(viewModel)

        if (words.isEmpty()) {
            Text(text = "Список слов пуст. Добавьте слова!", fontSize = 18.sp)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(words) { word ->
                    WordItem(word, viewModel)
                }
            }
        }
    }
}

@Composable
fun WordInput(viewModel: WordViewModel) {
    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("A1") }

    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        TextField(
            value = word,
            onValueChange = { word = it },
            label = { Text("Слово") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text // Разрешает все символы
            )
        )
        TextField(
            value = translation,
            onValueChange = { translation = it },
            label = { Text("Перевод") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text // Разрешает все символы
            )
        )
        TextField(value = level, onValueChange = { level = it }, label = { Text("Уровень (A1, A2...)") })

        Button(onClick = {
            if (word.isNotEmpty() && translation.isNotEmpty() && level.isNotEmpty()) {
                viewModel.addWord(word, translation, level)
                word = ""
                translation = ""
                level = ""
            }
        }) {
            Text("Добавить слово")
        }
    }
}

@Composable
fun WordItem(word: Word, viewModel: WordViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (word.isLearned) Color.Green.copy(alpha = 0.3f) else Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = word.word, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "Перевод: ${word.translation}", fontSize = 16.sp)
                Text(text = "Уровень: ${word.level}", fontSize = 14.sp, color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Button(onClick = { viewModel.toggleLearnedStatus(word) }) {
                    Text(if (word.isLearned) "Не выучено" else "Выучил")
                }
                Button(onClick = { viewModel.deleteWord(word) }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Удалить")
                }
            }
        }
    }
}
