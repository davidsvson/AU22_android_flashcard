package com.example.au22_flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddWordActivity : AppCompatActivity(), CoroutineScope {

    lateinit var sweText: EditText
    lateinit var engText: EditText
    private lateinit var job: Job
    private lateinit var db: AppDataBase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        sweText = findViewById(R.id.sweWordEdit)
        engText = findViewById(R.id.engWordEdit)

        job = Job()
        db = AppDataBase.getInstance(this)

        val backBtn = findViewById<Button>(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }


        val addBtn = findViewById<Button>(R.id.addWordBtn)
        addBtn.setOnClickListener {
            val newWord = Word(0, engText.text.toString(), sweText.text.toString())
            Log.d("!!!", "swe : ${newWord.swedish} + eng ${newWord.english}")
            launch {
                saveNewWord(newWord)

            }
            Log.d("!!!", "Adding...")
            finish()
        }


    }

    fun saveNewWord(word: Word) {
        launch(Dispatchers.IO) {
            db.wordDao().insert(word)
            Log.d("!!!", "New word saved")
        }


    }
}