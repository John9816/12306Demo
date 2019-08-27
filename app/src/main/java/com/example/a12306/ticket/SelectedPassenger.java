package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.SelectedPassengerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectedPassenger extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_passengerlist;
    private Button btn_addPassenger;
    private HashMap<String, Object> itemContent, hashMap;
    private ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
    private static final String TAG = "SelectedPassenger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_passenger);
        toolbar = CONST.usrToolbar(R.id.selectedhead, "", this, 0);
        lv_passengerlist = findViewById(R.id.lv_passengerlist);
        btn_addPassenger = findViewById(R.id.btn_addPassenger);
        if (CONST.passenger_informationt.size()<=0) {
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("idCard", "身份证:123");
            map1.put("name", "张三(成人)");
            map1.put("tel", "电话:1234");
            CONST.passenger_informationt.add(map1);

            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("name", "李四(成人)");
            map2.put("idCard", "身份证:12345");
            map2.put("tel", "电话:123456");
            CONST.passenger_informationt.add(map2);

            Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("name", "王二(学生)");
            map3.put("idCard", "学生证:1234567");
            map3.put("tel", "电话:12345678");
            CONST.passenger_informationt.add(map3);
        }
        SelectedPassengerAdapter adapter = new SelectedPassengerAdapter(this, CONST.passenger_informationt);
        lv_passengerlist.setAdapter(adapter);
        btn_addPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedPassenger.this, AddPassenger.class);
                Bundle bundle = new Bundle();
                for (int i = 0; i < lv_passengerlist.getAdapter().getCount(); i++) {
                    hashMap = new HashMap<>();
                    itemContent = (HashMap<String, Object>) lv_passengerlist.getAdapter().getItem(i);
                    CheckBox checkBox = (CheckBox) itemContent.get("choose");
                    if (checkBox.isChecked()) {
                        hashMap.put("name", itemContent.get("name").toString());
                        Log.d(TAG, "onClick: "+itemContent.get("name").toString());
                        hashMap.put("idCard", itemContent.get("idCard").toString());
                        hashMap.put("tel", itemContent.get("tel").toString());
                        arrayList.add(hashMap);
                    }
                }
                bundle.putSerializable("passenger", arrayList);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();

            }
        });


    }
}
