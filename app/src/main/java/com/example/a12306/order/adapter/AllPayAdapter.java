package com.example.a12306.order.adapter;

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
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class AllPayAdapter extends BaseAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context context;
    public AllPayAdapter(Context context, ArrayList<HashMap<String, Object>> data){
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
        viewHolder.order.setText("订单编号为：" + data.get(position).get("orderId").toString());
        viewHolder.payState.setText(data.get(position).get("payState").toString());
        viewHolder.train_number.setText(data.get(position).get("trainNumber").toString());
        viewHolder.train_date.setText(data.get(position).get("date").toString());
        viewHolder.place_position.setText(data.get(position).get("place").toString());
        viewHolder.people_number.setText(data.get(position).get("sumPeople").toString() + "人");
        viewHolder.sumMoney.setText(data.get(position).get("sumPrice").toString()+"元");

        return convertView;
    }

}

class ViewHolder {
    public TextView order, payState, train_number, train_date, place_position, people_number,
            sumMoney;
}
