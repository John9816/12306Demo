package com.example.a12306;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306.my.MyFragment;

//登陆界面
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtUsername,edtPassword;
    private Button btnLogin;
    private CheckBox ckLogin;
    private TextView tvLostPassword;
    private String username,password;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        readAccount();
    }
    //读取本地用户和密码
    private void readAccount() {
        //创建SharedPreferences对象
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名和密码
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        Log.d(TAG, "readAccount: "+username);
        //在用户名和密码的输入框中显示用户名和密码
        edtUsername.setText(username);
        edtPassword.setText(password);
        }

    private void initView() {
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        tvLostPassword = (TextView)findViewById(R.id.tvLostPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        ckLogin = (CheckBox)findViewById(R.id.ckLogin);
        btnLogin.setOnClickListener(this);
        String url = "<a href='http://www.12306.cn'>忘记密码？</a>";
        CharSequence charSequence = Html.fromHtml(url);
        tvLostPassword.setText(charSequence);
        tvLostPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                username = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                //创建sharedPreference对象，info表示文件名，MODE_PRIVATE表示访问权限为私有的
                SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                String passwords = sp.getString("password", "");

                if(username.equals("dong") && password.equals("password")){
                    //勾选自动登陆
                    if(ckLogin.isChecked())
                    {
                        //创建sharedPreference对象，info表示文件名，MODE_PRIVATE表示访问权限为私有的
                        sp = getSharedPreferences("info", MODE_PRIVATE);
                        //获得sp的编辑器
                        SharedPreferences.Editor ed = sp.edit();
                        //以键值对的显示将用户名和密码保存到sp中
                        ed.putString("username", username);
                        ed.putString("password", password);
                        //提交用户名和密码
                        ed.commit();
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"密码错误请重新输入",Toast.LENGTH_SHORT).show();
                    break;
                }

        }
    }}
