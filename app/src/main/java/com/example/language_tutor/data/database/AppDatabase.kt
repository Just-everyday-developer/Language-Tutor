// data/database/AppDatabase.kt
package com.example.language_tutor.data.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.language_tutor.data.model.Word
import com.example.language_tutor.data.dao.WordDao

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "word_database"
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Initial data
                        db.execSQL("INSERT INTO Word (word, translation, isLearned, level) VALUES ('apple', 'яблоко', 0, 'A1')")
                        db.execSQL("INSERT INTO Word (word, translation, isLearned, level) VALUES ('table', 'стол', 0, 'A1')")
                        db.execSQL("INSERT INTO Word (word, translation, isLearned, level) VALUES ('car', 'машина', 0, 'A2')")
                        db.execSQL("INSERT INTO Word (word, translation, isLearned, level) VALUES ('universe', 'вселенная', 0, 'B2')")
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}