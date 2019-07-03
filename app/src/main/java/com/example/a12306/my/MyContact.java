package com.example.a12306.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class MyContact extends AppCompatActivity {

    private ListView lvMyContact;
    private List<Map<String,Object>> data;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);

        lvMyContact = findViewById(R.id.lv_mycontact);
        data = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name","张三(成人)");
        map1.put("idCard","身份证:123");
        map1.put("tel","电话:1234");
        data.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("name","李四(成人)");
        map2.put("idCard","身份证:12345");
        map2.put("tel","电话:123456");
        data.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("name","王二(学生)");
        map3.put("idCard","学生证:1234567");
        map3.put("tel","电话:12345678");
        data.add(map3);

        adapter = new SimpleAdapter(this,
                data,
                R.layout.list_item_my_contact_list_layout,
                new String[]{"name","idCard","tel","image"},
                new int[]{R.id.tvContactName,R.id.tvContactIdCard,R.id.tvContactTel});

        lvMyContact.setAdapter(adapter);

        lvMyContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MyContact.this, MyContactEdit.class);
                intent.putExtra("row", (Serializable) data.get(position));
                startActivity(intent);
            }
        });
    }
}
