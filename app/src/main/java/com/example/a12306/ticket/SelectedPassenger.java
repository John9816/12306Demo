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
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class SelectedPassenger extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv_passengerlist;
    private Button btn_addPassenger;
    private HashMap<String, Object> itemContent, hashMap;
    private ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
    private SelectedPassengerAdapter adapter;
    private static final String TAG = "SelectedPassenger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_passenger);
        ImmersionBar.with(this).init();
        toolbar = CONST.usrToolbar(R.id.selectedhead, "", this, 0);
        lv_passengerlist = findViewById(R.id.lv_passengerlist);
        btn_addPassenger = findViewById(R.id.btn_addPassenger);


       adapter = new SelectedPassengerAdapter(this,new ArrayList<Map<String, Object>>());
        lv_passengerlist.setAdapter(adapter);
        CONST.AddPassengerThread(SelectedPassenger.this,adapter);
        btn_addPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedPassenger.this, AddPassenger.class);
                Bundle bundle = new Bundle();
                for (int i = 0; i <lv_passengerlist.getAdapter().getCount(); i++) {
                    hashMap = new HashMap<>();
                    itemContent = (HashMap<String, Object>) lv_passengerlist.getAdapter().getItem(i);
                    CheckBox checkBox = (CheckBox) itemContent.get("choose");
                    if (checkBox.isChecked()) {
                        hashMap.put("name", itemContent.get("name").toString());
                        Log.d(TAG, "onClick: "+itemContent.get("name").toString());
                        hashMap.put("id", itemContent.get("id").toString());
                        hashMap.put("idType",itemContent.get("idType").toString());
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
