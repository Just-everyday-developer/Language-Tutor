package com.example.test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val wordDao = db.wordDao()

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    init {
        loadWords()
    }

    fun loadWords() {
        viewModelScope.launch {
            _words.value = wordDao.getAllWords()
        }
    }

    fun addWord(word: String, translation: String, level: String) {
        viewModelScope.launch {
            wordDao.insert(Word(word = word, translation = translation, level = level))
            loadWords()
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordDao.delete(word)
            loadWords()
        }
    }

    fun toggleLearnedStatus(word: Word) {
        viewModelScope.launch {
            wordDao.updateLearnedStatus(word.id, !word.isLearned)
            loadWords()
        }
    }
}
