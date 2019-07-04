package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewContact extends Activity {
    private ListView addnewpassenger;
    private List<Map<String,Object>> data;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        addnewpassenger = findViewById(R.id.addnewpassenger);
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        data = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();

        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key3",R.drawable.forward_25);
        data.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("key1","证件类型");
        data.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("key1","证件号码");
        data.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key3",R.drawable.forward_25);
        data.add(map4);

        Map<String,Object> map5 = new HashMap<>();
        map5.put("key1","电话");
        map5.put("key3",R.drawable.forward_25);
        data.add(map5);

        adapter = new SimpleAdapter(this,
                data,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        addnewpassenger.setAdapter(adapter);


        addnewpassenger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:

                        break;
                    case 3:

                        break;
                    case 4:

                }
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
}
