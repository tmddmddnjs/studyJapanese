package com.example.studyjapanese.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.studyjapanese.R;
import com.example.studyjapanese.lyricsDB.lyrics;
import com.example.studyjapanese.lyricsDB.lyricsDao;
import com.example.studyjapanese.lyricsDB.lyricsDatabase;
import com.example.studyjapanese.musicDB.music;
import com.example.studyjapanese.musicDB.musicDao;
import com.example.studyjapanese.musicDB.musicDatabase;

import org.w3c.dom.Text;

import java.util.List;

public class LyricsFragment extends Fragment {
    private lyricsDao mlyricsDao;

    String musicFragment_musicName;
    String musicName;
    TextView lyricsMusicName, lyricsText;
    String temp;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyrics, container, false);

        //Init
        lyricsMusicName = (TextView)rootView.findViewById(R.id.lyricsMusicName);
        lyricsText = (TextView)rootView.findViewById(R.id.lyricsText);

        //db이용
        //musicDB사용
        lyricsDatabase database = Room.databaseBuilder(rootView.getContext(), lyricsDatabase.class, "lyrics_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mlyricsDao = database.lyricsDao();

        //Bundle로 값 가져오기
        Bundle result = getArguments();
        if(result != null){
            musicFragment_musicName = result.getString("musicName");
            lyricsMusicName.setText("노래명 : " + musicFragment_musicName);
        }


        //db하나씩 탐색하기
        //musicdb의 내용 이름들을 가져와서
        List<lyrics> lyricsist = mlyricsDao.getlyricsAll();
        for (int i = 0; i < lyricsist.size(); i++) {
            musicName = lyricsist.get(i).getMusicName()+"";
            //bundle로 받아온 musicFragment_musicName lyrics.db의 musicName이 같으면
            //lyrics.db의 해당 lyrics을 가져옴.
            if(musicName.equals(musicFragment_musicName)){
                temp = lyricsist.get(i).getMusicLyrics() + "";
                lyricsText.setText(lyricsist.get(i).getMusicLyrics() + "");
            }
        }

        return rootView;
    }
}