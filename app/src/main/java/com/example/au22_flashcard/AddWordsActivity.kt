package com.example.au22_flashcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddWordsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_words)


        val doneButton = findViewById<Button>(R.id.doneButton)
        doneButton.setOnClickListener {
            finish()
        }
    }
}