package com.example.studyjapanese.lyricsDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface lyricsDao {
    @Insert
    void setInsertlyrics(lyrics lyrics);

    @Query("SELECT * from lyrics")
    List<lyrics> getlyricsAll();

    @Query("DELETE FROM lyrics")
    void deleteAll();
}
