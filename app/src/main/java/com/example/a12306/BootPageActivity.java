package com.example.a12306;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
//引导页面
public class BootPageActivity extends AppCompatActivity implements View.OnClickListener {

    private int reclen = 5;//倒计时
    private TextView tv_rec;
    private final static int COUNT = 1;
    Timer timer = new Timer();//定时器
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗口全屏显示
        getWindow().setFlags(flag,flag);
        setContentView(R.layout.activity_boot_page);
        initView();
        //正常情况不点击跳过
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case COUNT:
                        tv_rec.setText(String.valueOf(reclen)+"s");
                        reclen--;
                        if(reclen == 0){
                            Intent intent = new Intent(BootPageActivity.this,LoginActivity.class);
                            startActivity(intent);
                            timer.cancel();
                            finish();
                        }
                }
            }
        };
    }

    private void initView() {
        //跳过
        tv_rec = (TextView)findViewById(R.id.tv_rec);
        //跳过监听
        tv_rec.setOnClickListener(this);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(COUNT);
            }
        },1000,1000);
    }

    //点击跳过
    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_rec:
                    Intent intent = new Intent(BootPageActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    timer.cancel();
            }
    }
}
