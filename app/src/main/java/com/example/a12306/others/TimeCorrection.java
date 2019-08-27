package com.example.a12306.others;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

//时间校正
public class TimeCorrection {
    private int year,month,day;
    private Context context;
    private TextView textView;
    private Calendar currentCalendar,calendar;
    private final String[] sumweek = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public TimeCorrection(Context context, TextView textView, Calendar currentCalendar, int year, int month, int day){
        this.context = context;
        this.textView = textView;
        this.year = year;
        this.month = month;
        this.day = day;
        this.currentCalendar = currentCalendar;
        calendar = Calendar.getInstance();
    }
    //上一天
    public void lastDay() {
        day--;
        if(day <currentCalendar.get(Calendar.DAY_OF_MONTH)&& month == (currentCalendar.get(Calendar.MONTH)+1)) {
            day++;
            Toast.makeText(context, "日期无效，不能为当前日期的前一天", Toast.LENGTH_SHORT).show();
        } else {
            if (day < calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
                month--;
                modifyCalendar(year, month);
                day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            textView.setText(year + "-" + month + "-" + day + "（" + getCurrentWeek(year,
                    month, day) + "）");
        }
    }

    //下一天
    public void nextDay(){
        day++;
        if (day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            month++;
            modifyCalendar(year, month);
            day = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        }
        textView.setText(year + "-" + month + "-" + day + "（" + getCurrentWeek(year,
                month, day) + "）");
    }

    //获取当前周
    public String getCurrentWeek(int year,int month,int day){
        Date date = new Date(year - 1990,month-1,day);
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 7) {
            return sumweek[0];
        }
        return sumweek[week];
    }

    //获取年
    public int getYear() {
        return year;
    }

    //获取月
    public int getMonth() {
        return month;
    }

    //获取日
    public int getDay() {
        return day;
    }

    //    修改Calender
    protected void modifyCalendar(int year, int month) {
//        设置年份
        calendar.set(Calendar.YEAR, year);
//        设置月份
        calendar.set(Calendar.MONTH, month-1);
    }


}