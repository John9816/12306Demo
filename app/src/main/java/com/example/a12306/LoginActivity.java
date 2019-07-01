package com.example.a12306;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.a12306.my.MyFragment;

//登陆界面
public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername,edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edtUsername = (EditText)findViewById(R.id.edtPassword);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(username.equals("123") && password.equals("123")){
                    Intent intent = new Intent(LoginActivity.this, MyFragment.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

}
