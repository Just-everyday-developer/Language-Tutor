// viewmodel/WordViewModel.kt
package com.example.language_tutor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.language_tutor.data.model.Word
import com.example.language_tutor.data.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {
    val words: Flow<List<Word>> = repository.allWords

    fun addWord(word: String, translation: String, level: String) {
        viewModelScope.launch {
            repository.insert(
                Word(
                    word = word,
                    translation = translation,
                    level = level
                )
            )
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }

    fun toggleLearnedStatus(word: Word) {
        viewModelScope.launch {
            repository.updateLearnedStatus(word.id, !word.isLearned)
        }
    }

    class Factory(private val repository: WordRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WordViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}