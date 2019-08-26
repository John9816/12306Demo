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
import com.example.a12306.ticket.AddPassenger;

import java.util.ArrayList;
import java.util.HashMap;

public class TicketReservationDetailAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String,Object>>setStatus,ticket_info;
    private String[] ticket;
    public TicketReservationDetailAdapter(Context context,ArrayList<HashMap<String,Object>> setStatus,
                                          ArrayList<HashMap<String,Object>> ticket_info,
                                          String[] ticket){
        this.context = context;
        this.setStatus = setStatus;
        this.ticket_info = ticket_info;
        this.ticket = ticket;

    }
    @Override
    public int getCount() {
        return setStatus.size();
    }

    @Override
    public Object getItem(int position) {
        return setStatus.get(position);
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
            viewHolder.set = convertView.findViewById(R.id.setType);
            viewHolder.remaining = convertView.findViewById(R.id.remainingNumber);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.reservation = convertView.findViewById(R.id.reservation);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SetViewHolder) convertView.getTag();
        }
//        实现空间的内容修改和按钮的点击事件
        viewHolder.set.setText(setStatus.get(position).get("set").toString());
        viewHolder.remaining.setText(setStatus.get(position).get("remaining").toString());
        viewHolder.price.setText(setStatus.get(position).get("price").toString());
        viewHolder.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddPassenger.class);
                Bundle bundle = new Bundle();
                bundle.putString("year", ticket[0]);
                bundle.putString("month", ticket[1]);
                bundle.putString("day", ticket[2]);
                bundle.putString("startPlace", ticket[3]);
                bundle.putString("stopPlace", ticket[4]);
                bundle.putString("trainNumber", ticket[5]);
                bundle.putString("startTime", ticket[6]);
                bundle.putString("stopTime", ticket[7]);
                bundle.putString("setType", ticket_info.get(position).get("set").toString() + "（" +
                        ticket_info.get(position).get("remaining").toString() + "张）");
                bundle.putString("setPrice", ticket_info.get(position).get("price").toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
class SetViewHolder {
    public TextView set, remaining, price;
    public Button reservation;
}

