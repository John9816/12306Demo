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
import com.example.a12306.bean.TicketBean;
import com.example.a12306.others.CONST;
import com.example.a12306.utils.TimeCorrection;
import com.example.a12306.ticket.adapter.QueryResultAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class TicketReservation extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView query_date, place_interval;
    private Button last_day, next_day;
    private ListView result_list;
    private int year, month, day;
    private String startPlace,stopPlace;
    private Calendar calendar;
    private TimeCorrection timeCorrection;
    public static TicketReservation ticketReservation;
    private QueryResultAdapter resultViewAdapter;
    private static final String TAG = "TicketReservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation);
        ImmersionBar.with(this).init();
        calendar = Calendar.getInstance();
        getDate();

        resultViewAdapter = new QueryResultAdapter(this,new ArrayList<TicketBean>());
        result_list.setAdapter(resultViewAdapter);
        CONST.QueryThread(startPlace,stopPlace,year+"-"+month+"-"+ day,TicketReservation.this,resultViewAdapter);
        ticketReservation = this;
        result_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TicketReservation.this, TicketReservationDetail.class);
                Bundle bundle = new Bundle();
                bundle.putInt("YEAR", timeCorrection.getYear());
                bundle.putInt("MONTH", timeCorrection.getMonth());
                bundle.putInt("DAY", timeCorrection.getDay());
                bundle.putString("fromStationName", startPlace);
                bundle.putString("toStationName",stopPlace);
                bundle.putString("startTime",CONST.ticketBeans.get(position).getStartTime());
                bundle.putString("startTrainDate",timeCorrection.getYear()+ "-" +timeCorrection.getMonth()
                + "-" + timeCorrection.getDay());
                bundle.putString("trainNo",CONST.ticketBeans.get(position).getTrainNo());
                bundle.putString("arriveTime",CONST.ticketBeans.get(position).getArriveTime());
                bundle.putString("durationTime",CONST.ticketBeans.get(position).getDurationTime());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        final String date;
        switch (v.getId()) {
            case R.id.last_day:
               date =  timeCorrection.lastDay();
                Log.d(TAG, "onClick: "+date);
                new Thread(){
                    @Override
                    public void run() {

                        CONST.QueryThread(startPlace,stopPlace,date,TicketReservation.this,resultViewAdapter);

                    }
                }.start();
                break;
            case R.id.next_day:
              date =  timeCorrection.nextDay();
                new Thread(){
                    @Override
                    public void run() {

                        CONST.QueryThread(startPlace,stopPlace,date,TicketReservation.this,resultViewAdapter);

                    }
                }.start();                break;
        }

    }
    //获取上一个页面传来的数据
    private void getDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getInt("Year",calendar.get(Calendar.YEAR));
        month = bundle.getInt("Month",calendar.get(Calendar.MONTH) + 1);
        day = bundle.getInt("Day",calendar.get(Calendar.DAY_OF_MONTH));
        startPlace = bundle.getString("startPlace");
        stopPlace = bundle.getString("stopPlace");
        toolbar = CONST.usrToolbar(R.id.reservation1Head, "车票预定1/5", this, 0);
        query_date = findViewById(R.id.query_date);
        place_interval = findViewById(R.id.place_interval);
        last_day = findViewById(R.id.last_day);
        next_day = findViewById(R.id.next_day);
        result_list = findViewById(R.id.result_list);
        timeCorrection = new TimeCorrection(this,query_date,calendar,year,month,day);
        query_date.setText(year + "-" + month + "-" + day + "（" + timeCorrection.getCurrentWeek(year, month, day) + "）");
        place_interval.setText(startPlace+"-"+stopPlace);
        last_day.setOnClickListener(this);
        next_day.setOnClickListener(this);
    }








}
