package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12306.MainActivity;
import com.example.a12306.R;
import com.example.a12306.order.AllPayFragment;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//订单确认，车票预定4/5
public class TicketToBeConfirmed extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_ticket_details;
    private TextView orderId;
    private Button pause_submit,confirm_submit;
    private Toolbar toolbar;
    private Intent intent;
    public static ArrayList<HashMap<String, Object>> unpayTicket = new ArrayList<>();
    public static ArrayList<HashMap<String, Object>> allPayTicket = new ArrayList<>();
    public static ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    public static TicketToBeConfirmed ticketToBeConfirmed;
    public static ArrayList<ArrayList<HashMap<String, Object>>> content  = new ArrayList<>();
    private static final String TAG = "TicketToBeConfirmed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_to_be_confirmed);
        toolbar = CONST.usrToolbar(R.id.payHead, "车票预定4/5", this, 0);
        lv_ticket_details = (ListView)findViewById(R.id.lv_ticket_details);
        pause_submit = (Button) findViewById(R.id.pause_submit);
        pause_submit.setOnClickListener(this);
        confirm_submit = (Button) findViewById(R.id.confirm_submit);
        confirm_submit.setOnClickListener(this);
        orderId = (TextView) findViewById(R.id.orderId);
        getData();
        ticketToBeConfirmed = this;
    }

    //获取传递到的数据
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("ticket");
        orderId.setText("订单提交成功，订单编号为：" + data.get(0).get("orderId").toString());
        Log.d(TAG, "getData: "+data.get(0).get("orderId").toString());
        TicketToBeConfirmedAdapter adapter = new TicketToBeConfirmedAdapter(this,AddPassenger.dispalyselected);
        lv_ticket_details.setAdapter(adapter);
        content.add(AddPassenger.dispalyselected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_submit:
                intent = new Intent(TicketToBeConfirmed.this, TicketReservationSuccess.class);
                intent.putExtra("code", 0);
                allPayTicket = data;
                allPayTicket.get(0).put("payState", "已支付");
                AllPayFragment.allPaidTicket.addAll(data);
                startActivity(intent);
                break;
            case R.id.pause_submit:
                intent = new Intent(TicketToBeConfirmed.this, MainActivity.class);
                intent.putExtra("order", 1);
                allPayTicket = data;
                allPayTicket.get(0).put("payState", "未支付");
                unpayTicket.addAll(data);
                AllPayFragment.allPaidTicket.addAll(data);
                startActivity(intent);
                TicketReservation.ticketReservation.finish();
                TicketReservationDetail.ticketReservationDetail.finish();
                AddPassenger.addPassenger.finish();
                break;
        }

    }

}
