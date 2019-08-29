package com.example.a12306.order;

import android.content.Intent;
import android.support.constraint.solver.LinearSystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.order.adapter.AllPayAdapter;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.AddPassenger;
import com.example.a12306.ticket.TicketReservationSuccess;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;
import com.gyf.immersionbar.ImmersionBar;

//订单状态
public class ToBePay extends AppCompatActivity implements View.OnClickListener {
    private Button btn_cancel,btn_confirm;
    private ListView listView;
    private TextView textView;
    public static AllPayAdapter adapter;
    private Toolbar toolbar;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_be_pay);
        ImmersionBar.with(this).init();
        toolbar = CONST.usrToolbar(R.id.tobepay, "订单管理", this, 0);
        textView = (TextView) findViewById(R.id.tv_orderId);
        listView = (ListView)findViewById(R.id.lv_waitPay);
        btn_confirm = (Button)findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        intent = getIntent();
        String orderId = intent.getStringExtra("orderId");
        textView.setText("订单提交成功，您的编号为:"+orderId);
        TicketToBeConfirmedAdapter adapter = new TicketToBeConfirmedAdapter(this, AddPassenger.dispalyselected);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                Intent intent1 = new Intent(ToBePay.this, TicketReservationSuccess.class);
                TicketToBeConfirmed.unpayTicket.get(intent.getIntExtra("position",0)).put("payState","已支付");
                TicketToBeConfirmed.unpayTicket.remove(0);
                UnPayFragment.unPayAdapter.notifyDataSetChanged();
                AllPayFragment.allPayAdapter.notifyDataSetChanged();
                finish();
                startActivity(intent1);
                break;
            case R.id.btn_cancel:
                TicketToBeConfirmed.unpayTicket.get(intent.getIntExtra("position",0)).put("payState", "已取消");
                TicketToBeConfirmed.unpayTicket.remove(0);
                UnPayFragment.unPayAdapter.notifyDataSetChanged();
                AllPayFragment.allPayAdapter.notifyDataSetChanged();
                finish();
                break;
        }

    }
}
