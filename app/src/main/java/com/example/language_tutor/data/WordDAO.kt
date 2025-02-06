package com.example.test.data

import androidx.room.*

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("SELECT * FROM Word ORDER BY level")
    suspend fun getAllWords(): List<Word>

    @Query("UPDATE Word SET isLearned = :isLearned WHERE id = :wordId")
    suspend fun updateLearnedStatus(wordId: Int, isLearned: Boolean)
}
