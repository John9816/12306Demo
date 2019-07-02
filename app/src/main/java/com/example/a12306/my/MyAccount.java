package com.example.a12306.my;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.a12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//我的账户
public class MyAccount extends AppCompatActivity {
    private List<Map<String,Object>> listdata;
    private SimpleAdapter adapter;
    private ListView listView;
    private String[] titles = {"用户名","姓名","证件类型","证件号码","乘客类型","电话"};
    private String[] datas = {"dong","冬不拉","身份证","11010119910511947X","成人","13812345678"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        listView = (ListView)findViewById(R.id.lv_myaccount);
        listdata = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            Map<String,Object> hashMap = new HashMap<>();
            hashMap.put("titles",titles[i]);
            hashMap.put("datas",datas[i]);
            listdata.add(hashMap);
        }
       adapter = new SimpleAdapter(this,listdata,R.layout.common_list_items,new String[]{"titles","datas"},
               new int[]{R.id.tv_common,R.id.tv_common_details});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 4:
                        //乘客类型
                        break;
                    case 5:
                        //电话
                        break;
                }
            }
        });
    }

}
