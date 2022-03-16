package com.example.studyjapanese.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyjapanese.R;
import com.example.studyjapanese.adapter.MusicRecyclerViewItem;
import com.example.studyjapanese.adapter.musicAdapter;
import com.example.studyjapanese.musicDB.music;
import com.example.studyjapanese.musicDB.musicDao;
import com.example.studyjapanese.musicDB.musicDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MusicFragment extends Fragment {
    private musicDao mmusicDao;

    //리스트 뷰 사용하기
    RecyclerView mRecyclerView;
    musicAdapter musicAdapter;
    ArrayList<MusicRecyclerViewItem> list;

    String artistName, musicName;

    LyricsFragment lyricsFragment;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        //RecyclerView 생성
        mRecyclerView = rootView.findViewById(R.id.music_recyclerView);
        list = new ArrayList<>();

        musicAdapter = new musicAdapter(list);
        mRecyclerView.setAdapter(musicAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //musicDB사용
        musicDatabase database = Room.databaseBuilder(rootView.getContext(), musicDatabase.class, "music_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mmusicDao = database.musicDao();

        //musicdb의 내용 이름들을 가져와서
        List<music> musicList = mmusicDao.getmusicAll();
        for (int i = 0; i < musicList.size(); i++) {
            artistName = musicList.get(i).getArtistName() + "";
            musicName = musicList.get(i).getMusicName() + "";
            //munuItems에 넣은 뒤 list에 넣는다
            addItem(artistName, musicName);
        }

        musicAdapter.notifyDataSetChanged();

        //recyclerView click event
        lyricsFragment = new LyricsFragment();
        musicAdapter.setOnItemClickListener(new musicAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                MusicRecyclerViewItem item = musicAdapter.getItem(pos);

                //넘길 때 아티스트명, 노래제목을 함께 넘긴다.
                //Lyrics.db (노래제목, 가사)에서 노래제목이 일치하는 가사를 가져와서 보여준다.
                Bundle result = new Bundle();
                result.putString("musicName", item.getSub()+"");
                lyricsFragment.setArguments(result);

                //리사이클 뷰의 특정 아이템 클릭시 lyricsFragment로 이동
                //View subview = inflater.inflate(R.layout.activity_main, container, false);
                getFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
            }
        });

        return rootView;
    }

    private void addItem(String main, String sub) {
        MusicRecyclerViewItem item = new MusicRecyclerViewItem();
        item.setMain(main);
        item.setSub(sub);

        list.add(item);
    }
}