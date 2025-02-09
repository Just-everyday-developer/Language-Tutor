package com.example.language_tutor.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.language_tutor.data.model.Word
import com.example.language_tutor.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MemorizerViewModel(private val repository: FirestoreRepository = FirestoreRepository()) : ViewModel() {

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            _words.value = repository.getWords()
        }
    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            repository.addWord(word)
            loadWords() // Перезагружаем список после добавления
        }
    }

    fun deleteWord(wordId: String) {
        viewModelScope.launch {
            repository.deleteWord(wordId)
            loadWords() // Перезагружаем список после удаления
        }
    }

    fun toggleLearned(word: Word) {
        viewModelScope.launch {
            val updatedWord = word.copy(isLearned = !word.isLearned)
            repository.addWord(updatedWord) // Перезаписываем слово с обновленным статусом
            loadWords()
        }
    }
}
