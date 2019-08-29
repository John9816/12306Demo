package com.example.a12306.my;


import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
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
import com.example.a12306.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.a12306.others.CONST.passenger;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */

public class MyContactEdit extends Activity {

    private ListView lvMyContactEdit;
    private List<Map<String,Object>> data;
    private SimpleAdapter adapter;
    private Button btn_contactsave;//保存按钮
    private AlertDialog alertDialog;//对话框
    private AlertDialog.Builder builder;
    private EditText modifynm;//修改后的名字
    private int index = 0;//记录被选中的单选项
    private String inputname,inputtel;
    private  Map<String,Object> map1,map2,map3,map4,map5;
    private static final String TAG = "MyContactEdit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_edit);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_contactsave = (Button)findViewById(R.id.btn_contactsave);//保存
        lvMyContactEdit = findViewById(R.id.lv_mycontactedit);
        initData();



        lvMyContactEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 0:
                        builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(MyContactEdit.this).inflate(R.layout.activity_password_dialog,null,false);
                        modifynm=view.findViewById(R.id.et_common);
                        builder.setTitle("请输入你的姓名");
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
                                inputname = modifynm .getText().toString().trim();
                               // String matepasswor=sp.getString("password","");
                                data.get(position).put("key2",inputname);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();
                        break;
                    case 3:
                        //乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //默认选中第一个
                                index = which;
                                if (which == 0) {
                                    //如果选择0--成人
                                    Log.d(TAG, "position: "+position);
                                    Log.d(TAG, "which: "+which);
                                    map4.put("key2",passenger[0]);
                                }
                                if (which == 1) {
                                    //如果选择1--学生
                                    Log.d(TAG, "position1: "+position);
                                    Log.d(TAG, "which1: "+which);
                                    map4.put("key2",passenger[1]);
                                }

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.putExtra("row", (Serializable) data.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });

                        builder.create().show();
                        break;
                    case 4:
                        builder = new AlertDialog.Builder(MyContactEdit.this,R.style.AlertDialogCustom);
                        view= LayoutInflater.from(MyContactEdit.this).inflate(R.layout.activity_password_dialog,null,false);
                        modifynm=view.findViewById(R.id.et_common);
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
                                inputtel = modifynm .getText().toString().trim();
                                // String matepasswor=sp.getString("password","");
                                map5.put("key2",inputtel);
                                adapter.notifyDataSetChanged();

                            }
                        });
                        alertDialog=builder.create();
                        alertDialog.show();
                        break;
                }
            }
        });

        //保存数据并且传递
        btn_contactsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取姓名
                String name =  map1.get("key2").toString();
                Log.d(TAG, "onClick: "+name);
                //获取性别
                String type = map4.get("key2").toString();
                //获取电话号码
                String telephone = map5.get("key2").toString();
                String append = name+"("+type+")";
                //存入本地
                SharedPreferences sharedPreferences = getSharedPreferences("mycontact",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("append",append);
                editor.putString("tel",telephone);
                editor.commit();

                Intent intent = new Intent(MyContactEdit.this,MyContact.class);
                intent.putExtra("telephone",telephone);
                intent.putExtra("name",name);
                intent.putExtra("type",type);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    //数据初始化
    private void initData() {
        Map<String,Object> contact = (HashMap<String, Object>) getIntent().getSerializableExtra("row");
        data = new ArrayList<>();
        map1 = new HashMap<>();
        String name = (String) contact.get("name");
        map1.put("key1","姓名");
        //split拆分法，以括号拆分
        map1.put("key2",name.split("\\(")[0]);
        map1.put("key3",R.drawable.forward_25);
        data.add(map1);

        map2 = new HashMap<>();
        String idCard = (String) contact.get("idCard");
        map2.put("key1","证件类型");
        map2.put("key2",idCard.split("\\:")[0]);
        data.add(map2);

        map3 = new HashMap<>();
        map3.put("key1","证件号码");
        map3.put("key2",idCard.split("\\:")[1]);
        data.add(map3);

        map4 = new HashMap<>();
        map4.put("key1","乘客类型");
        map4.put("key2",name.split("\\(")[1].replace(")",""));
        map4.put("key3",R.drawable.forward_25);
        data.add(map4);

        map5 = new HashMap<>();
        String tel = (String) contact.get("tel");
        map5.put("key1","电话");
        map5.put("key2",tel.split("\\:")[1]);
        map5.put("key3",R.drawable.forward_25);
        data.add(map5);

        adapter = new SimpleAdapter(this,
                data,
                R.layout.list_item_my_contact_edit_layout,
                new String[]{"key1","key2","key3"},
                new int[]{R.id.tv_MyContact_edit_key,R.id.tv_MyContact_edit_value,R.id.img_MyContact_edit_flag});
        lvMyContactEdit.setAdapter(adapter);
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

