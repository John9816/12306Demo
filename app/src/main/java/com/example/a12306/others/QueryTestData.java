package com.example.a12306.others;

import android.util.Log;

import java.util.ArrayList;

public class QueryTestData {
    private static final String TAG = "QueryTestData";
    //列车车次
    public static final String[] Train_number = new String[]{"G89", "G90", "G95", "G87", "G84"};
    //列车
    public static final String[] Stop_state = new String[]{"终", "过", "过", "终", "终"};
    //发车时间
    public static final String[] Start_time = new String[]{"6:00", "7:35", "7:50", "8:25", "9:40"};
    //靠站时间
    public static final String[] Stop_time = new String[]{"12:35", "11:20", "11:55", "12:20", "12:45"};

    //数据
    public static final String[] ticket1 = new String[]{"高级软卧：42", "硬座：45\n", "一等座：8"};
    public static final String[] ticket2 = new String[]{"特等座：4", "硬座：20\n", "软座：7", "硬卧：19"};
    public static final String[] ticket3 = new String[]{"无座：39", "硬座：38\n", "一等座：17", "软卧：48"};
    public static final String[] ticket4 = new String[]{"商务座：20", "一等座：5\n", "硬卧：50"};
    public static final String[] ticket5 = new String[]{"高级软卧：10", "特等座：18\n", "硬座：33"};

    public static ArrayList<String[]> addData(){
        ArrayList<String[]> tickets = new ArrayList<>();
        tickets.add(ticket1);
        tickets.add(ticket2);
        tickets.add(ticket3);
        tickets.add(ticket4);
        tickets.add(ticket5);
        return tickets;
    }
}
