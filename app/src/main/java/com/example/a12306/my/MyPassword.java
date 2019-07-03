package com.example.a12306.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.AlteredCharSequence;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;
//我的密码
public class MyPassword extends AppCompatActivity {
    private EditText edtPassword, edtagainPassword;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_password);
        initView();
    }

    private void initView() {
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtagainPassword = (EditText) findViewById(R.id.edtagainPassword);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = edtPassword.getText().toString().trim();
                String againpassword = edtagainPassword.getText().toString().trim();
                if (againpassword.equals(password)) {
                    SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("password", password);
                    ed.commit();
                    Intent intent = new Intent(MyPassword.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MyPassword.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }
            }
        });
    }}



