package com.example.language_tutor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.language_tutor.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UserStats(
    val timeSpent: Int = 0, // Время в приложении (в минутах)
    val wordsLearned: Int = 0, // Количество выученных слов
    val testsCompleted: Int = 0 // Количество пройденных тестов
)

class StatsViewModel(private val repository: FirestoreRepository = FirestoreRepository()) : ViewModel() {

    private val _stats = MutableStateFlow(UserStats())
    val stats: StateFlow<UserStats> = _stats

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            val words = repository.getWords()
            val learnedWords = words.count { it.isLearned }
            // Здесь можно добавить логику загрузки времени и тестов из Firestore
            _stats.value = UserStats(
                timeSpent = 120, // Заглушка (в минутах)
                wordsLearned = learnedWords,
                testsCompleted = 5 // Заглушка (количество тестов)
            )
        }
    }

    fun resetStats() {
        viewModelScope.launch {
            _stats.value = UserStats()
            // Здесь можно добавить логику сброса статистики в Firestore
        }
    }
}
