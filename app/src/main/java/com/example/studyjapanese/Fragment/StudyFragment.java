package com.example.studyjapanese.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyjapanese.R;
import com.example.studyjapanese.wordDB.Word;
import com.example.studyjapanese.wordDB.WordDao;
import com.example.studyjapanese.wordDB.WordDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StudyFragment extends Fragment {
    private WordDao mWordDao;

    //textView 뒤집기
    private TextView clickTextView;
    private boolean viewClickCheck;
    Button studyNext, studyPrev;
    List<Word> wordList;
    int i = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_study, container, false);

        //DB받아오기
        WordDatabase database = Room.databaseBuilder(rootView.getContext(), WordDatabase.class, "Word_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mWordDao = database.wordDao();

        //inclue layout의 textView 뒤집기

        //Init
        wordList = mWordDao.getWordAll();
        clickTextView = rootView.findViewById(R.id.cardTextView);
        viewClickCheck = true;
        studyNext = rootView.findViewById(R.id.studyNext);
        studyPrev = rootView.findViewById(R.id.studyPrev);

        //버튼 클릭
        buttonNextPrev();

        //초기값
        clickTextView.setText("단어 : " + wordList.get(0).getWordWord() + "");

        //카드를 눌렀을 시
        clickTextView.setOnClickListener(new View.OnClickListener() {
            //텍스트를 누르면 Y축 90식 회전을 시키는데
            @Override
            public void onClick(View v) {
                clickTextView.animate().withLayer()
                        .rotationY(90) //Y축 회전
                        .setDuration(150) //시간
                        .withEndAction(new Runnable() {
                            //반복해서 누를 때 마다 스레드로 계속해서 바꿔주는 것
                            @Override
                            public void run() {
                                //앞
                                if (!viewClickCheck) {
                                    clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                    viewClickCheck = true;
                                }
                                //뒤
                                else {
                                    clickTextView.setText("의미 : " + wordList.get(i).getWordMean() + "");
                                    viewClickCheck = false;
                                }
                                clickTextView.setRotationY(-90); //Y축 복구
                                clickTextView.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250) //시간
                                        .start();
                            }
                        }).start();
            }
        });

        return rootView;
    }

    private void buttonNextPrev() {

        studyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    clickTextView.animate().withLayer()
                            .rotationY(90)
                            .setDuration(150) //시간
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.setRotationY(-90);
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250)
                                                .start();
                                        viewClickCheck = true;
                                    }
                                    catch (Exception e){
                                        i--;
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.setRotationY(-90);
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250)
                                                .start();
                                        viewClickCheck = true;
                                        Toast.makeText(getContext(), "마지막 페이지.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).start();
                    i++;
            }
        });

        studyPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    clickTextView.animate().withLayer()
                            .rotationY(90)
                            .setDuration(150) //시간
                            .withEndAction(new Runnable() {
                                //반복해서 누를 때 마다 스레드로 계속해서 바꿔주는 것
                                @Override
                                public void run() {
                                    try {
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250) //시간
                                                .start();
                                        viewClickCheck = true;
                                    }
                                    //db 범위 초과 시
                                    catch (Exception e){
                                        i++;
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250) //시간
                                                .start();
                                        viewClickCheck = true;
                                        Toast.makeText(getContext(), "첫 페이지.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).start();
                    i--;
            }
        });
    }
}