package com.example.a12306.ticket;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.adapter.AddPassengerAdapter;
import com.example.a12306.utils.CONSTANT;
import com.gyf.immersionbar.ImmersionBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class AddPassenger extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private Toolbar toolbar;
    private String date, trainNo, fromStationName, toStationName, startTime, arriveTime, seatName, seatNum, seatPrice, startTrainDate;
    private TextView tv_TicketPlaceFrom, tv_TicketTimeFrom, tv_TrainNumber, tv_Date,
            tv_TicketPlaceTo, tv_TicketTimeTo, tv_SeatName, tv_SeatPrice, tv_PassengerList, tv_OrderSumPrice, tv_Submit;
    private ListView lv_TicketPassengerList;
    public static ArrayList<HashMap<String, Object>> dispalyselected, allselectedpay;
    private AddPassengerAdapter adapter;
    public static AddPassenger addPassenger;
    public static ArrayList<HashMap<String, Object>> maplist;
    private String[] id;
    private String[] idType;
    private static Handler okHttpHandler = new Handler();
    public static ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private static final String TAG = "AddPassenger";

Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 1:
                List<TicketNew.PassengerListBean> pslist = (List<TicketNew.PassengerListBean>) msg.obj;
                list.clear();
                for (int i = 0; i < pslist.size(); i++) {
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("name",pslist.get(i).getName());
                    map.put("seatName",pslist.get(i).getSeat().getSeatNo());
                    list.add(map);
                }
                Intent intent = new Intent(AddPassenger.this,TicketToBeConfirmed.class);
                startActivity(intent);


                Log.e(TAG, "handleMessage: "+list );

        }

    }
};
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
        tv_TicketPlaceFrom.setText(fromStationName);
        tv_TicketTimeFrom = findViewById(R.id.tv_TicketTimeFrom);
        tv_TicketTimeFrom.setText(startTime);
        tv_TrainNumber = findViewById(R.id.tv_TrainNumber);
        tv_TrainNumber.setText(trainNo);
        tv_Date = findViewById(R.id.tv_Date);
        tv_Date.setText(date);
        tv_TicketPlaceTo = findViewById(R.id.tv_TicketPlaceTo);
        tv_TicketPlaceTo.setText(toStationName);
        tv_TicketTimeTo = findViewById(R.id.tv_TicketTimeTo);
        tv_TicketTimeTo.setText(arriveTime);
        tv_SeatName = findViewById(R.id.tv_SeatName);
        tv_SeatName.setText(seatName + "(" + seatNum + "张)");
        tv_SeatPrice = findViewById(R.id.tv_SeatPrice);
        tv_SeatPrice.setText(seatPrice + "元");
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
                        if (dispalyselected.size() == 0) {
                            dispalyselected = new ArrayList<>();
                        }
                        adapter.notifyDataSetChanged();
                        tv_OrderSumPrice.setText("订单总额：￥" + (float) adapter.getCount() * Float.parseFloat(seatPrice) + "元");
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
        date = bundle.getString("date");
        trainNo = bundle.getString("trainNo");
        fromStationName = bundle.getString("fromStationName");
        toStationName = bundle.getString("toStationName");
        startTime = bundle.getString("startTime");
        arriveTime = bundle.getString("arriveTime");
        seatName = bundle.getString("seatName");
        seatNum = bundle.getString("seatNum");
        seatPrice = bundle.getString("seatPrice");
        startTrainDate = bundle.getString("startTrainDate");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_PassengerList:
                startActivityForResult(new Intent(AddPassenger.this, SelectedPassenger.class), 0);

                break;
            case R.id.tv_Submit:
                new Thread(SubmitOrderThread).start();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 0) {
            if (resultCode == 1) {
                Bundle bundle = data.getExtras();
                dispalyselected = (ArrayList<HashMap<String, Object>>) bundle.getSerializable("passenger");
                id = new String[dispalyselected.size()];
                idType = new String[dispalyselected.size()];
                for (int i = 0; i < dispalyselected.size(); i++) {

                    id[i] = dispalyselected.get(i).get("id").toString();
                    idType[i] = dispalyselected.get(i).get("idType").toString();
                    ;
                    Log.e(TAG, "onResume: " + id[i] + idType[i]);
                }

                allselectedpay.addAll(dispalyselected);
                adapter = new AddPassengerAdapter(this, dispalyselected);
                lv_TicketPassengerList.setAdapter(adapter);


                adapter.notifyDataSetChanged();
                tv_OrderSumPrice.setText("订单总额：￥" + (float) adapter.getCount() * Float.parseFloat(seatPrice) + "元");
            }

        }
    }


    //提交订单限制
    private void subLimit() {
        if (dispalyselected.size() == 0) {
            tv_Submit.setEnabled(false);
            Toast.makeText(AddPassenger.this, "当前乘车人不能为空", Toast.LENGTH_SHORT).show();
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
                if (dispalyselected.size() == 0) {
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

    //设置数据
    public void setData(List<TicketNew> ticketNewList) {
        maplist = new ArrayList<>();
        for (int i = 0; i < ticketNewList.size(); i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("orderId", ticketNewList.get(i).getId());
            map.put("trainNo", ticketNewList.get(i).getTrain().getTrainNo());
            map.put("startTrainDate", ticketNewList.get(i).getTrain().getStartTrainDate());
            for (int j = 0; j < ticketNewList.get(i).getPassengerList().size(); j++) {
                map.put("name", ticketNewList.get(i).getPassengerList().get(j).getName());
                map.put("seatName", ticketNewList.get(i).getPassengerList().get(j).getSeat());
            }
            maplist.add(map);
            Log.e(TAG, "setData: " + maplist);
        }
    }

    Runnable SubmitOrderThread = new Runnable() {
        Message msg = handler.obtainMessage();
        @Override
        public void run() {
            String url = CONSTANT.HOST + "/otn/Order";
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  //设置超时时间
                    .readTimeout(30, TimeUnit.SECONDS)     //设置读取超时时间
                    .writeTimeout(30, TimeUnit.SECONDS);   //设置写入超时时间;
            OkHttpClient mOkHttpClient = clientBuilder.build();
            ;
            HashMap<String, String> params = new HashMap<>();
            params.put("trainNo", trainNo);
            params.put("startTrainDate", startTrainDate);
            params.put("seatName", seatName);
            RequestBody body = setRequestBody(params, url, id, idType);
            Request.Builder requestBuilder = new Request.Builder();
            Request request = requestBuilder
                    .addHeader("cookie", CONST.getCookie(getApplicationContext()))
                    .post(body)
                    .url(url)
                    .build();
            okhttp3.Call call = mOkHttpClient.newCall(request);
            call.enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull okhttp3.Call call, @NonNull final IOException e) {

                }

                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) {
                    try {
                        final String responseDate = response.body().string();
                        Log.e(TAG, "onResponse: " + responseDate);
                        okHttpHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    JSONObject prentJsonobj = new JSONObject(responseDate);
                                    List<TicketNew> ticketNewList = new ArrayList<>();
                                    String id = prentJsonobj.getString("id");
                                    String passengerList = prentJsonobj.getString("passengerList");
                                    String train = prentJsonobj.getString("train");
                                    String status = prentJsonobj.getString("status");
                                    String orderTime = prentJsonobj.getString("orderTime");
                                    String orderPrice = prentJsonobj.getString("orderPrice");

                                    TicketNew ticketNew = new TicketNew();
                                    ticketNew.setId(id);
                                    ticketNew.setOrderPrice(orderPrice);
                                    ticketNew.setOrderTime(orderTime);
                                    ticketNew.setStatus(status);


                                    //passengerList array
                                    List<TicketNew.PassengerListBean> passengerListBeanList = new ArrayList<>();
                                    passengerListBeanList.clear();
                                    JSONArray passengerListArray = new JSONArray(passengerList);


                                    for (int j = 0; j < passengerListArray.length(); j++) {

                                        TicketNew.PassengerListBean passengerListBean = new TicketNew.PassengerListBean();

                                        JSONObject childObjt = passengerListArray.getJSONObject(j);
                                        String idChild = childObjt.getString("id");
                                        String name = childObjt.getString("name");
                                        String idType = childObjt.getString("idType");
                                        String tel = childObjt.getString("tel");
                                        String seat = childObjt.getString("seat");
                                        String type = childObjt.getString("type");

                                        passengerListBean.setId(idChild);
                                        passengerListBean.setIdType(idType);
                                        passengerListBean.setName(name);
                                        passengerListBean.setTel(tel);
                                        passengerListBean.setType(type);


                                        TicketNew.PassengerListBean.SeatBean seatBean = new TicketNew.PassengerListBean.SeatBean();
                                        JSONObject seatFirstObect = new JSONObject(seat);
                                        String seatName = seatFirstObect.getString("seatName");
                                        String seatNum = seatFirstObect.getString("seatNum");
                                        String seatPrice = seatFirstObect.getString("seatPrice");
                                        String seatNo = seatFirstObect.getString("seatNo");
                                        seatBean.setSeatName(seatName);
                                        seatBean.setSeatNo(seatNo);
                                        seatBean.setSeatNum(seatNum);
                                        seatBean.setSeatPrice(seatPrice);
                                        passengerListBean.setSeat(seatBean);
                                        passengerListBeanList.add(passengerListBean);


                                    }
                                    ticketNew.setPassengerList(passengerListBeanList);

                                    //train objet
                                    TicketNew.TrainBean trainBean = new TicketNew.TrainBean();

                                    JSONObject trainObject = new JSONObject(train);
                                    String trainNo = trainObject.getString("trainNo");
                                    String fromStationName = trainObject.getString("fromStationName");
                                    String toStationName = trainObject.getString("toStationName");
                                    String startTime = trainObject.getString("startTime");
                                    String arriveTime = trainObject.getString("arriveTime");
                                    String dayDifference = trainObject.getString("dayDifference");
                                    String durationTime = trainObject.getString("durationTime");
                                    String startTrainDate = trainObject.getString("startTrainDate");
                                    String seats = trainObject.getString("seats");

                                    trainBean.setTrainNo(trainNo);
                                    trainBean.setArriveTime(arriveTime);
                                    trainBean.setDayDifference(dayDifference);
                                    trainBean.setDurationTime(durationTime);
                                    trainBean.setFromStationName(fromStationName);
                                    trainBean.setToStationName(toStationName);
                                    trainBean.setStartTime(startTime);
                                    trainBean.setStartTrainDate(startTrainDate);

                                    List<TicketNew.TrainBean.SeatsBean> seatsBeanList = new ArrayList<>();
                                    JSONObject seatsObject = new JSONObject(seats);
                                    List<String> keyList = new ArrayList<>();
                                    Iterator keys = seatsObject.keys();
                                    while (keys.hasNext()) {
                                        String key = (String) keys.next();
                                        keyList.add(key);
                                    }

                                    for (String data : keyList) {
                                        TicketNew.TrainBean.SeatsBean seatsBean = new TicketNew.TrainBean.SeatsBean();
                                        JSONObject seatsChildObject = seatsObject.getJSONObject(data);
                                        String seatName = seatsChildObject.getString("seatName");
                                        String seatNum = seatsChildObject.getString("seatNum");

                                        String seatPrice = seatsChildObject.getString("seatPrice");

                                        seatsBean.setSeatName(seatName);
                                        seatsBean.setSeatNum(seatNum);
                                        seatsBean.setSeatPrice(seatPrice);
                                        seatsBeanList.add(seatsBean);
                                    }

                                    trainBean.setSeats(seatsBeanList);
                                    ticketNew.setTrain(trainBean);
                                    ticketNewList.add(ticketNew);
                                    CONST.trainData = new String[20];
                                    for (int i = 0; i <3 ; i++) {
                                        CONST.trainData[0] = ticketNewList.get(0).getId();
                                        CONST.trainData[1] = ticketNewList.get(0).getTrain().getTrainNo();
                                        CONST.trainData[2] = ticketNewList.get(0).getTrain().getStartTrainDate();
                                    }

                                    msg.what = 1;
                                    msg.obj = passengerListBeanList;

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                handler.sendMessage(msg);
                            }


                        });
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                }
            });

        }

    };

    private static RequestBody setRequestBody(Map<String, String> BodyParams, String url, String[] id
            , String[] idType) {
        RequestBody body;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (BodyParams != null) {
            Iterator<String> iterator = BodyParams.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                formEncodingBuilder.add(key, BodyParams.get(key));
                Log.e("post Params==" + url, BodyParams.get(key));
            }
            for (String s : id) {
                formEncodingBuilder.add("id",s);
            }
            for(String e: idType){
                formEncodingBuilder.add("idType",e);
            }
        }


        body = formEncodingBuilder.build();
        return body;
    }
}