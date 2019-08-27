package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.example.a12306.others.QueryTestData;
import com.example.a12306.others.TimeCorrection;
import com.example.a12306.ticket.adapter.TicketReservationDetailAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//车票预定详细信息
public class TicketReservationDetail extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Calendar calendar;
    private TimeCorrection timeCorrection;
    private Button last_day,next_day;
    private TextView tv_query_date,tv_place_interval,tv_train_number,tv_time_content;
    private ListView listView;
    private int year,month,day;
    private String startPlace,stopPlace,traniNumber,startTime,stopTime;
    private String[] ticketinformations;
    private int indexNumber;
    public static TicketReservationDetail ticketReservationDetail;
    private ArrayList<HashMap<String, Object>> test_data, ticket_info;
    private static final String TAG = "TicketReservationDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation_detail);
        toolbar = CONST.usrToolbar(R.id.reservationhead2, "车票预定2/5", this, 0);
        init();
        ticketReservationDetail = this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.last_day:
                timeCorrection.lastDay();

                break;
            case R.id.next_day:
                timeCorrection.nextDay();
                year = timeCorrection.getYear();
                day = timeCorrection.getDay();
                month = timeCorrection.getMonth();
                break;
        }
    }

    //控件初始化
    private void init() {
        calendar = Calendar.getInstance();
        getData();
        last_day = (Button)findViewById(R.id.last_day);
        last_day.setOnClickListener(this);
        next_day = (Button)findViewById(R.id.next_day);
        next_day.setOnClickListener(this);
        tv_query_date = (TextView)findViewById(R.id.query_date);
        tv_place_interval = findViewById(R.id.place_interval);
        tv_train_number = findViewById(R.id.TrainNumber);
        tv_time_content = findViewById(R.id.TimeContent);
        tv_time_content.setText(startTime+"-"+stopTime+"    " +Time_conversion(startTime,stopTime));
        listView = findViewById(R.id.result_list);
        setData();
    }
    //获取1/5传递过来的参数
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getInt("YEAR");
        month = bundle.getInt("MONTH");
        day = bundle.getInt("DAY");
        stopPlace = bundle.getString("stopPlace");
        startPlace = bundle.getString("startPlace");
        traniNumber = bundle.getString("TrainNumber");
        startTime = bundle.getString("StartTime");
        stopTime = bundle.getString("StopTime");
        indexNumber = bundle.getInt("SetType");
    }
    //设置数据
    private void setData() {
        timeCorrection = new TimeCorrection(this,tv_query_date,calendar,year,month,day);
        tv_query_date.setText(year + "-" + month + "-" + day + "（" + timeCorrection.getCurrentWeek(year, month, day) + "）");
        tv_place_interval.setText(startPlace+"-"+stopPlace);
        tv_train_number.setText(traniNumber);
        getSet();
        ticketinformations = new String[] {
                String.valueOf(year), String.valueOf(month), String.valueOf(day), startPlace,stopPlace, traniNumber,
                startTime, stopTime};
        Log.d(TAG, "setData: "+String.valueOf(day));
        TicketReservationDetailAdapter adapter = new TicketReservationDetailAdapter(this,
                test_data,ticket_info,ticketinformations);
        listView.setAdapter(adapter);
    }

    //    将字符串切片，获取座位类型和剩余票数
    private void getSet(){
        ticket_info = new ArrayList<>();
        test_data = new ArrayList<>();
        String[] set = new String[2];
        String[] tickets = QueryTestData.addData().get(indexNumber);
        for (int i = 0; i < tickets.length; i++) {
            HashMap<String,Object> content_data = new HashMap<>();
            set = tickets[i].split("：");
            content_data = new HashMap<>();
            content_data.put("set", set[0]);
            content_data.put("remaining", set[1]);
            content_data.put("price", 210.0);
            ticket_info.add(content_data);
            test_data.add(content_data);
        }
    }



    //    将时间从字符串转化为日期类型，计算经过时间
    private String Time_conversion(String starttime, String stoptime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date startdate = null;
        Date stopdate = null;
        long time_difference = 0;
        long hours = 0;
        long minutes = 0;
        try {
            startdate = dateFormat.parse(starttime);
            stopdate = dateFormat.parse(stoptime);
            time_difference = stopdate.getTime() - startdate.getTime();
            hours = time_difference / (1000 * 60 * 60);
            if (hours < 1)
                hours = 0;
            minutes = time_difference / (1000* 60) - (hours * 60);
            if (minutes < 1)
                minutes = 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return "历时" +hours + "时" + minutes + "分";
        }
    }
}
