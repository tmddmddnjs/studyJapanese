package com.example.studyjapanese;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studyjapanese.musicDB.music;
import com.example.studyjapanese.musicDB.musicDatabase;
import com.example.studyjapanese.wordDB.Word;
import com.example.studyjapanese.wordDB.WordDao;
import com.example.studyjapanese.wordDB.WordDatabase;

public class insertStudyActivity extends AppCompatActivity {
    private WordDao mWordDao;

    Button allDeleteButton, allInsertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_study);

        //DB받아오기
        WordDatabase database = Room.databaseBuilder(getApplicationContext(), WordDatabase.class, "Word_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mWordDao = database.wordDao();

        //music 데이터 클리어
        allDeleteButton = (Button)findViewById(R.id.allDeleteButton);
        allDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDao.deleteAll();
                Toast.makeText(insertStudyActivity.this, "단어 삭제 완료", Toast.LENGTH_SHORT).show();
            }
        });

        Word uInsert = new Word();
        //music에 데이터 다시 넣기
        allInsertButton = (Button)findViewById(R.id.allInsertButton);
        allInsertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uInsert.setWordWord("6畳半(６じょうはん)");
                uInsert.setWordMean("6장반");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("灯らない(ともらない)");
                uInsert.setWordMean("비치지 않는");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("幼少期(ようしょうき)");
                uInsert.setWordMean("유년기");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("燦然（さんぜん）");
                uInsert.setWordMean("반짝반짝 빛나는");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("対照的（たいしょうてき）");
                uInsert.setWordMean("대조적");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("家路（いえじ）");
                uInsert.setWordMean("집으로 가는 길");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("透ける（すける）");
                uInsert.setWordMean("비치다");
                mWordDao.setInsertWord(uInsert);

                uInsert.setWordWord("退廃的（たいはいてき）");
                uInsert.setWordMean("퇴폐적");
                mWordDao.setInsertWord(uInsert);

                Toast.makeText(insertStudyActivity.this, "단어 입력 완료", Toast.LENGTH_SHORT).show();
            }
        });
    }
}