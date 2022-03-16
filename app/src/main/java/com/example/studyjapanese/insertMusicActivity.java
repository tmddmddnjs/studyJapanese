package com.example.studyjapanese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studyjapanese.musicDB.music;
import com.example.studyjapanese.musicDB.musicDao;
import com.example.studyjapanese.musicDB.musicDatabase;

public class insertMusicActivity extends AppCompatActivity {
    private musicDao mmusicDao;

    Button allDeleteButton, allInsertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_music);

        musicDatabase database = Room.databaseBuilder(getApplicationContext(), musicDatabase.class, "music_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mmusicDao = database.musicDao();

        //music 데이터 클리어
        allDeleteButton = (Button)findViewById(R.id.allDeleteButton);
        allDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mmusicDao.deleteAll();
                Toast.makeText(insertMusicActivity.this, "노래 삭제 완료", Toast.LENGTH_SHORT).show();
            }
        });

        music uInsert = new music();
        //music에 데이터 다시 넣기
        allInsertButton = (Button)findViewById(R.id.allInsertButton);
        allInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uInsert.setArtistName("yama");
                uInsert.setMusicName("夜を告げる");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("yama");
                uInsert.setMusicName("幽霊東京");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("ウォルピス");
                uInsert.setMusicName("泥中に咲く");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("Aimer");
                uInsert.setMusicName("恋は雨上がりのように");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("YOASOBI");
                uInsert.setMusicName("もう少しだけ");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("花たん");
                uInsert.setMusicName("空想少女への恋手紙");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("ヨルシカ");
                uInsert.setMusicName("だから僕は音楽を辞めた");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("ヨルシカ");
                uInsert.setMusicName("雨とカプチーノ");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("Aimer");
                uInsert.setMusicName("カタオモイ");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("EGOIST");
                uInsert.setMusicName("雨、君を連れて");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("花たん");
                uInsert.setMusicName("夜明けと蛍");
                mmusicDao.setInsertmusic(uInsert);

                uInsert.setArtistName("花たん");
                uInsert.setMusicName("心做し");
                mmusicDao.setInsertmusic(uInsert);

                Toast.makeText(insertMusicActivity.this, "노래 입력 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}