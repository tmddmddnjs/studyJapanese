package com.example.studyjapanese.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    //삽입, (onConflict = OnConflictStrategy.IGNORE) 중복 무시
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void setInsertUser(User user);

    //수정
    @Update
    void setUpdateUser(User user);

    //삭제
    @Delete
    void setDeleteUser(User user);

    //DB 요청 명령문
    @Query("SELECT * from User")
    List<User> getUserAll();

    @Query("DELETE FROM User ")
    void deleteAll();
}
