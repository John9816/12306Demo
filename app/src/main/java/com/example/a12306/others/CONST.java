package com.example.a12306.others;

import android.app.Activity;
import android.bluetooth.le.AdvertiseData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.bean.Contact;
import com.example.a12306.bean.TicketBean;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.order.AllPayFragment;
import com.example.a12306.order.UnPayFragment;
import com.example.a12306.order.adapter.AllPayAdapter;
import com.example.a12306.order.adapter.UnPayAdapter;
import com.example.a12306.ticket.AddPassenger;
import com.example.a12306.ticket.SelectedPassenger;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.example.a12306.ticket.adapter.QueryResultAdapter;
import com.example.a12306.ticket.adapter.SelectedPassengerAdapter;
import com.example.a12306.ticket.adapter.TicketReservationDetailAdapter;
import com.example.a12306.utils.CONSTANT;
import com.zhy.http.okhttp.OkHttpUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.content.Context.MODE_PRIVATE;
import static com.example.a12306.ticket.AddPassenger.dispalyselected;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public final class CONST {

    private static final String TAG = "CONST";
    public static final String[] passenger = {"成人", "学生"};
    public static final String[] idType = {"身份证", "军人证", "港澳通行证"};
    public static final String[] mydatas = {"我的联系人", "我的账户", "我的密码"};
    public static final int[] images = {R.drawable.forward_25, R.drawable.forward_25};
    public static final String[] cities = {"北京", "大连", "上海", "天津", "重庆", "哈尔滨", "长春", "沈阳"
            , "呼和浩特", "石家庄", "乌鲁木齐", "兰州", "西宁", "西安", "银川", "郑州", "济南", "太原", "合肥", "武汉", "长沙"
            , "南京", "成都", "贵阳", "昆明", "南宁", "拉萨", "杭州", "南宁", "广州", "福州", "台北", "海口", "香港", "澳门"};
    public static ArrayList<String> query_history =
            new ArrayList<String>();
    //我的联系人
    public static ArrayList<Map<String, Object>> passenger_info = new ArrayList<Map<String, Object>>();
    //乘客信息
    public static ArrayList<Map<String,Object>> ticket_passenger_info = new ArrayList<>();
    //个人信息
    public static List<Map<String, Object>> personal_info = new ArrayList<Map<String, Object>>();
    public static List<TicketBean> ticketBeans = new ArrayList<>();
    public static List<TicketBean.SeatsBean> beanList = new ArrayList<>();
    public static ArrayList<Contact> contactArrayList = new ArrayList<>();
    private static OkHttpClient client = new OkHttpClient();//公用请求
   private static Handler okHttpHandler = new Handler();
    public static List<TicketNew> ticketNewList=new ArrayList<>();//全部订单
    public static List<TicketNew>  unPayNewList = new ArrayList<>();//未支付订单
    public static List<TicketNew.PassengerListBean> passengerListBeanList;
    public static String[] trainData = new String[20];


    //   Toolbar 静态方法
    public static Toolbar usrToolbar(int id, String title, final Activity activity, int Menuid) {
        Toolbar toolbar = activity.findViewById(id);
        toolbar.setTitle(title);
        if (Menuid != 0) {
            toolbar.inflateMenu(Menuid);
        }
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return toolbar;
    }

    //获取cookie
    public static String getCookie(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("userinfo", MODE_PRIVATE);
        String value = preferences.getString("cookie", "");
        return value;

    }

    //查询结果显示
    public static void QueryThread(final String fromStationName, final String toStationName,
                                   final String date, final Context context, final QueryResultAdapter adapter) {


        RequestBody requestBody = new FormBody.Builder()
                .add("fromStationName", fromStationName)
                .add("toStationName", toStationName)
                .add("startTrainDate", date)
                .build();

        Request request = new Request.Builder()
                .addHeader("cookie", CONST.getCookie(context))
                .url(CONSTANT.HOST + "/otn/TrainList")
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        // 异步请求更新数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                final String reqStr = response.body().string();
                okHttpHandler.post(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            //解析JSONArray,取得对象，创建实体设置实体
                            JSONArray jsonArray = new JSONArray(reqStr);
                            ticketBeans = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                TicketBean ticketBean = new TicketBean();
                                JSONObject childObject = jsonArray.getJSONObject(i);
                                ticketBean.setTrainNo(childObject.getString("trainNo"));
                                ticketBean.setStartStationName(childObject.getString("startStationName"));
                                ticketBean.setEndStationName(childObject.getString("endStationName"));
                                ticketBean.setFromStationName(childObject.getString("fromStationName"));
                                ticketBean.setToStationName(childObject.getString("toStationName"));
                                ticketBean.setStartTime(childObject.getString("startTime"));
                                ticketBean.setArriveTime(childObject.getString("arriveTime"));
                                ticketBean.setDayDifference(childObject.getInt("dayDifference"));
                                ticketBean.setDurationTime(childObject.getString("durationTime"));
                                ticketBean.setStartTrainDate(childObject.getString("startTrainDate"));

                                String seats = childObject.getString("seats");

                                JSONObject seatsJsonboject = new JSONObject(seats);

                                List<String> keyList = new ArrayList<>();
                                List<TicketBean.SeatsBean> seatsBeanList = new ArrayList<>();
                                Iterator keys = seatsJsonboject.keys();
                                while (keys.hasNext()) {
                                    String key = (String) keys.next();
                                    keyList.add(key);
                                }


                                for (String data : keyList) {
                                    TicketBean.SeatsBean seatsBean = new TicketBean.SeatsBean();
                                    JSONObject seatsChildObject = seatsJsonboject.getJSONObject(data);
                                    String seatName = seatsChildObject.getString("seatName");
                                    String seatNum = seatsChildObject.getString("seatNum");
                                    String seatPrice = seatsChildObject.getString("seatPrice");
                                    seatsBean.setSeatName(seatName);
                                    seatsBean.setSeatNum(seatNum);
                                    seatsBean.setSeatPrice(seatPrice);
                                    seatsBeanList.add(seatsBean);
                                }
                                ticketBean.setSeats(seatsBeanList);
                                ticketBeans.add(ticketBean);
                            }
                            adapter.setList(ticketBeans);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


    //列车详细信息显示
    public static void QueryThreadDetail(final String fromStationName,
                                         final String toStationName, final String date,
                                         final String trainNo, final Context context, final TicketReservationDetailAdapter adapter1) {


                    RequestBody requestBody = new FormBody.Builder()
                            .add("fromStationName", fromStationName)
                            .add("toStationName", toStationName)
                            .add("startTrainDate", date)
                            .add("trainNo", trainNo)
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("cookie", CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/Train")
                            .post(requestBody)
                            .build();

                   Call call = client.newCall(request);
                   //创建异步
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            final String responseData = response.body().string();

                          okHttpHandler.post(new Runnable() {
                              @Override
                              public void run() {
                                  try{
                                      JSONObject jsonObject = new JSONObject(responseData);
                                      TicketBean bean = new TicketBean();
                                      bean.setTrainNo(jsonObject.getString("trainNo"));
                                      bean.setStartStationName(jsonObject.getString("startStationName"));
                                      bean.setEndStationName(jsonObject.getString("endStationName"));
                                      bean.setFromStationName(jsonObject.getString("fromStationName"));
                                      bean.setToStationName(jsonObject.getString("toStationName"));
                                      bean.setStartTime(jsonObject.getString("startTime"));
                                      bean.setArriveTime(jsonObject.getString("arriveTime"));
                                      bean.setDayDifference(jsonObject.getInt("dayDifference"));
                                      bean.setDurationTime(jsonObject.getString("durationTime"));
                                      bean.setStartTrainDate(jsonObject.getString("startTrainDate"));
                                      String seats = jsonObject.getString("seats");

                                      JSONObject js1 = new JSONObject(seats);
                                      List<String> keylist = new ArrayList<>();
                                      Iterator iterator = js1.keys();
                                      while (iterator.hasNext()) {
                                          String key1 = (String) iterator.next();
                                          keylist.add(key1);
                                      }


                                      beanList = new ArrayList<>();
                                      for (String data : keylist) {
                                          TicketBean.SeatsBean seatsBean = new TicketBean.SeatsBean();
                                          JSONObject seatsChildObject = js1.getJSONObject(data);
                                          String seatName = seatsChildObject.getString("seatName");
                                          String seatNum = seatsChildObject.getString("seatNum");
                                          String seatPrice = seatsChildObject.getString("seatPrice");
                                          seatsBean.setSeatName(seatName);
                                          seatsBean.setSeatNum(seatNum);
                                          seatsBean.setSeatPrice(seatPrice);
                                          beanList.add(seatsBean);
                                          bean.setSeats(beanList);
                                      }
                                      adapter1.setData(beanList);

                                  } catch (JSONException e) {
                                      e.printStackTrace();
                                  }
                              }
                          });
                        }
                    });




    }


    //添加乘车人显示
    public static void AddPassengerThread(final Context context, final SelectedPassengerAdapter adapter){

                    Request request = new Request.Builder()
                            .addHeader("Cookie",CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/TicketPassengerList")
                            .build();

                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            final String responseData = response.body().string();
                            okHttpHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        JSONArray jsonArray = new JSONArray(responseData);
                                        JSONObject jsonObject;
                                        contactArrayList = new ArrayList<>();
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            jsonObject = jsonArray.getJSONObject(i);
                                            Contact contact = new Contact();
                                            contact.setName(jsonObject.getString("name"));
                                            contact.setId(jsonObject.getString("id"));
                                            contact.setIdType(jsonObject.getString("idType"));
                                            contact.setType(jsonObject.getString("type"));
                                            contact.setTel(jsonObject.getString("tel"));
                                            contactArrayList.add(contact);
                                        }

                                        CONST.ticket_passenger_info = new ArrayList<>();
                                        for (int i = 0; i < CONST.contactArrayList.size(); i++) {
                                            HashMap<String,Object> map = new HashMap<>();
                                            Contact contact = CONST.contactArrayList.get(i);
                                            map.put("name",contact.getName()+"("+contact.getType()+")");
                                            map.put("idType",contact.getIdType());
                                            map.put("id",contact.getId());
                                            map.put("type",contact.getType());
                                            map.put("tel","电话:"+contact.getTel());
                                            map.put("phone",contact.getTel());
                                            CONST.ticket_passenger_info.add(map);
                                        }
                                        adapter.setData(CONST.ticket_passenger_info);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });


                        }
                    });



    }


    //提交订单
    public static void SubmitOrderThread(final String trainNo, final String startTrainDate,
                                         final String seatName, final String[] id, final String[] idType,
                                         final Context context){

        String url = CONSTANT.HOST + "/otn/Order";
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)  //设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)     //设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS);   //设置写入超时时间;
        OkHttpClient  mOkHttpClient = clientBuilder.build();;
        HashMap<String, String> params = new HashMap<>();
        params.put("trainNo", trainNo);
        params.put("startTrainDate", startTrainDate);
        params.put("seatName", seatName);
        RequestBody body = setRequestBody(params, url,id,idType);
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder
                .addHeader("cookie",CONST.getCookie(context))
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
                    Log.e(TAG, "onResponse: "+responseDate );
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

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    });
                } catch (IOException e1) {
                    e1.printStackTrace();
                }


            }
        });


    }

    private static RequestBody setRequestBody(Map<String, String> BodyParams, String url, String[] id
    ,String[] idType) {
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



    //全部订单
    public static void OrderThread(final Context context, final AllPayAdapter adapter){


                    RequestBody requestBody = new FormBody.Builder()
                            .add("status","1")
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie", CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/OrderList")
                            .post(requestBody)
                            .build();
                    Call call = client.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
                            final String responseDate = response.body().string();

                            okHttpHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        ticketNewList.clear();
                                        JSONArray prentJsonary = new JSONArray(responseDate);
                                        for (int i = 0; i < prentJsonary.length(); i++) {
                                            JSONObject prentObject = prentJsonary.getJSONObject(i);
                                            String id = prentObject.getString("id");
                                            String passengerList = prentObject.getString("passengerList");
                                            String train = prentObject.getString("train");
                                            String status = prentObject.getString("status");
                                            String orderTime = prentObject.getString("orderTime");
                                            String orderPrice = prentObject.getString("orderPrice");

                                            TicketNew ticketNew = new TicketNew();
                                            ticketNew.setId(id);
                                            ticketNew.setOrderPrice(orderPrice);
                                            ticketNew.setOrderTime(orderTime);
                                            ticketNew.setStatus(status);

                                            //passengerList array

                                            List<TicketNew.PassengerListBean> passengerListBeanList = new ArrayList<>();
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

                                        }
                                        Log.e(TAG, "run: "+ticketNewList.toString() );
                                        adapter.notifyDataSetChanged();
                                        adapter.setData(ticketNewList);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }


                            });
                        }
                    });





    }

    //未支付订单
    public static void UnPayThread(final Context context, final UnPayAdapter adapter){


        RequestBody requestBody = new FormBody.Builder()
                .add("status","0")
                .build();
        Request request = new Request.Builder()
                .addHeader("cookie", CONST.getCookie(context))
                .url(CONSTANT.HOST + "/otn/OrderList")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull final Call call, @NotNull Response response) throws IOException {
                final String responseDate = response.body().string();

                okHttpHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            unPayNewList.clear();
                            JSONArray prentJsonary = new JSONArray(responseDate);
                            for (int i = 0; i < prentJsonary.length(); i++) {
                                JSONObject prentObject = prentJsonary.getJSONObject(i);
                                String id = prentObject.getString("id");
                                String passengerList = prentObject.getString("passengerList");
                                String train = prentObject.getString("train");
                                String status = prentObject.getString("status");
                                String orderTime = prentObject.getString("orderTime");
                                String orderPrice = prentObject.getString("orderPrice");
                                // AllPayFragment.status[i] = status;
                                TicketNew ticketNew = new TicketNew();
                                ticketNew.setId(id);
                                ticketNew.setOrderPrice(orderPrice);
                                ticketNew.setOrderTime(orderTime);
                                ticketNew.setStatus(status);

                                //passengerList array

                               passengerListBeanList = new ArrayList<>();
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
                                unPayNewList.add(ticketNew);


                            }
                            adapter.notifyDataSetChanged();
                            adapter.setData(unPayNewList);
                            Log.e(TAG, "run: "+passengerListBeanList.size() );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                });
            }
        });





    }

    // 取消订单
    public static void CancelThread(final Context context,final String orderId){
        new Thread(){

            @Override
            public void run() {
                super.run();
                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("orderId",orderId)
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie", CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/Cancel")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();
                    Log.e(TAG, "Cancel: "+responseDate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //订单支付
    public static void OrderPay(final Context context,final String orderId){

        new Thread(){

            @Override
            public void run() {
                super.run();

                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("orderId",orderId)
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie", CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/Pay")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();
                    Log.e(TAG, "pay: "+responseDate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    //退票
    public static void RefundThread(final Context context, final String id, final String idType, final String orderId){
        new Thread(){

            @Override
            public void run() {
                super.run();

                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("orderId",orderId)
                            .add("id",id)
                            .add("idType",idType)
                            .build();
                    Request request = new Request.Builder()
                            .addHeader("cookie", CONST.getCookie(context))
                            .url(CONSTANT.HOST + "/otn/Refund")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseDate = response.body().string();
                    Log.e(TAG, "RefundThread: "+responseDate);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}


