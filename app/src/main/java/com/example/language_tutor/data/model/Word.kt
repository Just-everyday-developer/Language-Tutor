// data/model/Word.kt
package com.example.language_tutor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    val translation: String,
    val isLearned: Boolean = false,
    val level: String
)