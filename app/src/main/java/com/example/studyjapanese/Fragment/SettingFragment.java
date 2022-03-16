package com.example.studyjapanese.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.studyjapanese.MainActivity;
import com.example.studyjapanese.R;
import com.example.studyjapanese.insertLyricsActivity;
import com.example.studyjapanese.insertMusicActivity;


public class SettingFragment extends Fragment {
    Button inputMusicData, inputLyricsData;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        //Init
        inputMusicData = (Button)rootView.findViewById(R.id.inputMusicData);
        inputLyricsData = (Button)rootView.findViewById(R.id.inputLyricsData);

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

        return rootView;
    }
}