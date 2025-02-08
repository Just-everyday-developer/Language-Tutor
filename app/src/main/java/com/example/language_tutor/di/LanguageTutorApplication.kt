// di/LanguageTutorApplication.kt
package com.example.language_tutor.di

import android.app.Application
import com.example.language_tutor.data.database.AppDatabase
import com.example.language_tutor.data.repository.WordRepository

class LanguageTutorApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}