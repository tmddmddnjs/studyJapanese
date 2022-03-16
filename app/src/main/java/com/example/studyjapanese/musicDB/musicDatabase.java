package com.example.studyjapanese.musicDB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = music.class, version = 1)
public abstract class musicDatabase extends RoomDatabase {
    public abstract musicDao musicDao();
}
