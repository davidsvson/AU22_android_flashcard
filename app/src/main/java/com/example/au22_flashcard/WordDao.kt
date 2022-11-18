package com.example.au22_flashcard

import androidx.room.*

@Dao
interface WordDao {
    @Insert
    fun insert(word: Word)

    @Query("SELECT * FROM word_table")
    fun getAll() : List<Word>

    @Delete
    fun delete(word: Word)
}


