package com.example.au22_flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.room.Room
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


 class SecondActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var addenglishWord: EditText
    private lateinit var addswedishWord: EditText
    private lateinit var addNewWordButton: Button

    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d("!!!", "här körs onCreate")

        job = Job()
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "DatabaseWord")
            .fallbackToDestructiveMigration()
            .build()
        db = AppDatabase.getInstance(this)


        addenglishWord = findViewById(R.id.englishWord)
        addswedishWord = findViewById(R.id.swedishWord)

        addNewWordButton = findViewById(R.id.addNewWordButton)
        addNewWordButton.setOnClickListener {
            addWordToDatabase()
            finish()
        }
    }

    private fun addWordToDatabase() {

        val sweWord = addswedishWord.text.toString()
        val engWord = addenglishWord.text.toString()
        if (sweWord.isNotEmpty() && engWord.isNotEmpty()) {
            val word = Word(0, sweWord, engWord)
            launch(Dispatchers.IO) {
                db.wordDao().insert(word)
            }
        }
    }
}





