package com.example.language_tutor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.LevelButton(level: String, selected: String?, onClick: (String) -> Unit) {
    Button(
        onClick = { onClick(level) },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected == level) Color.Blue else Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp).weight(1f)
    ) {
        Text(level, color = Color.White, maxLines = 1)
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
        modifier = Modifier.width(200.dp).padding(10.dp)
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