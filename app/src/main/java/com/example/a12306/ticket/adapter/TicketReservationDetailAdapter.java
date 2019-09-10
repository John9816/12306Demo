package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.bean.TicketBean;
import com.example.a12306.ticket.AddPassenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketReservationDetailAdapter extends BaseAdapter {
   private Context context;
    private List<TicketBean.SeatsBean> data;
    private String date,trainNo,fromStationName,toStationName,startTime,arriveTime,startTrainDate;

    public TicketReservationDetailAdapter(Context context, List<TicketBean.SeatsBean> data){
        this.context = context;
        this.data = data;
    }
    public void setTicketDate (String date,
                   String trainNo, String fromStationName, String toStationName,
                   String startTime,String arriveTime,String startTrainDate){
        this.date = date;
        this.trainNo = trainNo;
        this.fromStationName = fromStationName;
        this.toStationName = toStationName;
        this.startTime = startTime;
        this.arriveTime = arriveTime;
        this.startTrainDate = startTrainDate;

    }
    @Override
    public int getCount() {
        return data.size();
    }

    public void setData(List<TicketBean.SeatsBean> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SetViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_ticket_reservation_detailadapter, null);
            viewHolder = new SetViewHolder();
            viewHolder.seatName = convertView.findViewById(R.id.setType);
            viewHolder.seatNum = convertView.findViewById(R.id.tv_seatNum);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.reservation = convertView.findViewById(R.id.reservation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SetViewHolder) convertView.getTag();
        }
//        实现空间的内容修改和按钮的点击事件
        viewHolder.seatName.setText(data.get(position).getSeatName());
        viewHolder.seatNum.setText(data.get(position).getSeatNum());
        viewHolder.price.setText(data.get(position).getSeatPrice());
        viewHolder.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPassenger.class);
                Bundle bundle = new Bundle();
                bundle.putString("seatName", data.get(position).getSeatName());
                bundle.putString("seatNum", data.get(position).getSeatNum());
                bundle.putString("seatPrice", data.get(position).getSeatPrice());
                bundle.putString("date", date);
                bundle.putString("trainNo",trainNo);
                bundle.putString("fromStationName", fromStationName);
                bundle.putString("toStationName",toStationName);
                bundle.putString("startTime", startTime);
                bundle.putString("arriveTime",arriveTime);
                bundle.putString("startTrainDate",startTrainDate);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        return convertView;
    }
}
class SetViewHolder {
    public TextView seatName, seatNum, price;
    public Button reservation;
}

