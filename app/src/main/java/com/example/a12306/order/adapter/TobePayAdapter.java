package com.example.a12306.order.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/912:29
 * desc   :
 * version: 1.0
 */
public class TobePayAdapter extends BaseAdapter {
    private Context context;
    private List<TicketNew.PassengerListBean> data;
    private String trainNo,trainDate;
    private static final String TAG = "TicketToBeConfirmedAdap";

    public TobePayAdapter(Context context, List<TicketNew.PassengerListBean> data,String trainNo,String trainDate){
        this.context = context;
        this.data = data;
        this.trainNo = trainNo;
        this.trainDate = trainDate;

    }
    @Override
    public int getCount() {
        Log.e(TAG, "getCount: "+data.size() );
        return data.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
          viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_ticket_tobeconfirmedadapter,null);
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.date = convertView.findViewById(R.id.date);
            viewHolder.trainNumber = convertView.findViewById(R.id.trainNumber);
            viewHolder.setNumber = convertView.findViewById(R.id.setNumber);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            viewHolder.name.setText(data.get(position).getName());
            viewHolder.trainNumber.setText(trainNo);
            viewHolder.date.setText(trainDate);
            viewHolder.setNumber.setText(data.get(position).getSeat().getSeatNo());



        return convertView;

    }

    class ViewHolder{
        public TextView name, trainNumber, setNumber, date;
    }
}



