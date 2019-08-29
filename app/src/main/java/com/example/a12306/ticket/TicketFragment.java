package com.example.a12306.ticket;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a12306.MainActivity;
import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//订票
public class TicketFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView tvTicketStationFrom,tvTicketStationTo,tvTicketDateFrom;
    private ImageView imgTicketExchange;
    private Button btnTicketQuery;
    private ListView lv_query_history;
    private ArrayAdapter<String> adapter;
    private Calendar calendar;
    private int Year,Month,Day;
    private String stationFrom,stationTo;
    public TicketFragment(){

    }

    private static final String TAG = "TicketFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ticket_fragment, container, false);
        initView();
        return view;
    }

    //控件初始化
    private void initView() {
        ImmersionBar.with(this).init();
        calendar = Calendar.getInstance();
        tvTicketStationFrom = (TextView)view.findViewById(R.id.tvTicketStationFrom);
        tvTicketStationFrom.setOnClickListener(this);
        tvTicketStationTo = (TextView)view.findViewById(R.id.tvTicketStationTo);
        tvTicketStationTo.setOnClickListener(this);
        imgTicketExchange = (ImageView)view.findViewById(R.id.imgTicketExchange);
        imgTicketExchange.setOnClickListener(this);
        tvTicketDateFrom = (TextView)view.findViewById(R.id.tvTicketDateFrom);
        tvTicketDateFrom.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) +
                "-" + calendar.get(Calendar.DAY_OF_MONTH));
        tvTicketDateFrom.setOnClickListener(this);
        btnTicketQuery = (Button)view.findViewById(R.id.btnTicketQuery);
        btnTicketQuery.setOnClickListener(this);
        lv_query_history = (ListView)view.findViewById(R.id.lv_query_history);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1,
                CONST.query_history);
        lv_query_history.setAdapter(adapter);
    }

    //全局按钮事件的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //出发地
            case R.id.tvTicketStationFrom:
                SelectCityList(tvTicketStationFrom);
                break;
                //目的地
            case R.id.tvTicketStationTo:
                SelectCityList(tvTicketStationTo);
                break;
                //图片切换出发地和目的地
            case R.id.imgTicketExchange:
                stationFrom = tvTicketStationFrom.getText().toString();
                stationTo = tvTicketStationTo.getText().toString();
                TranslateAnimation animationFrom = new TranslateAnimation(0, 650, 0, 0);
                TranslateAnimation animationTo = new TranslateAnimation(0, 650, 0, 0);
                animationFrom.setDuration(300);
                animationFrom.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvTicketStationFrom.setText(stationTo);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                animationFrom.setDuration(300);
                animationFrom.setInterpolator(new AccelerateDecelerateInterpolator());
                animationFrom.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        tvTicketStationTo.setText(stationFrom);
                        tvTicketStationFrom.setText(stationTo);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                tvTicketStationTo.startAnimation(animationTo);
                tvTicketStationFrom.startAnimation(animationFrom);

                break;
                //查询时间选择
            case R.id.tvTicketDateFrom:
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvTicketDateFrom.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                        Year = year;
                        Month = month + 1;
                        Day = dayOfMonth;
                    }
                },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
                dialog.setCancelable(false);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
                break;
                //查询历史
            case R.id.btnTicketQuery:
            CONST.query_history.add(tvTicketStationFrom.getText().toString() + "------------------" +
                    "-----" + tvTicketStationTo.getText().toString());
            adapter.notifyDataSetChanged();//列表刷新
                Intent intent = new Intent(getActivity(),TicketReservation.class);
                if(Year == 0 || Month == 0|| Day == 0){
                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH) + 1;
                    Day = calendar.get(Calendar.DAY_OF_MONTH);
                }
                Bundle bundle = new Bundle();
                bundle.putInt("Year",Year);
                bundle.putInt("Month",Month);
                bundle.putInt("Day",Day);
                bundle.putString("startPlace",
                        tvTicketStationFrom.getText().toString() );
                bundle.putString("stopPlace",tvTicketStationTo.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }

    }

    //选择城市列表
    private void SelectCityList(final  TextView tv_place) {
       AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
       alert.setItems(CONST.cities, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               tv_place.setText(CONST.cities[which]);
               dialog.cancel();
           }
       });
       alert.setTitle("请选择城市:");
       alert.create().show();
    }


}
