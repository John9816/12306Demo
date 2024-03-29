package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.ticket.TicketToBeConfirmed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.a12306.others.CONST.trainData;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class TicketToBeConfirmedAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String,Object>> data;
    private static final String TAG = "TicketToBeConfirmedAdap";

    public TicketToBeConfirmedAdapter(Context context, ArrayList<HashMap<String,Object>> data){
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
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
        ViewHolder ToBeConfirm;
        if(convertView == null){
            ToBeConfirm = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_ticket_tobeconfirmedadapter,null);
            ToBeConfirm.name = convertView.findViewById(R.id.name);
            ToBeConfirm.date = convertView.findViewById(R.id.date);
            ToBeConfirm.trainNumber = convertView.findViewById(R.id.trainNumber);
            ToBeConfirm.setNumber = convertView.findViewById(R.id.setNumber);
            convertView.setTag(ToBeConfirm);
        }else {
            ToBeConfirm = (ViewHolder) convertView.getTag();
        }



        ToBeConfirm.name.setText(data.get(position).get("name").toString());
        ToBeConfirm.trainNumber.setText(trainData[1]);
        ToBeConfirm.date.setText(trainData[2]);
        ToBeConfirm.setNumber.setText(data.get(position).get("seatName").toString());
        return convertView;

    }

    class ViewHolder{
        public TextView name, trainNumber, setNumber, date;
    }
}


