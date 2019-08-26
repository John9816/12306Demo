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
import com.example.a12306.others.CONST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//添加新的联系人
public class AddNewContact extends Activity {
    private ListView addnewpassenger;
    private SimpleAdapter adapter;
    private Button add;

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
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        CONST.passenger_informationt = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();

        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key1","证件类型");
        CONST.passenger_informationt.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("key1","证件号码");
        CONST.passenger_informationt.add(map3);

        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map4);

        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("key1","电话");
        map5.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map5);

        adapter = new SimpleAdapter(this,
                CONST.passenger_informationt,
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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
