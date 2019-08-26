package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.ticket.SelectedPassenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SelectedPassengerAdapter extends BaseAdapter {
    private Context context;
    private  ArrayList<Map<String,Object>> data;
    private static final String TAG = "SelectedPassengerAdapte";

    public SelectedPassengerAdapter(Context context, ArrayList<Map<String,Object>> data){

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
        ViewHolder passengerHolder;
        if(convertView == null){
            passengerHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_selected_passenger, null);
            passengerHolder.checkBox = convertView.findViewById(R.id.choosePassenger);
            passengerHolder.name = convertView.findViewById(R.id.tvContactName);
            passengerHolder.id = convertView.findViewById(R.id.tvContactIdCard);
            passengerHolder.phone = convertView.findViewById(R.id.tvContactTel);
            convertView.setTag(passengerHolder);
        }else {
            passengerHolder = (ViewHolder) convertView.getTag();
        }
        passengerHolder.name.setText(data.get(position).get("name").toString());
        passengerHolder.id.setText(data.get(position).get("idCard").toString());
        passengerHolder.phone.setText(data.get(position).get("tel").toString());
        data.get(position).put("choose", passengerHolder.checkBox);
        Log.d(TAG, "getView: "+data.get(position).get("name").toString());
        Log.d(TAG, "getView: "+data.get(position).get("idCard").toString());
        Log.d(TAG, "getView: "+data.get(position).get("tel").toString());
        return convertView;
    }

    class ViewHolder{
        public TextView name,id,phone;
        public CheckBox checkBox;
    }
}
