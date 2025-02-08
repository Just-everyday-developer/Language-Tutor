// data/repository/WordRepository.kt
package com.example.language_tutor.data.repository

import com.example.language_tutor.data.dao.WordDao
import com.example.language_tutor.data.model.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

    suspend fun delete(word: Word) {
        wordDao.delete(word)
    }

    suspend fun updateLearnedStatus(wordId: Int, isLearned: Boolean) {
        wordDao.updateLearnedStatus(wordId, isLearned)
    }
}