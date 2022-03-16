package com.example.studyjapanese.musicDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class music {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String artistName;
    private String musicName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
}
