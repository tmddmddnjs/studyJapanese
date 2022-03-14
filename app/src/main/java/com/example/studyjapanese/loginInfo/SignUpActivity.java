package com.example.studyjapanese.loginInfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class SignUpActivity extends AppCompatActivity {
    private EditText et_id, et_pass, et_name, et_age;
    private Button btn_register;

    public void Init(){
        // 아이디 값 찾아주기
        et_id = (EditText)findViewById(R.id.et_id);
        et_pass = (EditText)findViewById(R.id.et_pass);
        et_name = (EditText)findViewById(R.id.et_name);
        et_age = (EditText)findViewById(R.id.et_age);
        //회원가입 버튼
        btn_register = (Button)findViewById(R.id.btn_register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        this.Init();
    }
}