package com.example.a12306.ticket;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a12306.MainActivity;
import com.example.a12306.R;

import java.util.Calendar;

//订票
public class TicketFragment extends Fragment implements View.OnClickListener{
    private View view;
    private TextView tvTicketStationFrom,tvTicketStationTo,tvTicketDateFrom;
    private ImageView imgTicketExchange;
    public TicketFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_ticket_fragment, container, false);
        initView();
        return view;
    }

    //控件初始化
    private void initView() {
        tvTicketStationFrom = (TextView)view.findViewById(R.id.tvTicketStationFrom);
        tvTicketStationFrom.setOnClickListener(this);
        tvTicketStationTo = (TextView)view.findViewById(R.id.tvTicketStationTo);
        tvTicketStationTo.setOnClickListener(this);
        imgTicketExchange = (ImageView)view.findViewById(R.id.imgTicketExchange);
        imgTicketExchange.setOnClickListener(this);
        tvTicketDateFrom = (TextView)view.findViewById(R.id.tvTicketDateFrom);
        tvTicketDateFrom.setOnClickListener(this);
    }

    //全局按钮事件的监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //出发地
            case R.id.tvTicketStationFrom:
                Intent intent = new Intent(getActivity(),CityActivity.class);
                startActivity(intent);
                break;
                //目的地
            case R.id.tvTicketStationTo:
                Intent intent1 = new Intent(getActivity(),CityActivity.class);
                startActivity(intent1);
                break;
                //图片切换出发地和目的地
            case R.id.imgTicketExchange:
                break;
                //查询时间选择
            case R.id.tvTicketDateFrom:
                Calendar cale1 = Calendar.getInstance();
                new DatePickerDialog(getActivity(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                      tvTicketDateFrom.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }
                        ,cale1.get(Calendar.YEAR)
                        ,cale1.get(Calendar.MONTH)
                        ,cale1.get(Calendar.DAY_OF_MONTH)).show();

        }

    }






}
