package com.example.studyjapanese.lyricsDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = lyrics.class, version = 1)
public abstract class lyricsDatabase extends RoomDatabase {
    public abstract  lyricsDao lyricsDao();
}
