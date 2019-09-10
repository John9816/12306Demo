package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a12306.R;

import java.util.List;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/511:37
 * desc   :
 * version: 1.0
 */
public class GridViewAdapter extends BaseAdapter {

    private List<String> mDataList;
    private Context context;
    private ViewHolder viewHolder;

    public GridViewAdapter(List<String> mDataList,Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridviewtext,null);
            viewHolder.textView = convertView.findViewById(R.id.gridviewText);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mDataList.get(position));
        return convertView;
    }
    class ViewHolder{
        private TextView textView;
    }
}
