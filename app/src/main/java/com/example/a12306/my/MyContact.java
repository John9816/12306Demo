package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;


public class MyContact extends Activity {

    private ListView lvMyContact;
    private List<Map<String, Object>> data;
    private SimpleAdapter adapter;
    private static final String TAG = "MyContact";
    private Map<String, Object> map1,map2,map3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);

        initData();
        lvMyContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyContact.this, MyContactEdit.class);
                intent.putExtra("row", (Serializable) data.get(position));
                Log.d(TAG, "onItemClick: "+(Serializable) data.get(position));
                startActivity(intent);
            }
        });
    }


    //初始化数据
    private void initData() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        lvMyContact = findViewById(R.id.lv_mycontact);
        data = new ArrayList<>();
        map1 = new HashMap<>();
        map1.put("idCard", "身份证:123");
        map1.put("name", "张三(成人)");
        map1.put("tel", "电话:1234");
        data.add(map1);

        map2 = new HashMap<>();
        map2.put("name", "李四(成人)");
        map2.put("idCard", "身份证:12345");
        map2.put("tel", "电话:123456");
        data.add(map2);

       map3 = new HashMap<>();
        map3.put("name", "王二(学生)");
        map3.put("idCard", "学生证:1234567");
        map3.put("tel", "电话:12345678");
        data.add(map3);

        adapter = new SimpleAdapter(this,
                data,
                R.layout.list_item_my_contact_list_layout,
                new String[]{"name", "idCard", "tel", "image"},
                new int[]{R.id.tvContactName, R.id.tvContactIdCard, R.id.tvContactTel});

        lvMyContact.setAdapter(adapter);
        Log.d(TAG, "initData: "+"initData");
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

    @Override
    protected void onStart() {

       // getData();
        super.onStart();
    }

    //从MyContactEdit收到数据
    public void getData(){
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String sex = bundle.getString("sex");
        String telephone = bundle.getString("telephone");
        map1.put("name",name);
        map1.put("name",name);
        Log.d(TAG, "getData: "+"getData");

    }

    //收到来自MyContactEdit传来的数据
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case RESULT_OK:
                Bundle bundle = getIntent().getExtras();
                String name = bundle.getString("name");
                Log.d(TAG, "name: "+name);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}

