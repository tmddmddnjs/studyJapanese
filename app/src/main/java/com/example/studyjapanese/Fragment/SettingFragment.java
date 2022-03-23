package com.example.studyjapanese.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.studyjapanese.MainActivity;
import com.example.studyjapanese.R;
import com.example.studyjapanese.insertLyricsActivity;
import com.example.studyjapanese.insertMusicActivity;
import com.example.studyjapanese.lyricsDB.lyricsDatabase;
import com.example.studyjapanese.wordDB.WordDao;
import com.example.studyjapanese.wordDB.WordDatabase;


public class SettingFragment extends Fragment {
    Button inputMusicData, inputLyricsData, inputWordData;
    private WordDao mWordDao;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        //Init
        inputMusicData = (Button)rootView.findViewById(R.id.inputMusicData);
        inputLyricsData = (Button)rootView.findViewById(R.id.inputLyricsData);
        inputWordData = (Button)rootView.findViewById(R.id.inputWordData);

        inputMusicData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), insertMusicActivity.class);
                startActivity(intent);
            }
        });

        inputLyricsData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), insertLyricsActivity.class);
                startActivity(intent);
            }
        });

        WordDatabase database = Room.databaseBuilder(rootView.getContext(), WordDatabase.class, "Word_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mWordDao = database.wordDao();

        inputWordData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDao.deleteAll();
                Toast.makeText(getActivity(), "모든 단어 삭제 완료", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}