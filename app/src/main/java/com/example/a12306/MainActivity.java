package com.example.a12306;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

        private RadioButton rb_ticket,rb_order,rb_my;
        private RadioGroup rg;
        private ViewPager viewPager;
        private MyFragmentPagerAdapter mAdapter;
    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        initView();
        rb_ticket.setChecked(true);

    }

    private void initView() {
        rb_ticket = (RadioButton)findViewById(R.id.rb_ticket);
        rb_order = (RadioButton)findViewById(R.id.rb_order);
        rb_my = (RadioButton)findViewById(R.id.rb_my);
        rg = (RadioGroup)findViewById(R.id.rg_tab_bar);
        rg.setOnCheckedChangeListener(this);
        viewPager = (ViewPager)findViewById(R.id.vpager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override//页面切换
    public void onPageScrollStateChanged(int state) {
//state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_ticket.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_order.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_my.setChecked(true);
                    break;
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_ticket:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_order:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_my:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
        }
    }
}
