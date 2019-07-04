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
                    SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString("password", password);
                    ed.putBoolean("MODIFYPASSWORD",true);
                    ed.putBoolean("ISCHECKED",false);
                    ed.commit();
                    Log.d(TAG, "onClick: "+password);
                    Intent intent = new Intent(MyPassword.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(MyPassword.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                }
            }
        });
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



