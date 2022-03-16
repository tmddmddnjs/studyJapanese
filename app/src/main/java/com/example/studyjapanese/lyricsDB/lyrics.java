package com.example.studyjapanese.lyricsDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class lyrics {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String musicName;
    private String musicLyrics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicLyrics() {
        return musicLyrics;
    }

    public void setMusicLyrics(String musicLyrics) {
        this.musicLyrics = musicLyrics;
    }
}
