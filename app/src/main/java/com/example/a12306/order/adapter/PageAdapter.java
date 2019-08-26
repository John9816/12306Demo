package com.example.a12306.order.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.a12306.order.AllPayFragment;
import com.example.a12306.order.OrderFragment;
import com.example.a12306.order.UnPayFragment;

import java.util.List;

//订单页面适配器
public class PageAdapter extends FragmentPagerAdapter {
    private UnPayFragment unPayFragment;
    private AllPayFragment allPayFragment;
    private Fragment fragment;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        unPayFragment = new UnPayFragment();
        allPayFragment = new AllPayFragment();
    }

    @Override
    public Fragment getItem(int i) {
        fragment = new Fragment();
        switch (i) {
            case OrderFragment.UNPAY:
                fragment = unPayFragment;
                break;
            case  OrderFragment.ALLPAY:
                fragment = allPayFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
