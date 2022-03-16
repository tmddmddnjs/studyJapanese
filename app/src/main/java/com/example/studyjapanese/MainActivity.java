package com.example.studyjapanese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studyjapanese.Fragment.LookupFragment;
import com.example.studyjapanese.Fragment.LyricsFragment;
import com.example.studyjapanese.Fragment.MusicFragment;
import com.example.studyjapanese.Fragment.SettingFragment;
import com.example.studyjapanese.Fragment.StudyFragment;
import com.example.studyjapanese.loginInfo.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    //BottomNavigationBar에 쓰이는 Fragment들
    MusicFragment musicFragment;
    LyricsFragment lyricsFragment;
    LookupFragment lookupFragment;
    StudyFragment studyFragment;
    SettingFragment settingFragment;
    //로그인 전, 후마다 다른 xml가져올거
    LinearLayout before_after_login;

    //변수 초기화
    public void Init(){
        //BottomNavigationBar에 쓰이는 Fragment들
        musicFragment = new MusicFragment();
        lyricsFragment = new LyricsFragment();
        lookupFragment = new LookupFragment();
        studyFragment = new StudyFragment();
        settingFragment = new SettingFragment();
        //activity_main에 있는 before_after_login_Layout라는 이름의 LinearLayout을 가져옴
        //로그인 전에는 before_login.xml을 로그인 후에는 after_login.xml사용할거임.
        before_after_login = findViewById(R.id.before_after_login_Layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //변수 초기화
        Init();

        //초기 Fragment 화면 설정
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

        Intent intent = getIntent();
        String getName = intent.getExtras().getString("이름");

        //activity_main에 있는 before_after_login화면서 after_login.xml을 넣는다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.after_login, before_after_login, true);
        //로그인시 받아온 유저 이름으로 넣기
        TextView name = (TextView)findViewById(R.id.afterLoginTextView);
        name.setText(getName + "님 환영합니다.");
    }
}