package com.example.a12306.order;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.solver.LinearSystem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.order.adapter.AllPayAdapter;
import com.example.a12306.order.adapter.TobePayAdapter;
import com.example.a12306.order.adapter.UnPayAdapter;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.AddPassenger;
import com.example.a12306.ticket.TicketReservationSuccess;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;
import com.example.a12306.utils.QRCodeUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//订单状态
public class ToBePay extends AppCompatActivity implements View.OnClickListener {
    private Button btn_cancel,btn_confirm;
    private ListView listView;
    private TextView textView;
    private Toolbar toolbar;
    private Intent intent;
    private  String orderId;


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
        orderId = intent.getStringExtra("orderId");
        String trainNo = intent.getStringExtra("trainNO");
        String trainDate  = intent.getStringExtra("trainDate");
        textView.setText("订单提交成功，您的编号为:"+orderId);
        TobePayAdapter adapter = new TobePayAdapter(this, CONST.passengerListBeanList,trainNo,trainDate);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
              CONST.OrderPay(this,orderId);
                    Toast.makeText(getApplicationContext(),"支付成功",Toast.LENGTH_SHORT).show();
                    finish();
                    AllPayFragment.allPayAdapter.notifyDataSetChanged();
                    UnPayFragment.unPayAdapter.notifyDataSetChanged();



                break;
            case R.id.btn_cancel:
             CONST.CancelThread(this,orderId);
                finish();
                UnPayFragment.unPayAdapter.notifyDataSetChanged();
                break;
        }

    }
}
