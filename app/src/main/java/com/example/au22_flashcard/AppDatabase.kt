package com.example.au22_flashcard

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Word::class],
    version = 1)

abstract class AppDatabase : RoomDatabase () {
    abstract fun wordDao() : WordDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                // om INSTANCE är inte null, då returneras den.
                // Om det är null, då skapar det en databas.
                if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "DatabaseWord"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                        INSTANCE = instance
                    }
                    // Return database.
                    return instance
                }
            }
        }
    }
