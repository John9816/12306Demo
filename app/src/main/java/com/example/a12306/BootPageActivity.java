package com.example.a12306;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a12306.utils.CONSTANT;
import com.example.a12306.utils.NetUtils;
import com.gyf.immersionbar.ImmersionBar;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;
import okhttp3.Headers;
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
//引导页面
public class BootPageActivity extends AppCompatActivity implements View.OnClickListener {

    private int reclen = 5;//倒计时
    private TextView tv_rec;
    private final static int COUNT = 3;
    Timer timer = new Timer();//定时器
    private Handler handler;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;
    private static final String TAG = "BootPageActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*//定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗口全屏显示
        getWindow().setFlags(flag,flag);*/
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
                        preferences = getSharedPreferences("userinfo",MODE_PRIVATE);
                        if(reclen == 0){
                            //1、检查网络连接是否正常
                            if (!NetUtils.check(BootPageActivity.this)) {
                                Toast.makeText(BootPageActivity.this, "网络异常，请检查！", Toast.LENGTH_LONG).show();
                                return;//后续代码不执行
                            }else if(preferences.getString("password","").isEmpty()){
                                    Intent intent = new Intent(BootPageActivity.this,LoginActivity.class);
                                    Log.d(TAG, "handleMessage: "+"未点击跳过");
                                    startActivity(intent);
                                    timer.cancel();
                                    finish();
                                }else {
                                   AutoLogin();
                                }
                            }
                        break;
                    case 1:
                        int result = msg.arg1;
                        String sessionId = msg.obj.toString();
                        if(result ==1){
                            //页面跳转到MainActivity
                            Intent intent = new Intent(BootPageActivity.this,MainActivity.class);
                            startActivity(intent);
                            //将登录从堆栈清除
                            BootPageActivity.this.finish();
                        }
                        break;


                }
            }
        };

    }
//自动登陆
    private void AutoLogin(){

        final String username = preferences.getString("username","");
        final String password = preferences.getString("password","");

        new Thread(){
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                String result="";
                try{
                    //使用OKHttpClient连接网络
                    OkHttpClient client  = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .build();
                    Request request = new Request.Builder()
                            .url(CONSTANT.HOST+"/Login")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "获取的服务器数据： "+responseData);
                    //解析成功接收到的数据
                    if(response.isSuccessful()){
                        // 进行PULL解析
                        //1、生成解析器
                        XmlPullParser parser = Xml.newPullParser();
                        //2、引入要解析的流
                        //parser.setInput(response.body().byteStream(),"UTF-8");
                        parser.setInput(new StringReader(responseData));
                        //3、事件类型解析
                        int type = parser.getEventType();
                        while (type!= XmlPullParser.END_DOCUMENT){
                            switch (type){
                                case XmlPullParser.START_TAG:
                                    if("result".equals(parser.getName())){
                                        result = parser.nextText();
                                        Log.d(TAG, "解析出的服务器响应信息result="+result);
                                    }
                                    break;
                            }
                            type = parser.next();
                            Log.d(TAG, "run: "+type);
                        }
                        //读取sessionId
                        Headers headers = response.headers();
                        Log.e(TAG, "headers: "+headers );
                        List<String> cookies = headers.values("Set-Cookie");
                        Log.e(TAG, "Set-Cookie: "+cookies );
                        String session = cookies.get(0);
                        Log.e(TAG, "onResponse-size: " + session);
                        String sessionId = session.substring(0, session.indexOf(";"));
                        Log.e(TAG, "session is :" + sessionId);
                        //发送消息
                        msg.what = 1;
                        msg.arg1 = Integer.parseInt(result);
                        msg.obj = sessionId;
                    }else {
                        //网络状态异常
                        msg.what = 2;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    msg.what = 2;
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    msg.what = 2;
                }
                handler.sendMessage(msg);
            }

    }.start();


}

    private void initView() {
        ImmersionBar.with(this).init();
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
                    preferences = getSharedPreferences("userinfo",MODE_PRIVATE);
                    if(preferences.getString("password","").isEmpty()){
                        Intent intent = new Intent(BootPageActivity.this,LoginActivity.class);
                        Log.d(TAG, "onClick: "+"点击跳过");
                        startActivity(intent);
                        timer.cancel();
                        finish();
                    }else {
                        timer.cancel();
                     AutoLogin();
                    }

            }
    }
}
