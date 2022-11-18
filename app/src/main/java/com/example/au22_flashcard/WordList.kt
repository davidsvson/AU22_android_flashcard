package com.example.au22_flashcard

class WordList() {
    val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()

    init {
        initializeWords()
    }


    fun initializeWords() {
        val word = Word(0, "Hello", "Hej")
        wordList.add(word)
        wordList.add(Word(0, "Good bye", "Hej då"))
        wordList.add(Word(0, "Thank you", "Tack"))
        wordList.add(Word(0, "Welcome", "Välkommen"))
        wordList.add(Word(0, "Computer", "Dator"))

    }


    fun addWord(word : Word) {
        wordList.add(word)
    }

    fun clearList() {
        wordList.clear()
    }


    fun getNewWord(): Word {
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
    }









