package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.others.QueryTestData;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//查询结果适配器
public class QueryResultAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> test_data;
    private Context context;
    private ResultHolder resultHolder;
    private ArrayList<String[]> tickets;
    private static final String TAG = "QueryResultAdapter";

    public QueryResultAdapter(Context context, ArrayList<HashMap<String, Object>> test_data){
        this.context = context;
        this.test_data = test_data;
        tickets = QueryTestData.addData();
    }

    @Override
    public int getCount() {
        return test_data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            resultHolder = new ResultHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_query_result, null);
            resultHolder.trainNumber = convertView.findViewById(R.id.trainNumber);
            resultHolder.startState = convertView.findViewById(R.id.startState);
            resultHolder.stopState = convertView.findViewById(R.id.stopState);
            resultHolder.startTime = convertView.findViewById(R.id.startTime);
            resultHolder.stopTime = convertView.findViewById(R.id.stopTime);
            resultHolder.gridLayout = convertView.findViewById(R.id.gridlayout);
            convertView.setTag(resultHolder);
        } else {
            resultHolder = (ResultHolder) convertView.getTag();
        }
        resultHolder.trainNumber.setText(test_data.get(position).get("Train_number").toString());
        resultHolder.startState.setText(test_data.get(position).get("Start_state").toString());
        resultHolder.stopState.setText(test_data.get(position).get("Stop_state").toString());
        resultHolder.startTime.setText(test_data.get(position).get("Start_time").toString());
        resultHolder.stopTime.setText(test_data.get(position).get("Stop_time").toString());
        StringBuffer string = new StringBuffer();
        for (int i = 0; i <tickets.get(position).length ; i++) {

            string.append(tickets.get(position)[i]);
        }
        resultHolder.gridLayout.setText(string.toString());


        return convertView;
    }
}

class ResultHolder{
    public TextView trainNumber, startState, stopState, startTime, stopTime;
    public TextView gridLayout;
}
