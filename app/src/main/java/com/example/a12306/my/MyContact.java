package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.a12306.R;
import com.example.a12306.others.CONST;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


public class MyContact extends Activity {

    private ListView lvMyContact;
    private static final int REQUEST_CODE = 1001;
    private SimpleAdapter adapter;
    private static final String TAG = "MyContact";
    private int point;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        lvMyContact = findViewById(R.id.lv_mycontact);
        CONST.passenger_informationt = new ArrayList<>();
        //judge();
        initData();
        lvMyContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyContact.this, MyContactEdit.class);
                intent.putExtra("row", (Serializable) CONST.passenger_informationt.get(position));
                point = position;
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
    }

    //判断是否有数据存在本地
    private void judge() {

        sharedPreferences = getSharedPreferences("mycontact",MODE_PRIVATE);
        String name = sharedPreferences.getString("append","");
        if(name .isEmpty()){
            initData();
            Log.d(TAG, "judge1: "+ name);
        }else{

            /*datas.get(point).put("name",name);*/
            Log.d(TAG, "judge2: "+ name);
        }
    }


    //初始化数据
    private void initData() {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("idCard", "身份证:123");
            map1.put("name", "张三(成人)");
            map1.put("tel", "电话:1234");
            CONST.passenger_informationt.add(map1);
        Log.d(TAG, "initData1: "+CONST.passenger_informationt);

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", "李四(成人)");
            map2.put("idCard", "身份证:12345");
            map2.put("tel", "电话:123456");
            CONST.passenger_informationt.add(map2);
        Log.d(TAG, "initData2: "+CONST.passenger_informationt);

            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("name", "王二(学生)");
            map3.put("idCard", "学生证:1234567");
            map3.put("tel", "电话:12345678");
            CONST.passenger_informationt.add(map3);
        Log.d(TAG, "initData3: "+CONST.passenger_informationt);


        //新增联系人
       /* Intent intent1 = getIntent();
        String name = intent1.getStringExtra("name");
        Log.d(TAG, "name: "+name);
        String idCard = intent1.getStringExtra("idCard");
        Log.d(TAG, "id: "+idCard);
        String tel = intent1.getStringExtra("tel");
        Log.d(TAG, "tel: "+tel);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("idCard", idCard);
        map4.put("name", name);
        map4.put("tel", tel);
        CONST.passenger_informationt.add(map4);
        Log.d(TAG, "onActivityResult: "+CONST.passenger_informationt);*/

        adapter = new SimpleAdapter(this,
                CONST.passenger_informationt,
                R.layout.list_item_my_contact_list_layout,
                new String[]{"name", "idCard", "tel"},
                new int[]{R.id.tvContactName, R.id.tvContactIdCard, R.id.tvContactTel});

        lvMyContact.setAdapter(adapter);
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
                finish();
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


    //收到来自MyContactEdit传来的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String name = data.getStringExtra("name");
                    String type = data.getStringExtra("type");
                    String telephone = data.getStringExtra("telephone");
                    String append = name + "(" + type + ")";
                    CONST.passenger_informationt.get(point).put("name", append);
                    CONST.passenger_informationt.get(point).put("tel", "电话:" + telephone);
                    adapter.notifyDataSetChanged();
                }





        }
    }

}

