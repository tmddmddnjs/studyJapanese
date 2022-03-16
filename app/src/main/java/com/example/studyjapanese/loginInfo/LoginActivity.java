package com.example.studyjapanese.loginInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.studyjapanese.Database.User;
import com.example.studyjapanese.Database.UserDao;
import com.example.studyjapanese.Database.UserDatabase;
import com.example.studyjapanese.MainActivity;
import com.example.studyjapanese.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText login_userId, login_userPass;
    //로그인 회원가입 버튼
    private Button login_btn_login, login_btn_register;

    //ROOM이용
    private UserDao mUserDao;
    private String id, pass, putName;

    public void Init(){
        login_userId = findViewById(R.id.login_userId);
        login_userPass = findViewById(R.id.login_userPass);
        //로그인, 회원가입 버튼
        login_btn_login = findViewById(R.id.login_btn_login);
        login_btn_register = findViewById(R.id.login_btn_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.Init();

        //UserDatabase를 이용한 db생성인데 tmddmddnjs_db는 table이름.
        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "tmddmddnjs_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        //uUserDao는 UserDao 인터페이스를 이용해, 삽입 갱신등을 함. 예시는 아래 삽입, 수정, 삭제로 확인해보자.
        mUserDao = database.userDao();

        //로그인 버튼 클릭 시
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login", "로그인 버튼");

                boolean check = false;
                id = login_userId.getText().toString();
                pass = login_userPass.getText().toString();

                //빈칸 확인
                if(id.equals("")){
                    Toast.makeText(LoginActivity.this,"아이디가 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pass.equals("")){
                    Toast.makeText(LoginActivity.this,"비밀번호가 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<User> userList = mUserDao.getUserAll();
                for (int i = 0; i < userList.size(); i++){
                    String checkId = userList.get(i).getUserId() + "";
                    if(checkId.equals(id + "")){
                        check = true;
                        //check가 false면 아이디가 일치하지 않기 때문에 넘어가지 않고 return시킴
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //회원 이름 정보 넘겨주기
                        putName = userList.get(i).getUserName() + "";
                        intent.putExtra("이름", putName);
                        startActivity(intent);
                    }
                }
                //check가 false인 경우에만 다음을 실행
                if(!check) {
                    Toast.makeText(LoginActivity.this, "아이디가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //회원가입 클릭 시
        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("register", "회원가입버튼");
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}