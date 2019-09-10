package com.example.a12306.ticket.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * date   : 2019/9/512:20
 * desc   :
 * version: 1.0
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //改变测量模式

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //需要改变的只是高度的测量模式
        int heightMes = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMes);
    }
}

