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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class AllPayAdapter extends BaseAdapter {
    private List<TicketNew> data;
    private Context context;
    private static final String TAG = "AllPayAdapter";
    public AllPayAdapter(Context context, List<TicketNew> data){
        this.context = context;
        this.data = data;
    }

    public void setData(List<TicketNew> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        Log.d(TAG, "getCount: "+data.get(position));
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_allpay_adapter, null);
            viewHolder = new ViewHolder();
//            订单序号
            viewHolder.order = convertView.findViewById(R.id.order);
//            订单状态
            viewHolder.payState = convertView.findViewById(R.id.payState);
//            列车号
            viewHolder.train_number = convertView.findViewById(R.id.train_number);
//            日期
            viewHolder.train_date = convertView.findViewById(R.id.train_data);
//            地点
            viewHolder.place_position = convertView.findViewById(R.id.place_position);
//            乘客人数
            viewHolder.people_number = convertView.findViewById(R.id.people_number);
//            订单总金额
            viewHolder.sumMoney = convertView.findViewById(R.id.sumMoney);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Log.d(TAG, "getView: "+data.get(position).getStatus());
        if("1".equals(data.get(position).getStatus())){
            viewHolder.payState.setText("已支付");
        }else if("0".equals(data.get(position).getStatus())){
            viewHolder.payState.setText("未支付");
        }else {
            viewHolder.payState.setText("已取消");
        }
        viewHolder.order.setText("订单编号为：" + data.get(position).getId());
        viewHolder.train_number.setText(data.get(position).getTrain().getTrainNo());
        viewHolder.train_date.setText(data.get(position).getTrain().getStartTrainDate());
        viewHolder.place_position.setText(data.get(position).getTrain().getFromStationName()+"--"+data.get(position).getTrain().getToStationName());
        viewHolder.people_number.setText(data.get(position).getPassengerList().size() + "人");
        viewHolder.sumMoney.setText(data.get(position).getOrderPrice()+"元");

        return convertView;
    }

}

class ViewHolder {
    public TextView order, payState, train_number, train_date, place_position, people_number,
            sumMoney;
}
