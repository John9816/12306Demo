package com.example.a12306.my;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.a12306.utils.NetUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class MyContactEdit extends Activity {

    private ListView lvMyContactEdit;
    private SimpleAdapter adapter;
    private Button btn_contactsave;//保存按钮
    private AlertDialog alertDialog;//对话框
    private AlertDialog.Builder builder;
    private EditText modifynm;//修改后的名字
    private int index = 0;//记录被选中的单选项
    private String inputname,inputtel;
    private  Map<String,Object> map1,map2,map3,map4,map5;
    private static final String TAG = "MyContactEdit";
    private ProgressDialog pDialog;
    private String action = "";
    private OkHttpClient client = new OkHttpClient();
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_edit);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_contactsave = (Button)findViewById(R.id.btn_contactsave);//保存
        lvMyContactEdit = findViewById(R.id.lv_mycontactedit);
        initData();



        lvMyContactEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(MyContactEdit.this).inflate(R.layout.activity_password_dialog,null,false);
                        modifynm=view.findViewById(R.id.et_common);
                        builder.setTitle("请输入你的姓名");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder.setView(view);
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sp=getSharedPreferences("info",MODE_PRIVATE);
                                inputname = modifynm .getText().toString().trim();
                               // String matepasswor=sp.getString("password","");
                                CONST.passenger_info.get(position).put("key2",inputname);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();
                        break;
                    case 3:
                        //乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //默认选中第一个
                                index = which;
                                if (which == 0) {
                                    //如果选择0--成人
                                    Log.d(TAG, "position: "+position);
                                    Log.d(TAG, "which: "+which);
                                    CONST.passenger_info.get(position).put("key2",passenger[0]);
                                }
                                if (which == 1) {
                                    //如果选择1--学生
                                    Log.d(TAG, "position1: "+position);
                                    Log.d(TAG, "which1: "+which);
                                    CONST.passenger_info.get(position).put("key2",passenger[1]);
                                }

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("row", (Serializable) CONST.passenger_info.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });

                        builder.create().show();
                        break;
                    case 4:
                        builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(MyContactEdit.this).inflate(R.layout.activity_password_dialog,null,false);
                        modifynm=view.findViewById(R.id.et_common);
                        builder.setTitle("请输入你的电话");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder.setView(view);
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                inputtel = modifynm .getText().toString().trim();
                                // String matepasswor=sp.getString("password","");
                                CONST.passenger_info.get(position).put("key2",inputtel);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();
                        break;
                }
            }
        });

        //保存数据并且传递
        btn_contactsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //1、检查网络连接是否正常
                if (!NetUtils.check(MyContactEdit.this)) {
                    Toast.makeText(MyContactEdit.this, "网络异常，请检查！", Toast.LENGTH_LONG).show();
                    return;//后续代码不执行
                }
              pDialog = ProgressDialog.show(MyContactEdit.this,null,"正在加载中...",
                        false,true);
                action = "update";
        new Thread(){
            @Override
            public void run() {
                super.run();
                preferences = MyContactEdit.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                String value = preferences.getString("cookie", "");
                String id = CONST.passenger_info.get(2).get("key2").toString();
                String name = CONST.passenger_info.get(0).get("key2").toString();
                String idType = CONST.passenger_info.get(1).get("key2").toString();
                String tel =CONST.passenger_info.get(4).get("key2").toString();
                String type = CONST.passenger_info.get(3).get("key2").toString();
                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("姓名", name)
                            .add("证件类型", idType)
                            .add("证件号码",id)
                            .add("乘客类型",type)
                            .add("电话",tel)
                            .add("action",action)
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("cookie",value)
                            .url(CONSTANT.HOST + "/otn/Passenger")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "responseData: "+responseData);
                    if("\"1\"".equals(responseData)){
                        if(pDialog != null){
                            pDialog.dismiss();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
                MyContact.adapter.notifyDataSetChanged();
                finish();
            }
        });
    }


    //数据初始化
    private void initData() {
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        CONST.passenger_info = new ArrayList<>();
        map1 = new HashMap<>();
        String name = (String) contact.get("name");
        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key2",name.split("\\(")[0]);
        map1.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map1);

        map2 = new HashMap<>();
        String idCard = (String) contact.get("idCard");
        map2.put("key1","证件类型");
        map2.put("key2",idCard.split("\\:")[0]);
        CONST.passenger_info.add(map2);

        map3 = new HashMap<>();
        map3.put("key1","证件号码");
        map3.put("key2",idCard.split("\\:")[1]);
        CONST.passenger_info.add(map3);

        map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key2",name.split("\\(")[1].replace(")",""));
        map4.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map4);

        map5 = new HashMap<>();
        String tel = (String) contact.get("tel");
        map5.put("key1","电话");
        map5.put("key2",tel.split("\\:")[1]);
        map5.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map5);

        adapter = new SimpleAdapter(this,
                CONST.passenger_info,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        lvMyContactEdit.setAdapter(adapter);
    }

    //ActionBar重写的方法
    @Override
    public boolean onOptionsItemSelected(   MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}

