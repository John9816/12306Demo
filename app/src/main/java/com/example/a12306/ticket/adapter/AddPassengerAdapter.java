package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12306.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AddPassengerAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context context;
    private static final String TAG = "AddPassengerAdapter";
    public AddPassengerAdapter(Context context,ArrayList<HashMap<String, Object>> data){
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_my_contact_list_layout, null);
            viewHolder.name = convertView.findViewById(R.id.tvContactName);
            viewHolder.idCard = convertView.findViewById(R.id.tvContactIdCard);
            viewHolder.tel = convertView.findViewById(R.id.tvContactTel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(data.get(position).get("name").toString());
        viewHolder.idCard.setText(data.get(position).get("idCard").toString());
        viewHolder.tel.setText(data.get(position).get("tel").toString());
        Log.d(TAG, "getView: "+data.get(position).get("name").toString());
        return convertView;

    }
}
class ViewHolder{
    public TextView name, idCard, tel;
}