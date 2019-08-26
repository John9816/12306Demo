package com.example.a12306.order;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.a12306.R;
import com.example.a12306.order.adapter.PageAdapter;

import java.util.ArrayList;
import java.util.List;

//订单
public class OrderFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    public static final int UNPAY = 0;
    public static final int ALLPAY= 1;
    private RadioGroup radioGroup;
    private RadioButton unpay, allpay;
    private ViewPager paid_pager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        radioGroup = getActivity().findViewById(R.id.paid_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);
        unpay = getActivity().findViewById(R.id.unPaid);
        allpay = getActivity().findViewById(R.id.allPaid);
        paid_pager = getActivity().findViewById(R.id.viewPager);
        PageAdapter paidPager = new PageAdapter(getActivity().getSupportFragmentManager());
        paid_pager.setAdapter(paidPager);
        paid_pager.setCurrentItem(1);
        allpay.setChecked(true);
        paid_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case UNPAY:
                        unpay.setChecked(true);
                        break;
                    case  ALLPAY:
                        allpay.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case  R.id.unPaid:
                paid_pager.setCurrentItem(UNPAY);
                break;
            case R.id.allPaid:
                paid_pager.setCurrentItem(ALLPAY);
                break;
        }
    }




}