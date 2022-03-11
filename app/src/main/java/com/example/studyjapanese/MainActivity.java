package com.example.studyjapanese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    MusicFragment musicFragment;
    LyricsFragment lyricsFragment;
    LookupFragment lookupFragment;
    StudyFragment studyFragment;
    SettingFragment settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musicFragment = new MusicFragment();
        lyricsFragment = new LyricsFragment();
        lookupFragment = new LookupFragment();
        studyFragment = new StudyFragment();
        settingFragment = new SettingFragment();

        //초기 화면 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();
        //bottom_mune의 xml을 가져옴.
        BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    //bottom_menu의 item의 id를 가져온 것.
                    case R.id.first_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();
                        return true;
                    case R.id.second_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
                        return true;
                    case R.id.third_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, lookupFragment).commit();
                        return true;
                    case R.id.fourth_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, studyFragment).commit();
                        return true;
                    case R.id.fifth_tab:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}