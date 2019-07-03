package com.example.a12306.ticket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a12306.R;

public class CityActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();

    }

    private void initView() {
        listView = (ListView)findViewById(R.id.lv_city);

    }
}
