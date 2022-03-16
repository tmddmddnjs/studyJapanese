package com.example.studyjapanese.musicDB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.studyjapanese.Database.User;

import java.util.List;

@Dao
public interface musicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void setInsertmusic(music music);

    //DB 요청 명령문
    @Query("SELECT * from music")
    List<music> getmusicAll();

    @Query("DELETE FROM music")
    void deleteAll();
}
