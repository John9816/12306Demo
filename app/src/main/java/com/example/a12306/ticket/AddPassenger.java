package com.example.a12306.ticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.my.MyContact;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.AddPassengerAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class AddPassenger extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private Toolbar toolbar;
    private String year,month,day,startPlace,stopPlace,trainNumber,startTime,stopTime,setType,setPrice;
    private TextView tv_TicketPlaceFrom,tv_TicketTimeFrom,tv_TrainNumber,tv_Date,
            tv_TicketPlaceTo,tv_TicketTimeTo,tv_SeatName,tv_SeatPrice,tv_PassengerList,tv_OrderSumPrice,tv_Submit;
    private ListView lv_TicketPassengerList;
    public static ArrayList<HashMap<String, Object>> dispalyselected,allselectedpay,intentdata;
    private AddPassengerAdapter adapter;
    public static AddPassenger addPassenger;
    private static final String TAG = "AddPassenger";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_passenger);
        ImmersionBar.with(this).init();
        toolbar = CONST.usrToolbar(R.id.reservation1Head, "车票预定3/5", this, 0);
        getData();
        init();
        addPassenger = this;
    }

    //绑定控件，初始化
    private void init() {
        tv_TicketPlaceFrom = findViewById(R.id.tv_TicketPlaceFrom);
        tv_TicketPlaceFrom.setText(startPlace);
        tv_TicketTimeFrom = findViewById(R.id.tv_TicketTimeFrom);
        tv_TicketTimeFrom.setText(startTime);
        tv_TrainNumber = findViewById(R.id.tv_TrainNumber);
        tv_TrainNumber.setText(trainNumber);
        tv_Date = findViewById(R.id.tv_Date);
        tv_Date.setText(year+"-"+month+"-"+day);
        tv_TicketPlaceTo = findViewById(R.id.tv_TicketPlaceTo);
        tv_TicketPlaceTo.setText(stopPlace);
        tv_TicketTimeTo = findViewById(R.id.tv_TicketTimeTo);
        tv_TicketTimeTo.setText(stopTime);
        tv_SeatName = findViewById(R.id.tv_SeatName);
        tv_SeatName.setText(setType);
        tv_SeatPrice = findViewById(R.id.tv_SeatPrice);
        tv_SeatPrice.setText(setPrice);
        tv_PassengerList = findViewById(R.id.tv_PassengerList);
        tv_PassengerList.setOnClickListener(this);
        lv_TicketPassengerList = findViewById(R.id.lv_TicketPassengerList);
        tv_OrderSumPrice = findViewById(R.id.tv_OrderSumPrice);
        tv_Submit = findViewById(R.id.tv_Submit);
        tv_Submit.setOnClickListener(this);
        dispalyselected = new ArrayList<>();
        allselectedpay = new ArrayList<>();

        lv_TicketPassengerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder aler = new AlertDialog.Builder(AddPassenger.this);
                aler.setMessage("是否删除该乘客");
                aler.setCancelable(false);
                aler.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dispalyselected.remove(position);
                        if (dispalyselected.size() == 0){
                            dispalyselected = new ArrayList<>();
                        }
                       adapter.notifyDataSetChanged();
                        tv_OrderSumPrice.setText("订单总额：￥"+ (float) adapter.getCount() * Float.parseFloat(setPrice) + "元");
                    }
                });
                aler.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                aler.create().show();
                return false;
            }
        });
    }

    //获取2/5传来的数据
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getString("year");
        month = bundle.getString("month");
        day = bundle.getString("day");
        startPlace = bundle.getString("startPlace");
        stopPlace = bundle.getString("stopPlace");
        trainNumber= bundle.getString("trainNumber");
        startTime= bundle.getString("startTime");
        stopTime= bundle.getString("stopTime");
        setType= bundle.getString("setType");
        setPrice= bundle.getString("setPrice");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_PassengerList:
                startActivityForResult(new Intent(AddPassenger.this, SelectedPassenger.class),0);
                break;
            case R.id.tv_Submit:
                Intent intent = new Intent(AddPassenger.this,TicketToBeConfirmed.class);
                Bundle bundle = new Bundle();
                Calendar calendar = Calendar.getInstance();
                //小时
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                //分钟
                int minute = calendar.get(Calendar.MINUTE);
                //秒
                int second = calendar.get(Calendar.SECOND);

                intentdata = new ArrayList<>();
                HashMap<String,Object> map = new HashMap<>();
                map.put("orderId", year + month + day + hour + minute + second +
                        (String.valueOf(new Random().nextInt(900000) + 100000)) +
                        ((char)(int)(Math.random() * 26 + 65)) + ((char)(int)(Math.random() * 26 + 97)));
                map.put("date",tv_Date.getText().toString());
                map.put("trainNumber",tv_TrainNumber.getText().toString());
                map.put("setNumber","1车1座");
                map.put("sumPeople",adapter.getCount());
                map.put("sumPrice",(float) adapter.getCount() * Float.parseFloat(setPrice));
                map.put("place",startPlace+"-"+stopPlace);
                intentdata.add(map);
                bundle.putSerializable("ticket", intentdata);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 0) {
            Bundle bundle = data.getExtras();
            dispalyselected = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("passenger");
            for (int i = 0; i < dispalyselected.size(); i++) {
                dispalyselected.get(i).put("trainNumber", tv_TrainNumber.getText().toString());
                dispalyselected.get(i).put("date", tv_Date.getText().toString());
                dispalyselected.get(i).put("setNumber", "1车1座");
            }
            Log.d(TAG, "onActivityResult: "+dispalyselected);
            allselectedpay.addAll(dispalyselected);
            adapter = new AddPassengerAdapter(this,dispalyselected);
            lv_TicketPassengerList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            tv_OrderSumPrice.setText("订单总额：￥"+ (float) adapter.getCount() * Float.parseFloat(setPrice) + "元");
        }
    }


    //提交订单限制
    private void subLimit() {
        if (dispalyselected.size() == 0) {
            tv_Submit.setEnabled(false);
            Toast.makeText(AddPassenger.this,"当前乘车人不能为空",Toast.LENGTH_SHORT).show();
        } else {
            tv_Submit.setEnabled(true);
            tv_Submit.setOnClickListener(this);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(AddPassenger.this);
        alert.setMessage("是否删除该乘客");
        alert.setCancelable(false);
        alert.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dispalyselected.remove(position);
                if (dispalyselected.size() == 0){
                    dispalyselected = new ArrayList<>();
                }
              subLimit();
            }
        });
        alert.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.create().show();
        return false;
    }
}
