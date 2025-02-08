// data/dao/WordDao.kt
package com.example.language_tutor.data.dao

import androidx.room.*
import com.example.language_tutor.data.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM Word ORDER BY level")
    fun getAllWords(): Flow<List<Word>>

    @Query("UPDATE Word SET isLearned = :isLearned WHERE id = :wordId")
    suspend fun updateLearnedStatus(wordId: Int, isLearned: Boolean)
}
