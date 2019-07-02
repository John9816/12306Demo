package com.example.a12306.my;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a12306.LoginActivity;
import com.example.a12306.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//用户管理界面
public class MyFragment extends AppCompatActivity {
    private ListView listView;
    private Button btn_esc;
    private String[] datas ={ "我的联系人", "我的账户", "我的密码" };
    private int Images[] = {R.drawable.mycontact,R.drawable.mycontact,R.drawable.mycontact};
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private View view;
    private EditText pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fragment);
        initView();//初始化
        }

    private void initView() {
        btn_esc = (Button)findViewById(R.id.btn_esc);
        btn_esc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MyFragment.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        List<Map<String, Object>> listitem = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < Images.length; i++) {
            Map<String,Object> hashMap = new HashMap<String, Object>();
            hashMap.put("data",datas[i]);
            hashMap.put("Images", Images[i]);
            listitem.add(hashMap);
            SimpleAdapter adapter = new SimpleAdapter(MyFragment.this,listitem,R.layout.activity_my_fragment_items,
                    new String[]{"data", "Images"}, new int[]{R.id.tv_items, R.id.iv_items});
            listView = (ListView) findViewById(R.id.myuser);
            listView.setAdapter(adapter);
    }
        //listview事件监听
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MyFragment.this,MyContact.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MyFragment.this,MyAccount.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        builder=new AlertDialog.Builder(MyFragment.this);
                        view= LayoutInflater.from(MyFragment.this).inflate(R.layout.activity_password_dialog,null,false);
                        pa=view.findViewById(R.id.password);
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
                                SharedPreferences sp=getSharedPreferences("info",MODE_PRIVATE);
                                String inputpasswors = pa.getText().toString().trim();
                                String matepasswor=sp.getString("password","");
                                if (inputpasswors.equals(matepasswor)){
                                    alertDialog.dismiss();
                                    Intent intent2 = new Intent(MyFragment.this,MyPassword.class);
                                    startActivity(intent2);
                                }else {
                                    Toast.makeText(MyFragment.this,"密码错误",Toast.LENGTH_LONG).show();
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

