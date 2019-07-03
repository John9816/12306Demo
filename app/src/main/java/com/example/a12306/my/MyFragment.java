package com.example.a12306.my;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;
import com.example.a12306.others.CONST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

//用户管理界面
public class MyFragment extends Fragment {
    private View view;
    private EditText modifypw;
    private ListView listView;
    private Button btn_esc;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

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
                SharedPreferences sp = getActivity().getSharedPreferences("info", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("ISCHECKED",false);
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Images.length; i++) {
            Map<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put("data", CONST.mydatas[i]);
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
                        //我的联系人
                        Intent intent = new Intent(getActivity(),MyContact.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //我的账户
                        Intent intent1 = new Intent(getActivity(),MyAccount.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        //我的密码
                        builder=new AlertDialog.Builder(getActivity());
                        view= LayoutInflater.from(getActivity()).inflate(R.layout.activity_password_dialog,null,false);
                        modifypw=view.findViewById(R.id.et_common);
                        builder.setTitle("请输入你的密码");
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder.setView(view);
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sp=getActivity().getSharedPreferences("info",MODE_PRIVATE);
                                String inputpasswors = modifypw.getText().toString().trim();
                                String matepasswor=sp.getString("password","");
                                if (inputpasswors.equals(matepasswor)){
                                    alertDialog.dismiss();
                                    Intent intent2 = new Intent(getActivity(),MyPassword.class);
                                    startActivity(intent2);
                                }else {
                                    Toast.makeText(getActivity(),"密码错误", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();
                        break;
                    default:
                        break;

                }
            }
        });

}
}

