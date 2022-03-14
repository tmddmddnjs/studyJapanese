package com.example.studyjapanese.loginInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    private EditText et_id, et_pass;
    //로그인 회원가입 버튼
    private Button btn_login, btn_register;

    public void Init(){
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        //로그인, 회원가입 버튼
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.Init();
    }
}