package com.example.a12306.my;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.example.a12306.utils.CONSTANT;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.example.a12306.others.CONST.passenger;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//我的账户
public class MyAccount extends Activity {
    private HashMap<String,Object> map1,map2,map3,map4,map5,map6;
    private SimpleAdapter adapter;
    private ListView listView;
    private Map<String,Object> hashMap;
    private AlertDialog alertDialog;
    private int index = 0;//记录被选中的单选项
    private Button btn_save;
    private OkHttpClient client = new OkHttpClient();
    private static final String TAG = "MyAccount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
            getData();
        /* 显示App icon左侧的back键 */
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
            getData();



        //保存
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try{
                            SharedPreferences preferences = MyAccount.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                            String value = preferences.getString("cookie", "");
                            RequestBody requestBody = new FormBody.Builder()
                                    .add("乘客类型",CONST.personal_info.get(4).get("key2").toString())
                                    .add("电话",CONST.personal_info.get(5).get("key2").toString())
                                    .add("action","update")
                                    .build();
                            Request request = new Request.Builder()
                                    .addHeader("Cookie", value)
                                    .url(CONSTANT.HOST + "/otn/Account")
                                    .post(requestBody)
                                    .build();

                            Response response = client.newCall(request).execute();
                            String responseData = response.body().string();
                            Log.d(TAG, "onClick: "+responseData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();



                Toast.makeText(MyAccount.this,"保存成功",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getData() {
        //添加数据到Listview
        listView = (ListView)findViewById(R.id.lv_myaccount);
        adapter = new SimpleAdapter(MyAccount.this,CONST.personal_info,R.layout.list_items,new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_common,R.id.tv_common_details,R.id.iv_common});
        listView.setAdapter(adapter);
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 4:
                        //乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyAccount.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index = which;
                                if(index == 0){
                                    CONST.personal_info.get(4).put("key2" , passenger[0]);

                                }if(index == 1){
                                    CONST.personal_info.get(4).put("key2" , passenger[1]);
                                }
                            }
                        });

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.create().show();
                        break;
                    case 5:
                        //电话
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyAccount.this,R.style.AlertDialogCustom);
                        View view1= LayoutInflater.from(MyAccount.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifyphone = view1.findViewById(R.id.et_common);
                        builder1.setTitle("请输入你的手机号");
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder1.setView(view1);
                        builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phone = modifyphone.getText().toString().trim();
                                if(phone.equals("") || phone.equals("13812345678") || phone.length() != 11){
                                    Toast.makeText(MyAccount.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                                }else {

                                    CONST.personal_info.get(5).put("key2" , phone);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        alertDialog=builder1.create();
                        alertDialog.show();
                        break;
                }

            }

        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Thread(){
            @Override
            public void run() {
                super.run();
                //读取已经存好的sessionId
                SharedPreferences preferences = MyAccount.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                String value = preferences.getString("cookie", "");
                try {

                    Request request = new Request.Builder()
                            .addHeader("Cookie", value)
                            .url(CONSTANT.HOST + "/otn/Account")
                            .build();
                    Log.d(TAG, "request: "+request);
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "response: "+response);
                    String responseData = response.body().string();
                    Log.d(TAG, "获取的服务器数据： " + responseData);
                    //解析
                    JSONObject jsonObject = new JSONObject(responseData);


                    map1 = new HashMap<>();
                    map1.put("key1","用户名");
                    map1.put("key2",jsonObject.getString("username"));
                    CONST.personal_info.add(map1);

                    map2 = new HashMap<>();
                    map2.put("key1","姓名");
                    map2.put("key2",jsonObject.getString("name"));
                    CONST.personal_info.add(map2);

                    map3 = new HashMap<>();
                    map3.put("key1","证件类型");
                    map3.put("key2",jsonObject.getString("idType"));
                    CONST.personal_info.add(map3);

                    map4  = new HashMap<>();
                    map4.put("key1","证件号码");
                    map4.put("key2",jsonObject.getString("id"));
                    CONST.personal_info.add(map4);

                    map5 = new HashMap<>();
                    map5.put("key1","乘客类型");
                    map5.put("key2",jsonObject.getString("type"));
                    map5.put("key3",R.drawable.forward_25);
                    CONST.personal_info.add(map5);

                    map6 = new HashMap<>();
                    map6.put("key1","电话");
                    map6.put("key2",jsonObject.getString("tel"));
                    map6.put("key3",R.drawable.forward_25);
                    CONST.personal_info.add(map6);
                    Log.d(TAG, "run: "+CONST.personal_info);


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
                CONST.personal_info = new ArrayList<>();
                finish();
                break;
            default:
        }
        return true;
    }

}
