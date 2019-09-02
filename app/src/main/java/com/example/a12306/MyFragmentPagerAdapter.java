package com.example.a12306;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.a12306.my.MyFragment;
import com.example.a12306.order.OrderFragment;
import com.example.a12306.ticket.TicketFragment;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 3;
    private TicketFragment ticketFragment;
    private OrderFragment orderFragment;
    private MyFragment myFragment;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        ticketFragment = new TicketFragment();
        orderFragment = new OrderFragment();
        myFragment = new MyFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case MainActivity.PAGE_ONE:
                fragment = ticketFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = orderFragment;
                break;
                case MainActivity.PAGE_THREE:
                    fragment = myFragment;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
