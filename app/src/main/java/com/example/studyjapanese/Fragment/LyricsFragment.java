package com.example.studyjapanese.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyjapanese.R;
import com.example.studyjapanese.lyricsDB.lyrics;
import com.example.studyjapanese.lyricsDB.lyricsDao;
import com.example.studyjapanese.lyricsDB.lyricsDatabase;
import com.example.studyjapanese.musicDB.music;
import com.example.studyjapanese.musicDB.musicDao;
import com.example.studyjapanese.musicDB.musicDatabase;
import com.example.studyjapanese.wordDB.Word;
import com.example.studyjapanese.wordDB.WordDao;
import com.example.studyjapanese.wordDB.WordDatabase;

import org.w3c.dom.Text;

import java.util.List;

public class LyricsFragment extends Fragment {
    private lyricsDao mlyricsDao;
    private WordDao mWordDao;

    String musicFragment_musicName;
    String musicName;
    TextView lyricsMusicName, lyricsText;
    String temp;
    Button lyricsAddButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lyrics, container, false);

        //Init
        lyricsMusicName = (TextView) rootView.findViewById(R.id.lyricsMusicName);
        lyricsText = (TextView) rootView.findViewById(R.id.lyricsText);
        lyricsAddButton = (Button) rootView.findViewById(R.id.lyricsAddButton);

        //db이용
        //musicDB사용
        lyricsDatabase databaseLyrics = Room.databaseBuilder(rootView.getContext(), lyricsDatabase.class, "lyrics_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mlyricsDao = databaseLyrics.lyricsDao();

        //Bundle로 값 가져오기
        Bundle getMusicFragment = getArguments();
        if (getMusicFragment != null) {
            musicFragment_musicName = getMusicFragment.getString("musicName");
            lyricsMusicName.setText("노래명 : " + musicFragment_musicName);
        }


        //db하나씩 탐색하기
        //musicdb의 내용 이름들을 가져와서
        List<lyrics> lyricsist = mlyricsDao.getlyricsAll();
        for (int i = 0; i < lyricsist.size(); i++) {
            musicName = lyricsist.get(i).getMusicName() + "";
            //bundle로 받아온 musicFragment_musicName lyrics.db의 musicName이 같으면
            //lyrics.db의 해당 lyrics을 가져옴.
            if (musicName.equals(musicFragment_musicName)) {
                temp = lyricsist.get(i).getMusicLyrics() + "";
                lyricsText.setText(lyricsist.get(i).getMusicLyrics() + "");
            }
        }

        //단어 추가를 위한 버튼, 아래의 dialog 불러오기
        //DB받아오기
        WordDatabase databaseWord = Room.databaseBuilder(rootView.getContext(), WordDatabase.class, "Word_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mWordDao = databaseWord.wordDao();

        lyricsAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAlterDialog();
            }
        });

        return rootView;
    }


    public void MyAlterDialog(){
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.plus_word, null);

        //inflater로 plus_word.xml을 불러옴
        ad.setView(view)
                .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText plusWordAddWord = (EditText)view.findViewById(R.id.plusWordAddWord);
                        EditText plusWordAddMean = (EditText)view.findViewById(R.id.plusWordAddMean);

                        //텍스트 받아와서
                        String AddWord = plusWordAddWord.getText().toString();
                        String AddMean = plusWordAddMean.getText().toString();

                        //Word.db에 다이어로그에 입력되었던 단어를 insert
                        Word uInsert = new Word();
                        uInsert.setWordWord(AddWord + "");
                        uInsert.setWordMean(AddMean + "");
                        mWordDao.setInsertWord(uInsert);

                        dialog.dismiss();

                        Toast.makeText(getContext(),"단어가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //취소 버튼 클릭 시 아무것도 안함
                        dialog.dismiss();
                    }
                });
        ad.show();
    }
}