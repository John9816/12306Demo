package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;
import com.example.a12306.utils.CONSTANT;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//我的密码
public class MyPassword extends Activity {
    private EditText edtPassword, edtagainPassword;
    private Button btnSave;
    private static final String TAG = "MyPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_password);
        initView();
        /* 显示App icon左侧的back键 */
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
                   Update(againpassword);
                    Intent intent = new Intent(MyPassword.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MyPassword.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Update(final String newPassword) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                SharedPreferences preferences = MyPassword.this.getSharedPreferences("userinfo", MODE_PRIVATE);
                String value = preferences.getString("cookie", "");
                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("newPassword",newPassword)
                            .add("action","update")
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("Cookie",value)
                            .url(CONSTANT.HOST + "/otn/AccountPassword")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "run: "+responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //ActionBar重写的方法
    @Override
    public boolean onOptionsItemSelected(   MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }
}



