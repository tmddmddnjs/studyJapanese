package com.example.studyjapanese.wordDB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void setInsertWord(Word word);

    @Query("SELECT * from Word")
    List<Word> getWordAll();

    @Query("DELETE FROM Word")
    void deleteAll();
}
