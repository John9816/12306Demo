package com.example.a12306.my;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//用户管理界面
public class MyFragment extends Fragment {
    private View view;
    private ListView listView;
    private Button btn_esc;
    private String[] datas ={ "我的联系人", "我的账户", "我的密码" };
    private int Images[] = {R.drawable.mycontact,R.drawable.mycontact,R.drawable.mycontact};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_fragment, container, false);
        initView();//初始化
        return view;
    }

    private void initView() {
        btn_esc = (Button)view.findViewById(R.id.btn_esc);
        btn_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit(); editor.clear();
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Images.length; i++) {
            Map<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put("data",datas[i]);
            hashMap.put("Images", Images[i]);
            listitem.add(hashMap);
            SimpleAdapter adapter = new SimpleAdapter(getActivity(),listitem,R.layout.activity_my_fragment_items,
                    new String[]{"data", "Images"}, new int[]{R.id.tv_items, R.id.iv_items});
            listView = (ListView)view.findViewById(R.id.myuser);
            listView.setAdapter(adapter);
    }
        //listview事件监听
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getActivity(),MyContact.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(),MyAccount.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(getActivity(),MyPassword.class);
                        startActivity(intent2);
                        break;
                        default:
                            break;
                }
            }
        });

}
}

