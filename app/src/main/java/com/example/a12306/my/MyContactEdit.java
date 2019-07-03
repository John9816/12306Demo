package com.example.a12306.my;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.example.a12306.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyContactEdit extends AppCompatActivity {

    private ListView lvMyContactEdit;
    private List<Map<String,Object>> data;
    private SimpleAdapter adapter;
    private Button btn_contactsave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_edit);
        btn_contactsave = (Button)findViewById(R.id.btn_contactsave);//保存
        lvMyContactEdit = findViewById(R.id.lv_mycontactedit);
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        data = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        String name = (String) contact.get("name");

        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key2",name.split("\\(")[0]);
        map1.put("key3",R.drawable.forward_25);
        data.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        String idCard = (String) contact.get("idCard");
        map2.put("key1","证件类型");
        map2.put("key2",idCard.split("\\:")[0]);
        data.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("key1","证件号码");
        map3.put("key2",idCard.split("\\:")[1]);
        data.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key2",name.split("\\(")[1]);
        map4.put("key3",R.drawable.forward_25);
        data.add(map4);

        Map<String,Object> map5 = new HashMap<>();
        String tel = (String) contact.get("tel");
        map5.put("key1","电话");
        map5.put("key2",tel.split("\\:")[1]);
        map5.put("key3",R.drawable.forward_25);
        data.add(map5);

        adapter = new SimpleAdapter(this,
                data,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        lvMyContactEdit.setAdapter(adapter);


    }
}

