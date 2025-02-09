package com.example.language_tutor.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.language_tutor.ui.components.Question
import com.example.language_tutor.ui.components.QuestionCard
import com.example.language_tutor.ui.components.getA1A2Questions
import com.example.language_tutor.ui.components.getB1B2Questions
import com.example.language_tutor.ui.components.ActionButton

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