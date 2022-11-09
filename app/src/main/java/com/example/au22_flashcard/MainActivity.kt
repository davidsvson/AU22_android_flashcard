package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    lateinit var wordView: TextView
    private val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()
    var currentWord: Word? = null
    lateinit var db: AppDataBase
    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDataBase.getInstance(this)
        job = Job()
        fillList()


        val addBtn = findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener {
            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)
        }




        wordView = findViewById(R.id.wordTextView)

        wordView.setOnClickListener {
            revealTranslation()
        }

    }

    override fun onResume() {
        super.onResume()
        fillList()
    }

    fun fillList() {
        Log.d("!!!", "FillList")
        val newList = loadAllItems()
        launch {
            val listOfword = newList.await()
            for (words in listOfword) {
                wordList.add(words)
            }
            showNewWord()
        }
    }

    fun loadAllItems(): Deferred<List<Word>> =
        async(Dispatchers.IO) {
            Log.d("!!!", "loadAllItems")
            db.wordDao().getAllItems()
        }

    fun showNewWord() {
        Log.d("!!!", "showNewWord")

        currentWord = getNewWord()
        wordView.text = currentWord?.swedish
    }

    fun getNewWord(): Word {
        Log.d("!!!", "getNewWord")
        if (wordList.size == usedWords.size) {
            usedWords.clear()
        }

        var word: Word? = null

        do {
            val rnd = (0 until wordList.size).random()
            word = wordList[rnd]
        } while (usedWords.contains(word))

        usedWords.add(word!!)

        return word
    }


    fun revealTranslation() {
        Log.d("!!!", "revealTranslation")
        wordView.text = currentWord?.english
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("!!!", "onTouchEvent")
        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }
        return true
    }
}

//skapa en ny aktivitet där ett nytt ord får skrivas in, sparas i databasen.
//i main ska alla ord läsas in ifrån databasen
//använd coroutines för att läsa och skriva









