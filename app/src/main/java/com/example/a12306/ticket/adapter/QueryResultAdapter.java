package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.bean.TicketBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//查询结果适配器
public class QueryResultAdapter extends BaseAdapter {

    private List<TicketBean> test_data;
    private Context context;
    private ResultHolder resultHolder;

    private static final String TAG = "QueryResultAdapter";

    public QueryResultAdapter(Context context, List<TicketBean> test_data){
        this.context = context;
        this.test_data = test_data;

    }

    public void setList(List<TicketBean> dataList){

        this.test_data=dataList;
        notifyDataSetChanged();
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
            resultHolder.startTime = convertView.findViewById(R.id.startTime);
            resultHolder.stopTime = convertView.findViewById(R.id.stopTime);
            resultHolder.gridview = convertView.findViewById(R.id.gridview);
            convertView.setTag(resultHolder);
        } else {
            resultHolder = (ResultHolder) convertView.getTag();
        }
        resultHolder.trainNumber.setText(test_data.get(position).getTrainNo());
        resultHolder.startTime.setText(test_data.get(position).getStartTime());
        resultHolder.stopTime.setText(test_data.get(position).getArriveTime());

        List<TicketBean.SeatsBean> data=test_data.get(position).getSeats();
        List<String> list=new ArrayList<>();
        for (TicketBean.SeatsBean datum : data) {
            String content= datum.getSeatName()+" : "+datum.getSeatNum();
            list.add(content);
        }
        if(list.size()>0){
            GridViewAdapter gridViewAdapter=new GridViewAdapter(list,context);
            gridViewAdapter.notifyDataSetChanged();
            resultHolder.gridview.setAdapter(gridViewAdapter);
        }
        return convertView;
    }
}

class ResultHolder{
    public TextView trainNumber, startTime, stopTime;
    public MyGridView gridview;
}
