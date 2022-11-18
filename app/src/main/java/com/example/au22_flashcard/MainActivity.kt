package com.example.au22_flashcard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {


    lateinit var wordView: TextView
    var currentWord: Word? = null
    val wordList = WordList()

    lateinit var addWordButton: Button


    private lateinit var job: Job
    lateinit var db: AppDatabase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        job = Job()

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "DatabaseWord")
            .fallbackToDestructiveMigration()
            .build()
        db = AppDatabase.getInstance(this)

        wordView = findViewById(R.id.wordTextView)
        addWordButton = findViewById(R.id.newWordButton)


        showNewWord()


        wordView.setOnClickListener {
            revealTranslation()
        }

        addWordButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            Log.d("!!!", "Nu körs onclick")
        }
    }


    override fun onResume() {
        super.onResume()
        wordList.clearList()
        wordList.initializeWords()
        launch {
            val addedWords = loadAllItems()
            val list = addedWords.await()
            addWord(list as MutableList<Word>)
        }
    }
    private fun revealTranslation() {
        wordView.text = currentWord?.english
    }
    private fun loadAllItems(): Deferred<List<Word>> = async(Dispatchers.IO) {
            db.wordDao().getAll()
        }
    private fun showNewWord() {
        currentWord = wordList.getNewWord()
        wordView.text = currentWord?.swedish
    }

    private fun addWord(list: MutableList<Word>) {
        for (word in list) {
            wordList.addWord(word)

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }
        return true
    }
}


//VAD SKALL GÖRAS
//1. Skapa en ny aktivitet där ett nytt ord får skrivas in
//2. Spara det nya ordet i databasen.
//3. I MainActivity, läs in alla ord från databasen.
//(använd av coroutiner nör ni läser och skriver till databasen, se tidigare exempel.