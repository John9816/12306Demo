package com.example.a12306.ticket;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306.MainActivity;
import com.example.a12306.R;
import com.example.a12306.order.AllPayFragment;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;
import com.example.a12306.utils.CONSTANT;
import com.gyf.immersionbar.ImmersionBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static com.example.a12306.others.CONST.trainData;
import static com.example.a12306.ticket.AddPassenger.list;


/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//订单确认，车票预定4/5
public class TicketToBeConfirmed extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_ticket_details;
    private TextView order;
    private Button pause_submit,confirm_submit;
    private Toolbar toolbar;
    private Intent intent;

    public static ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    public static TicketToBeConfirmed ticketToBeConfirmed;
    public static ArrayList<ArrayList<HashMap<String, Object>>> content  = new ArrayList<>();
    private static final String TAG = "TicketToBeConfirmed";
    private Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Log.d(TAG, "接收: "+msg.what);
        switch (msg.what){
            case 1:
                intent = new Intent(TicketToBeConfirmed.this, TicketReservationSuccess.class);
               // intent.putExtra("code", 0);
                intent.putExtra("orderId",trainData[0]);
                //allPayTicket = data;
                //allPayTicket.get(0).put("payState", "已支付");
              //  AllPayFragment.allPaidTicket.addAll(data);

                startActivity(intent);
                Toast.makeText(getApplicationContext(),"支付成功",Toast.LENGTH_SHORT).show();
                break;
            case 2:

                Toast.makeText(getApplicationContext(),"支付失败",Toast.LENGTH_SHORT).show();
                break;
        }

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_to_be_confirmed);
        ImmersionBar.with(this).init();
        toolbar = CONST.usrToolbar(R.id.payHead, "车票预定4/5", this, 0);
        lv_ticket_details = (ListView)findViewById(R.id.lv_ticket_details);
        pause_submit = (Button) findViewById(R.id.pause_submit);
        pause_submit.setOnClickListener(this);
        confirm_submit = (Button) findViewById(R.id.confirm_submit);
        confirm_submit.setOnClickListener(this);
        order = (TextView) findViewById(R.id.orderId);
        getData();
        ticketToBeConfirmed = this;
    }

    //获取传递到的数据
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        order.setText("订单提交成功，订单编号为："+trainData[0]);

        TicketToBeConfirmedAdapter adapter = new TicketToBeConfirmedAdapter(this,list);
       lv_ticket_details.setAdapter(adapter);
        Log.e(TAG, "getData: "+list.size());
        content.add(AddPassenger.dispalyselected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm_submit:
                new Thread(PayThread).start();
                break;
            case R.id.pause_submit:
                intent = new Intent(TicketToBeConfirmed.this, MainActivity.class);
                intent.putExtra("order", 1);
                TicketReservation.ticketReservation.finish();
                TicketReservationDetail.ticketReservationDetail.finish();
                AddPassenger.addPassenger.finish();
                startActivity(intent);
                break;
        }

    }


    //子线程请求网络
    Runnable PayThread = new Runnable() {
        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Message msg = handler.obtainMessage();
            try{
                RequestBody requestBody = new FormBody.Builder()
                        .add("orderId",trainData[0])
                        .build();
                Request request = new Request.Builder()
                        .addHeader("cookie",CONST.getCookie(TicketToBeConfirmed.this))
                        .url(CONSTANT.HOST + "/otn/Pay")
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String responseDate = response.body().string();
                Log.e(TAG, "PayThread: "+responseDate);
                if (responseDate.contains("1")){
                    msg.what = 1;
                    Log.d(TAG, "run: "+"判断1");
                }else{
                    msg.what = 2;
                    Log.d(TAG, "run: "+"判断2");
                }
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    };
}
