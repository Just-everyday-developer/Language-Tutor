package com.example.language_tutor.data.model

import com.google.firebase.firestore.DocumentId

data class QuizQuestion(
    @DocumentId val id: String = "",  // Firebase Firestore документ ID
    val question: String = "",
    val correctAnswer: String = "",
    val options: List<String> = emptyList()
) {
    // Функция для проверки ответа
    fun isCorrect(answer: String): Boolean {
        return answer.trim().equals(correctAnswer.trim(), ignoreCase = true)
    }

    // Функция для представления вопроса в удобочитаемом виде
    override fun toString(): String {
        return "Вопрос: $question \nОтвет: $correctAnswer"
    }
}
