package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.a12306.R;
import com.example.a12306.bean.TicketBean;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.TicketReservationDetailAdapter;
import com.example.a12306.utils.TimeCorrection;
import com.gyf.immersionbar.ImmersionBar;
import java.util.Calendar;
import java.util.List;


/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//车票预定详细信息
public class TicketReservationDetail extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Calendar calendar;
    private TimeCorrection timeCorrection;
    private Button last_day,next_day;
    private TextView tv_query_date,tv_place_interval,tv_train_number,tv_time_content;
    private ListView listView;
    private int year,month,day;
    private String fromStationName,toStationName,startTrainDate,trainNo,startTime,arriveTime,durationTime;
    private int indexNumber;
    public static TicketReservationDetail ticketReservationDetail;
    private TicketReservationDetailAdapter adapter;

    private static final String TAG = "TicketReservationDetail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation_detail);
        toolbar = CONST.usrToolbar(R.id.reservationhead2, "车票预定2/5", this, 0);
        ImmersionBar.with(this).init();
        calendar = Calendar.getInstance();
        getData();
        adapter = new TicketReservationDetailAdapter(TicketReservationDetail.this,CONST.beanList);
        listView.setAdapter(adapter);
        CONST.QueryThreadDetail(fromStationName,toStationName,startTrainDate,trainNo,TicketReservationDetail.this,adapter);
        adapter.setTicketDate(tv_query_date.getText().toString(),
                trainNo,fromStationName,toStationName,startTime,arriveTime,startTrainDate);
        ticketReservationDetail = this;
    }


    @Override
    public void onClick(View v) {
        final String date;
        switch (v.getId()){
            case R.id.last_day:
                date = timeCorrection.lastDay();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        CONST.QueryThreadDetail(fromStationName,toStationName,date,trainNo,TicketReservationDetail.this,
                                adapter);
                    }
                }.start();

                break;
            case R.id.next_day:
                date = timeCorrection.nextDay();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        CONST.QueryThreadDetail(fromStationName,toStationName,date,trainNo,TicketReservationDetail.this,
                                adapter);
                    }
                }.start();
                break;
        }
    }

    //获取1/5传递过来的参数
    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getInt("YEAR");
        month = bundle.getInt("MONTH");
        day = bundle.getInt("DAY");
        fromStationName = bundle.getString("fromStationName");
        toStationName = bundle.getString("toStationName");
        trainNo = bundle.getString("trainNo");
        startTime = bundle.getString("startTime");
        arriveTime = bundle.getString("arriveTime");
        indexNumber = bundle.getInt("SetType");
        startTrainDate = bundle.getString("startTrainDate");
        durationTime = bundle.getString("durationTime");
        last_day = (Button)findViewById(R.id.last_day);
        next_day = (Button)findViewById(R.id.next_day);
        tv_query_date = (TextView)findViewById(R.id.query_date);
        tv_place_interval = findViewById(R.id.place_interval);
        tv_train_number = findViewById(R.id.TrainNumber);
        tv_time_content = findViewById(R.id.TimeContent);
        last_day.setOnClickListener(this);
        next_day.setOnClickListener(this);
        tv_time_content.setText(startTime+"-"+arriveTime+"       历时" +durationTime);
        timeCorrection = new TimeCorrection(this,tv_query_date,calendar,year,month,day);
        tv_query_date.setText(year + "-" + month + "-" + day + "（" + timeCorrection.getCurrentWeek(year, month, day) + "）");
        tv_place_interval.setText(fromStationName+"-"+toStationName);
        tv_train_number.setText(trainNo);
        listView = findViewById(R.id.result_list);

    }



}
