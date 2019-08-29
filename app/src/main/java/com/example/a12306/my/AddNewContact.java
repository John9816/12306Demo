package com.example.a12306.my;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.a12306.R;
import com.example.a12306.others.CONST;
import com.gyf.immersionbar.ImmersionBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a12306.others.CONST.datas;
import static com.example.a12306.others.CONST.passenger;
import static com.example.a12306.ticket.TicketToBeConfirmed.data;

//添加新的联系人
public class AddNewContact extends Activity {
    private ListView addnewpassenger;
    private SimpleAdapter adapter;
    private Button add;
    private AlertDialog alertDialog;
    private int index = 0;//记录被选中的单选项
    private static final String TAG = "AddNewContact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_contact);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        add = (Button)findViewById(R.id.add);
        addnewpassenger = findViewById(R.id.addnewpassenger);
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        CONST.passenger_informationt = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();

        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map1);

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key1","证件类型");
        CONST.passenger_informationt.add(map2);

        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("key1","证件号码");
        CONST.passenger_informationt.add(map3);

        final HashMap<String, Object> map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map4);

        final HashMap<String, Object> map5 = new HashMap<>();
        map5.put("key1","电话");
        map5.put("key3",R.drawable.forward_25);
        CONST.passenger_informationt.add(map5);

        adapter = new SimpleAdapter(this,
                CONST.passenger_informationt,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        addnewpassenger.setAdapter(adapter);


        addnewpassenger.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        View view1= LayoutInflater.from(AddNewContact.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifyphone = view1.findViewById(R.id.et_common);
                        builder1.setTitle("请输入姓名");
                        builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        builder1.setView(view1);
                        builder1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = modifyphone.getText().toString().trim();
                                CONST.passenger_informationt.get(position).put("key2",name);
                                Log.d(TAG, "onClick: "+CONST.passenger_informationt.get(position).toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        alertDialog=builder1.create();
                        alertDialog.show();
                        break;

                    case 3://乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //默认选中第一个
                                index = which;
                                if (which == 0) {
                                    //如果选择0--成人
                                    CONST.passenger_informationt.get(position).put("key2",passenger[0]);
                                    Log.d(TAG, "成人: "+CONST.passenger_informationt.get(position).toString());
                                }
                                if (which == 1) {
                                    //如果选择1--学生
                                    CONST.passenger_informationt.get(position).put("key2",passenger[1]);
                                    Log.d(TAG, "学生: "+CONST.passenger_informationt.get(position).toString());
                                }
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("row", (Serializable) CONST.passenger_informationt.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.create().show();
                        break;
                    case 4:
                        builder = new AlertDialog.Builder(AddNewContact.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(AddNewContact.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifynm=view.findViewById(R.id.et_common);
                        builder.setTitle("请输入你的电话");
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
                                String inputtel = modifynm .getText().toString().trim();
                                CONST.passenger_informationt.get(position).put("key2",inputtel);
                                Log.d(TAG, "电话: "+CONST.passenger_informationt.get(position).toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();

                }
            }
        });


        //保存数据
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取姓名
                String name =  CONST.passenger_informationt.get(0).get("key2").toString();
                //获取性别
                String type = CONST.passenger_informationt.get(3).get("key2").toString();
                //获取电话号码
                String telephone = CONST.passenger_informationt.get(4).get("key2").toString();
                String append = name+"("+type+")";
                Log.d(TAG, "append: "+append);
                String append1 = "电话:" + telephone;
                Log.d(TAG, "append1: "+append1);

                Intent intent = new Intent(AddNewContact.this,MyContact.class);
                intent.putExtra("tel",append1);
                intent.putExtra("name",append);
                intent.putExtra("idCard","身份证:123456");
                startActivity(intent);
                finish();
            }
        });

    }

    //ActionBar重写的方法
    @Override
    public boolean onOptionsItemSelected(   MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
