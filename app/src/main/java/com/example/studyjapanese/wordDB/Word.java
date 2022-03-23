package com.example.studyjapanese.wordDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String wordWord;
    private String wordMean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWordWord() {
        return wordWord;
    }

    public void setWordWord(String wordWord) {
        this.wordWord = wordWord;
    }

    public String getWordMean() {
        return wordMean;
    }

    public void setWordMean(String wordMean) {
        this.wordMean = wordMean;
    }
}
