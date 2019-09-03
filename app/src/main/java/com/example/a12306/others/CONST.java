package com.example.a12306.others;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.a12306.R;
import com.example.a12306.my.MyFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

import static android.content.Context.MODE_PRIVATE;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public final class CONST {

   /* public static final String[] titles = {"用户名","姓名","证件类型","证件号码","乘客类型","电话"};
    public static final String[] datas = {"dong","冬不拉","身份证","11010119910511947X","成人","13812345678"};*/
    public static final String[] passenger = {"成人","学生"};
    public static final String[] idType = {"身份证","军人证","港澳通行证"};
    public static final String[] mydatas ={ "我的联系人", "我的账户", "我的密码" };
    public static final int[]  images= {R.drawable.forward_25,R.drawable.forward_25};
    public static final String[] cities = {"北京","大连" ,"上海", "天津", "重庆", "哈尔滨",  "长春", "沈阳"
            ,"呼和浩特", "石家庄", "乌鲁木齐", "兰州", "西宁", "西安", "银川", "郑州", "济南", "太原", "合肥", "武汉", "长沙"
            , "南京", "成都", "贵阳", "昆明", "南宁", "拉萨", "杭州", "南宁", "广州", "福州", "台北", "海口","香港", "澳门"};
    public static ArrayList<String>  query_history=
            new ArrayList<String>();
    //乘客信息
    public static ArrayList<Map<String, Object>> passenger_info = new ArrayList<Map<String, Object>>();
    //个人信息
    public static List<Map<String, Object>> personal_info= new ArrayList<Map<String, Object>>();
    public static HashMap<String, Object> passenger_content = new HashMap<String, Object>();


    //    静态方法
    public static Toolbar usrToolbar(int id, String title, final Activity activity, int Menuid){
        Toolbar toolbar = activity.findViewById(id);
        toolbar.setTitle(title);
        if (Menuid != 0){
            toolbar.inflateMenu(Menuid);
        }
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return toolbar;
    }


    public static  String getCookie(Context context){
        SharedPreferences preferences = context.getSharedPreferences("userinfo", MODE_PRIVATE);
        String value = preferences.getString("cookie", "");
        return value;

    }
}
