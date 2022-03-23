package com.example.studyjapanese.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.studyjapanese.R;
import com.example.studyjapanese.wordDB.Word;
import com.example.studyjapanese.wordDB.WordDao;
import com.example.studyjapanese.wordDB.WordDatabase;

import java.util.List;


public class StudyFragment extends Fragment {
    private WordDao mWordDao;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_study, container, false);

        //DB받아오기
        WordDatabase database = Room.databaseBuilder(rootView.getContext(), WordDatabase.class, "Word_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mWordDao = database.wordDao();



        return rootView;
    }
}