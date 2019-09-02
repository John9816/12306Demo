package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.example.a12306.others.Contact;
import com.example.a12306.ticket.AddPassenger;
import com.example.a12306.utils.CONSTANT;
import com.example.a12306.utils.NetUtils;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.a12306.ticket.AddPassenger.dispalyselected;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class MyContact extends Activity {

    private ListView lvMyContact;
    private static final int REQUEST_CODE = 1001;
    public static SimpleAdapter adapter;
    private static final String TAG = "MyContact";
    private int point;
    private ProgressDialog pDialog;
    private OkHttpClient client = new OkHttpClient();
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(pDialog != null){
                pDialog.dismiss();
            }

            switch (msg.what){
                case 1:
                    CONST.passenger_info.clear();
                    ArrayList<Contact> contactArrayList = (ArrayList<Contact>) msg.obj;
                    for (int i = 0; i <contactArrayList.size(); i++) {
                        Map<String,Object> map = new HashMap<>();
                        Contact contact = contactArrayList.get(i);
                        map.put("name",contact.getName()+"("+contact.getType()+")");
                        map.put("idType",contact.getIdType());
                        map.put("id",contact.getIdType()+":"+contact.getId());
                        map.put("type",contact.getType());
                        map.put("tel","电话:"+contact.getTel());
                        map.put("idCard",contact.getId());
                        map.put("phone",contact.getTel());
                        CONST.passenger_info.add(map);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 2:
                    Toast.makeText(MyContact.this, "网络异常，请检查！", Toast.LENGTH_LONG).show();
                    break;
                case 3:
                    Toast.makeText(MyContact.this, "删除成功！", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "handleMessage: "+CONST.passenger_info);
                    adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getData();
        lvMyContact = findViewById(R.id.lv_mycontact);
        adapter = new SimpleAdapter(this,
                CONST.passenger_info,
                R.layout.list_item_my_contact_list_layout,
                new String[]{"name", "id", "tel"},
                new int[]{R.id.tvContactName, R.id.tvContactIdCard, R.id.tvContactTel});
        lvMyContact.setAdapter(adapter);
        lvMyContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyContact.this, MyContactEdit.class);
                intent.putExtra("row", (Serializable) CONST.passenger_info.get(position));
                point = position;
                startActivity(intent);

            }
        });

        lvMyContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long op) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MyContact.this,R.style.Theme_AppCompat_Light_Dialog_Alert);
                alert.setMessage("是否删除乘客");
                alert.setCancelable(true);

                alert.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name= CONST.passenger_info.get(position).get("name").toString();
                        Log.d(TAG, "onClick: "+name);
                        String idType = CONST.passenger_info.get(position).get("idType").toString();
                        Log.d(TAG, "onClick: "+idType);
                        String id = CONST.passenger_info.get(position).get("idCard").toString();
                        Log.d(TAG, "onClick: "+id);
                        String type = CONST.passenger_info.get(position).get("type").toString();
                        Log.d(TAG, "onClick: "+type);
                        String tel = CONST.passenger_info.get(position).get("phone").toString();
                        Log.d(TAG, "onClick: "+tel);

                        remove(name,idType,id,type,tel);


                    }
                });
                alert.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.create().show();
                return true;


            }
        });

    }

    private void getData() {
        //1、检查网络连接是否正常
        if (!NetUtils.check(MyContact.this)) {
            Toast.makeText(MyContact.this, "网络异常，请检查！", Toast.LENGTH_LONG).show();
            return;//后续代码不执行
        }
        pDialog = ProgressDialog.show(MyContact.this,null,
                "正在加载中......",false,true);
        new Thread(){
            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                SharedPreferences preferences = MyContact.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                String value = preferences.getString("cookie","");
                try {

                    Request request = new Request.Builder()
                            .addHeader("Cookie",value)
                            .url(CONSTANT.HOST + "/otn/PassengerList")
                            .build();
                    Log.d(TAG, "request: "+request);
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "response: "+response);
                    String responseData = response.body().string();
                    Log.d(TAG, "responseData: "+responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    JSONObject jsonObject;
                    ArrayList<Contact> contactArrayList = new ArrayList<Contact>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Contact contact = new Contact();
                        contact.setName(jsonObject.getString("name"));
                        contact.setId(jsonObject.getString("id"));
                        contact.setIdType(jsonObject.getString("idType"));
                        contact.setType(jsonObject.getString("type"));
                        contact.setTel(jsonObject.getString("tel"));
                        contactArrayList.add(contact);
                    }
                    msg.obj = contactArrayList;
                    msg.what = 1;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    //删除乘客
    private void remove(final String name, final String idType, final String id, final String type, final String tel) {
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    Message msg = handler.obtainMessage();
                    SharedPreferences preferences = MyContact.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                    String value = preferences.getString("cookie","");
                    try{
                        RequestBody requestBody = new FormBody.Builder()
                                .add("姓名",name)
                                .add("证件类型",idType)
                                .add("证件号码",id)
                                .add("乘客类型",type)
                                .add("电话",tel)
                                .add("action","remove")
                                .build();

                        Request request = new Request.Builder()
                                .addHeader("Cookie",value)
                                .url(CONSTANT.HOST + "/otn/Passenger")
                                .post(requestBody)
                                .build();

                        Log.d(TAG, "request: "+request);
                        Response response = client.newCall(request).execute();
                        Log.d(TAG, "response: "+response);
                        String responseData = response.body().string();
                        Log.d(TAG, "responseData: "+responseData);

                        msg.what = 3;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(msg);
                }
            }.start();
    }



    //ActionBar重写的方法
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_bar_add:
                Intent intent = new Intent(MyContact.this,AddNewContact.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //解析ActionBar布局
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }


}

