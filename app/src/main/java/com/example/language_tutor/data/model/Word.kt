package com.example.language_tutor.data.model

import com.google.firebase.firestore.DocumentId

data class Word(
    @DocumentId val id: String = "",  // Firebase Firestore документ ID
    val word: String = "",
    val translation: String = "",
    val isLearned: Boolean = false,
    val level: String = "A1"
) {
    // Функция для обновления состояния слова (изучено/не изучено)
    fun toggleLearned(): Word {
        return this.copy(isLearned = !isLearned)
    }

    // Функция для представления слова в удобочитаемом виде
    override fun toString(): String {
        return "$word - $translation [${if (isLearned) "Выучено" else "Не выучено"}]"
    }
}
