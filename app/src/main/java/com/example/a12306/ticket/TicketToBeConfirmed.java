package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketToBeConfirmed extends AppCompatActivity {
    private ArrayList<HashMap<String,Object>> data;
    private ListView lv_ticket_details;
    private TextView orderId;
    private Button pause_submit,confirm_submit;
    private static final String TAG = "TicketToBeConfirmed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_to_be_confirmed);
        lv_ticket_details = (ListView)findViewById(R.id.lv_ticket_details);
        pause_submit = (Button) findViewById(R.id.pause_submit);
        confirm_submit = (Button) findViewById(R.id.pause_submit);
        orderId = (Button)findViewById(R.id.pause_submit);
        getData();
    }

    //获取传递到的数据
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("ticket");
        /*TicketToBeConfirmedAdapter adapter = new TicketToBeConfirmedAdapter(this,AddPassenger.dispalyselected);
        lv_ticket_details.setAdapter(adapter);*/
        Log.d(TAG, "getData: "+data);

    }
}
