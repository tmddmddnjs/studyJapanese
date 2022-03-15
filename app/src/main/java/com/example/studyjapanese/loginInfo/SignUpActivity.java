package com.example.studyjapanese.loginInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText signUp_userId, signUp_userPass, signUp_userName, signUp_userAge;
    private Button signUp_btn_register, allDelete;

    //ROOM이용
    private UserDao mUserDao;
    private String id, pass, name, age;

    public void Init(){
        // 아이디 값 찾아주기
        signUp_userId = (EditText)findViewById(R.id.signUp_userId);
        signUp_userPass = (EditText)findViewById(R.id.signUp_userPass);
        signUp_userName = (EditText)findViewById(R.id.signUp_userName);
        signUp_userAge = (EditText)findViewById(R.id.signUp_userAge);
        //회원가입 버튼
        signUp_btn_register = (Button)findViewById(R.id.signUp_btn_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.Init();

        //UserDatabase를 이용한 db생성인데 tmddmddnjs_db는 table이름.
        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "tmddmddnjs_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        //uUserDao는 UserDao 인터페이스를 이용해, 삽입 갱신등을 함. 예시는 아래 삽입, 수정, 삭제로 확인해보자.
        mUserDao = database.userDao();

        signUp_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User uInsert = new User();
                id = signUp_userId.getText().toString();
                pass = signUp_userPass.getText().toString();
                name = signUp_userName.getText().toString();
                age = signUp_userAge.getText().toString();

                //edittext에 빈칸이 있으면 안된다.
                if(id.equals("")){
                    Toast.makeText(SignUpActivity.this,"아이디가 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(pass.equals("")){
                    Toast.makeText(SignUpActivity.this,"비밀번호가 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(name.equals("")){
                    Toast.makeText(SignUpActivity.this,"이름이 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(age.equals("")){
                    Toast.makeText(SignUpActivity.this,"나이가 업습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //중복확인
                List<User> userList = mUserDao.getUserAll();
                for (int i = 0; i < userList.size(); i++){
                    String checkId = userList.get(i).getUserId() + "";
                    if(checkId.equals(id + "")){
                        Toast.makeText(SignUpActivity.this,"아이디가 중복되었습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                //중복이 없을 시 db에 데이터를 insert
                uInsert.setUserId(id);
                uInsert.setUserPass(pass);
                uInsert.setUserName(name);
                uInsert.setUserAge(age);

                Toast.makeText(SignUpActivity.this,"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                mUserDao.setInsertUser(uInsert);

                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        allDelete = (Button)findViewById(R.id.allDelete);
        allDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDao.deleteAll();
            }
        });
    }
}