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
import com.example.a12306.others.Contact;
import com.example.a12306.others.QueryTestData;
import com.example.a12306.others.TimeCorrection;
import com.example.a12306.ticket.adapter.QueryResultAdapter;
import com.example.a12306.utils.CONSTANT;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class TicketReservation extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private TextView query_date, place_interval;
    private Button last_day, next_day;
    private ListView result_list;
    private int year, month, day;
    private String startPlace,stopPlace,startTrainDate;
    private Calendar calendar;
    private TimeCorrection timeCorrection;
    private ArrayList<HashMap<String, Object>> test_data;
    private HashMap<String, Object> content_data;
    public static TicketReservation ticketReservation;
    private static final String TAG = "TicketReservation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation);
        ImmersionBar.with(this).init();
        calendar = Calendar.getInstance();
        getDate();
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
        result_list.setOnItemClickListener(this);
        addInquiryResult(result_list);

//        销毁当前活动所需要的变量
        ticketReservation = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.last_day:
                timeCorrection.lastDay();
                break;
            case R.id.next_day:
               timeCorrection.nextDay();
                break;
        }
    }

    //    初始化日期，为用户当前时间
    private void getDate() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        year = bundle.getInt("Year",calendar.get(Calendar.YEAR));
        month = bundle.getInt("Month",calendar.get(Calendar.MONTH) + 1);
        day = bundle.getInt("Day",calendar.get(Calendar.DAY_OF_MONTH));
        startPlace = bundle.getString("startPlace");
        stopPlace = bundle.getString("stopPlace");
        startTrainDate = year + "-" + month + "-" + day;

        new Thread(){
            @Override
            public void run() {
                super.run();

                try{
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("fromStationName",startPlace)
                            .add("toStationName",stopPlace)
                            .add("startTrainDate",startTrainDate)
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie",CONST.getCookie(getApplication()))
                            .url(CONSTANT.HOST + "/otn/TrainList")
                            .post(requestBody)
                            .build();
                    Log.d(TAG, "request: "+request);
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d(TAG, "responseDate: "+responseData);
                    JSONArray jsonArray = new JSONArray(responseData);
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        Log.d(TAG, "run: "+jsonObject.getString("trainNo"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }





    private void addInquiryResult(ListView listView) {
        test_data = new ArrayList<>();
        content_data = null;
        for (int i = 0; i < QueryTestData.Train_number.length; i++) {
            content_data = new HashMap<>();
            content_data.put("Train_number", QueryTestData.Train_number[i]);
            content_data.put("Start_state", "始");
            content_data.put("Stop_state", QueryTestData.Stop_state[i]);
            content_data.put("Start_time", QueryTestData.Start_time[i]);
            content_data.put("Stop_time", QueryTestData.Stop_time[i]);
            test_data.add(content_data);
        }
        QueryResultAdapter resultViewAdapter = new QueryResultAdapter(this, test_data);
        listView.setAdapter(resultViewAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(TicketReservation.this, TicketReservationDetail.class);
        Bundle bundle = new Bundle();
        bundle.putInt("YEAR", timeCorrection.getYear());
        bundle.putInt("MONTH", timeCorrection.getMonth());
        bundle.putInt("DAY", timeCorrection.getDay());
        Log.d(TAG, "onItemClick: "+year+month+day);
        bundle.putString("startPlace", startPlace);
        bundle.putString("stopPlace",stopPlace);
        bundle.putString("TrainNumber", QueryTestData.Train_number[position]);
        bundle.putString("StartTime", QueryTestData.Start_time[position]);
        bundle.putString("StopTime", QueryTestData.Stop_time[position]);
        bundle.putInt("SetType", position);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
