package com.example.studyjapanese.loginInfo;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.studyjapanese.MainActivity;
import com.example.studyjapanese.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText login_userId, login_userPass;
    //로그인 회원가입 버튼
    private Button login_btn_login, login_btn_register;

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

        //로그인 버튼 클릭 시
        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("login", "로그인 버튼");
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //회원가입 클릭 시
        login_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("register", "회원가입 버튼");
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}