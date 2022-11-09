package com.example.au22_flashcard

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun wordDao(): WordDao


    //gemensam för alla objekt som skapas utifrån klassen
    //returnerar samma databas i hela projektet, så det inte skapas olika databaser i samma app.
    companion object {

        @Volatile
        //hoppar över cache lagringar som görs annars
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            //låser tråden så den inte körs i olika trådar samtidigt
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {


                    //skapar databasen
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "word_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }

                return instance
            }


        }


    }

}