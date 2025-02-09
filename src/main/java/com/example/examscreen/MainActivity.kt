package com.example.examscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                LanguageTestScreen()
            }
        }
    }
}

@Composable
fun LanguageTestScreen() {
    var selectedLevel by remember { mutableStateOf<String?>(null) }
    var selectedSection by remember { mutableStateOf<String?>(null) }
    var showTest by remember { mutableStateOf(false) }

    if (showTest && selectedLevel != null) {
        TestScreen(selectedLevel!!) { showTest = false }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Выберите уровень", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("A1", "A2", "B1", "B2").forEach { level ->
                    LevelButton(level, selectedLevel) { selectedLevel = it }
                }
            }

            ActionButton(text = "Начать") { showTest = selectedLevel != null }

            Divider(modifier = Modifier.padding(vertical = 16.dp), thickness = 1.dp, color = Color.Gray)

            Text("Mock IELTS", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("Выберите секции", fontSize = 16.sp, modifier = Modifier.padding(top = 8.dp))

            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Reading", "Listening", "Writing").forEach { section ->
                    SectionButton(section, selectedSection) { selectedSection = it }
                }
            }
        }
    }
}

@Composable
fun TestScreen(level: String, onBack: () -> Unit) {
    val questions = if (level == "A1" || level == "A2") getA1A2Questions() else getB1B2Questions()
    val answers = remember { mutableStateListOf<String?>(null, null, null, null, null) }
    val scrollState = rememberScrollState()
    var result by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Тест уровня $level", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        questions.forEachIndexed { index, question ->
            QuestionCard(question, index) { selectedAnswer ->
                answers[index] = selectedAnswer
            }
        }

        ActionButton(text = "Отправить") {
            result = answers.filterIndexed { index, answer ->
                answer == questions[index].correctAnswer
            }.count()
        }

        result?.let {
            Text("Правильных ответов: $it из ${questions.size}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            ActionButton(text = "Вернуться назад") { onBack() }
        }
    }
}

@Composable
fun QuestionCard(question: Question, index: Int, onAnswerSelected: (String) -> Unit) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text("${index + 1}. ${question.text}", fontSize = 16.sp, fontWeight = FontWeight.Bold)

        question.options.forEach { option ->
            Button(
                onClick = {
                    selectedAnswer = option
                    onAnswerSelected(option)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedAnswer == option) Color.Blue else Color.Gray
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
            ) {
                Text(option, color = Color.White)
            }
        }
    }
}

// Вопросы для A1/A2
fun getA1A2Questions() = listOf(
    Question("Какое время суток 'Morning'?", listOf("Утро", "День", "Вечер", "Ночь"), "Утро"),
    Question("Как переводится 'I have a cat'?", listOf("У меня есть кот", "Я люблю котов", "Мой кот спит", "Это моя собака"), "У меня есть кот"),
    Question("Сколько букв в слове 'Apple'?", listOf("4", "5", "6", "7"), "5"),
    Question("Выберите правильную форму глагола 'to be' в Present Simple: 'She ___ a teacher'", listOf("is", "are", "am", "be"), "is"),
    Question("Какой цвет означает 'Red'?", listOf("Красный", "Синий", "Зеленый", "Желтый"), "Красный")
)

// Вопросы для B1/B2
fun getB1B2Questions() = listOf(
    Question("Какое слово является синонимом 'big'?", listOf("Huge", "Small", "Tiny", "Weak"), "Huge"),
    Question("Какой глагол подходит: 'If I ___ rich, I would travel the world'?", listOf("were", "am", "was", "be"), "were"),
    Question("Какая из этих фраз является пассивным залогом?", listOf("'The book is read by Tom'", "'Tom reads the book'", "'Tom has read the book'", "'Tom will read the book'"), "'The book is read by Tom'"),
    Question("Выберите правильный вариант: 'He has been working here ___ five years'", listOf("for", "since", "during", "at"), "for"),
    Question("Какой предлог используется с 'interested'?", listOf("in", "on", "at", "with"), "in")
)

// Модель вопроса
data class Question(val text: String, val options: List<String>, val correctAnswer: String)

// UI Компоненты
@Composable
fun LevelButton(level: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(level) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == level) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(64.dp)
    ) {
        Text(level, color = Color.White)
    }
}

@Composable
fun SectionButton(section: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(section) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == section) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.width(100.dp)
    ) {
        Text(section, color = Color.White)
    }
}

@Composable
fun ActionButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text, color = Color.White)
    }
}
