package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var wordView : TextView
    var currentWord : Word? = null
    val wordList = WordList()
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getInstance(this)

        wordView = findViewById(R.id.wordTextView)

        showNewWord()

        wordView.setOnClickListener {
            revealTranslation()
        }

        val addWordsButton = findViewById<Button>(R.id.addWordsButton)
        addWordsButton.setOnClickListener {
            val intent = Intent(this, AddWordsActivity::class.java)
            startActivity(intent)
        }


    }

    fun revealTranslation() {
        wordView.text = currentWord?.english
    }


    fun showNewWord() {

        currentWord = wordList.getNewWord()
        wordView.text = currentWord?.swedish
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }

        return true
    }
}

//1. skapa en ny activity där ett nytt ord får skrivas in
//2. spara det nya ordet i databasen
//3. I main activity läs in alla ord från databasen
//använd coroutiner när ni läser och skriver till databasen se tidigare exempel