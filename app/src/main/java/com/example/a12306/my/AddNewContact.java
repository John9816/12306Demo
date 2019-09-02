package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.example.a12306.utils.CONSTANT;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
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
import static com.example.a12306.ticket.TicketToBeConfirmed.data;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//添加新的联系人
public class AddNewContact extends Activity {
    private ListView addnewpassenger;
    private SimpleAdapter adapter;
    private Button add;
    private ProgressDialog progressDialog = null;
    private AlertDialog alertDialog;
    private int index = 0;//记录被选中的单选项
    private static final String TAG = "AddNewContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        add = (Button)findViewById(R.id.add);
        addnewpassenger = findViewById(R.id.addnewpassenger);
        //Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        CONST.passenger_info = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();

        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key1","证件类型");
        map2.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("key1","证件号码");
        map3.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map3);

        final HashMap<String, Object> map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map4);

        final HashMap<String, Object> map5 = new HashMap<>();
        map5.put("key1","电话");
        map5.put("key3",R.drawable.forward_25);
        CONST.passenger_info.add(map5);

        adapter = new SimpleAdapter(this,
                CONST.passenger_info,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        addnewpassenger.setAdapter(adapter);


        addnewpassenger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        View view1= LayoutInflater.from(AddNewContact.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifyphone = view1.findViewById(R.id.et_common);
                        builder1.setTitle("请输入姓名");
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
                                String name = modifyphone.getText().toString().trim();
                                CONST.passenger_info.get(position).put("key2",name);
                                Log.d(TAG, "onClick: "+CONST.passenger_info.get(position).toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        alertDialog=builder1.create();
                        alertDialog.show();
                        break;

                    case 1:
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        builder2.setTitle("证件类型");
                        builder2.setSingleChoiceItems(CONST.idType, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //默认选中第一个
                                index = which;
                                if (which == 0) {
                                    //如果选择0--成人
                                    CONST.passenger_info.get(position).put("key2",CONST.idType[0]);
                                    Log.d(TAG, "成人: "+CONST.passenger_info.get(position).toString());
                                }
                                if (which == 1) {
                                    //如果选择1--学生
                                    CONST.passenger_info.get(position).put("key2",CONST.idType[1]);
                                    Log.d(TAG, "学生: "+CONST.passenger_info.get(position).toString());
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
                        builder2.create().show();
                        break;


                    case 2:
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        View view3= LayoutInflater.from(AddNewContact.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText et_id = view3.findViewById(R.id.et_common);
                        builder3.setTitle("请输入证件号码");
                        builder3.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder3.setView(view3);
                        builder3.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = et_id.getText().toString().trim();
                                CONST.passenger_info.get(position).put("key2",id);
                                Log.d(TAG, "onClick: "+CONST.passenger_info.get(position).toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        alertDialog=builder3.create();
                        alertDialog.show();
                        break;


                    case 3://乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //默认选中第一个
                                index = which;
                                if (which == 0) {
                                    //如果选择0--成人
                                    CONST.passenger_info.get(position).put("key2",passenger[0]);
                                    Log.d(TAG, "成人: "+CONST.passenger_info.get(position).toString());
                                }
                                if (which == 1) {
                                    //如果选择1--学生
                                    CONST.passenger_info.get(position).put("key2",passenger[1]);
                                    Log.d(TAG, "学生: "+CONST.passenger_info.get(position).toString());
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
                        builder = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(AddNewContact.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifynm=view.findViewById(R.id.et_common);
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
                                String inputtel = modifynm .getText().toString().trim();
                                CONST.passenger_info.get(position).put("key2",inputtel);
                                Log.d(TAG, "电话: "+CONST.passenger_info.get(position).toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();

                }
            }
        });


        //保存数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTask().execute();


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
                break;
        }
        return true;
    }

    private class AddTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AddNewContact.this,null,"正在加载中......",false,true);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

                    String result="";
                    //获取姓名
                    final String name =  CONST.passenger_info.get(0).get("key2").toString();
                    Log.d(TAG, "name: "+name);
                    //获取证件类型
                    final String idType = CONST.passenger_info.get(1).get("key2").toString();
                    //获取证件号码
                    final String id = CONST.passenger_info.get(2).get("key2").toString();
                    //获取乘客类型
                    final String type = CONST.passenger_info.get(3).get("key2").toString();
                    //获取电话号码
                    final String tel = CONST.passenger_info.get(4).get("key2").toString();

                    try {
                        //读取已经存好的sessionId
                        SharedPreferences preferences = AddNewContact.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                        String value = preferences.getString("cookie", "");
                        OkHttpClient client = new OkHttpClient();
                        RequestBody requestBody = new FormBody.Builder()
                                .add("姓名",name)
                                .add("证件类型",idType)
                                .add("证件号码",id)
                                .add("乘客类型",type)
                                .add("电话",tel)
                                .add("action","new")
                                .build();

                        Request request = new Request.Builder()
                                .addHeader("Cookie",value)
                                .url(CONSTANT.HOST + "/otn/Passenger")
                                .post(requestBody)
                                .build();
                        Log.d(TAG, "request: "+request);
                        Log.d(TAG, "doInBackground: "+name);
                        Response response = client.newCall(request).execute();
                        Log.d(TAG, "response: "+response);
                        result = response.body().string();
                        Log.d(TAG, "result: "+result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog != null){
                progressDialog.dismiss();
            }
            if(s.equals("\"1\"")){
                Toast.makeText(AddNewContact.this, "添加成功！", Toast.LENGTH_LONG).show();
                MyContact.myContact.finish();
                Intent intent = new Intent(AddNewContact.this,MyContact.class);
                startActivity(intent);
               finish();

            }if(s.equals("\"0\"")){
                Log.d(TAG, "responseData: "+"0");
                Toast.makeText(AddNewContact.this, "联系人已存在！", Toast.LENGTH_LONG).show();
            }if(s.equals("\"-1\"")){
                Log.d(TAG, "responseData: "+"-1");
                Toast.makeText(AddNewContact.this, "添加失败！", Toast.LENGTH_LONG).show();
            }

        }
    }
}
