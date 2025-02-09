package com.example.language_tutor.data.repository

import com.example.language_tutor.data.model.QuizQuestion
import com.example.language_tutor.data.model.Word
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getQuizQuestions(): List<QuizQuestion> {
        return try {
            db.collection("quiz_questions")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(QuizQuestion::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWords(): List<Word> {
        return try {
            db.collection("words")
                .get()
                .await()
                .documents
                .mapNotNull { it.toObject(Word::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addWord(word: Word) {
        db.collection("words").add(word).await()
    }

    suspend fun deleteWord(wordId: String) {
        db.collection("words").document(wordId).delete().await()
    }
}
