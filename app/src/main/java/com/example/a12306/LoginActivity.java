package com.example.a12306;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.a12306.utils.CONSTANT;
import com.example.a12306.utils.Md5Utils;
import com.example.a12306.utils.NetUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

//登陆界面
public class LoginActivity extends AppCompatActivity{
    private EditText edtUsername = null;
    private EditText edtPassword = null;
    private Button btnLogin;
    private CheckBox ckLogin = null;
    private TextView tvLostPassword;
    private static final String TAG = "LoginActivity";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    int result = msg.arg1;
                    String sessionId = msg.obj.toString();
                    if(result == 0){
                        //用户名或者密码错误，给与提示
                        edtUsername.setError("用户名或者密码错误");
                        edtUsername.requestFocus();
                    }else if(result ==1){
                        //使用SPS保存
                        SharedPreferences preferences = getSharedPreferences("userinfo",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        //若勾选了“自动登录”，则保存正确的用户名和密码
                        if (ckLogin.isChecked()){
                            //记录用户名和加密后的密码
                            editor.putString("username",edtUsername.getText().toString());
                            editor.putString("password",Md5Utils.MD5(edtPassword.getText().toString()));
                        }

                        //保存sessionId
                        editor.putString("cookie",sessionId);
                        //执行修改
                        editor.commit();
                        //页面跳转到MainActivity
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        //将登录从堆栈清除
                        LoginActivity.this.finish();
                    }
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this,
                            "服务器错误，请重试！",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    private void initView() {

        edtUsername = (EditText)findViewById(R.id.edtUsername);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        tvLostPassword = (TextView)findViewById(R.id.tvLostPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        ckLogin = (CheckBox)findViewById(R.id.ckLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edtUsername.getText().toString().trim())){
                    edtUsername.requestFocus();
                    edtUsername.setError("请输入用户名");
                }else if(TextUtils.isEmpty(edtPassword.getText().toString().trim())){
                    edtPassword.requestFocus();
                    edtPassword.setError("请输入密码");
                }else {
                    //1、检查网络状态
                    if(!NetUtils.check(LoginActivity.this)){
                        Toast.makeText(LoginActivity.this,"网络异常，请检查！",Toast.LENGTH_LONG).show();
                        return;//后续代码不执行
                    }
                    //2、网络正常时发起网络连接，传递用户名和密码
                    //多线程
                    new Thread(){
                        @Override
                        public void run() {
                            Message msg = handler.obtainMessage();
                            String result="";
                            try{
                                //使用OKHttpClient连接网络
                                OkHttpClient client  = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("username",edtUsername.getText().toString())
                                        .add("password",Md5Utils.MD5(edtPassword.getText().toString()))
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
            }
        });
        String url = "<a href='http://www.12306.cn'>忘记密码？</a>";
        CharSequence charSequence = Html.fromHtml(url);
        tvLostPassword.setText(charSequence);
        tvLostPassword.setMovementMethod(LinkMovementMethod.getInstance());

    }



}
