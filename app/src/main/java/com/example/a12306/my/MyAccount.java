package com.example.a12306.my;


import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.a12306.others.CONST.datas;
import static com.example.a12306.others.CONST.passenger;
import static com.example.a12306.others.CONST.titles;

//我的账户
public class MyAccount extends Activity {
    private List<Map<String,Object>> listdata;
    private SimpleAdapter adapter;
    private ListView listView;
    private Map<String,Object> hashMap;
    private AlertDialog alertDialog;
    private int index = 0;//记录被选中的单选项
    private Button btn_save;
    private static final String TAG = "MyAccount";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        /* 显示App icon左侧的back键 */
        ActionBar actionBar = getActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //保存
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SharedPreferences sharedPreferences = getSharedPreferences("info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("type",datas[4]);
                editor.commit();*/
                Toast.makeText(MyAccount.this,"保存成功",Toast.LENGTH_SHORT).show();

            }
        });
        listView = (ListView)findViewById(R.id.lv_myaccount);
        listdata = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            hashMap = new HashMap<>();
            hashMap.put("titles", titles[i]);
            hashMap.put("datas", datas[i]);
            if(i >=4){
                for (int j = 0; j < CONST.images.length; j++) {
                    hashMap.put("images",CONST.images[j]);
                }
            }

            listdata.add(hashMap);
        }

       adapter = new SimpleAdapter(this,listdata,R.layout.list_items,new String[]{"titles","datas","images"},
               new int[]{R.id.tv_common,R.id.tv_common_details,R.id.iv_common});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                switch (position){
                    case 4:
                        //乘客类型
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyAccount.this,R.style.AlertDialogCustom);
                        builder.setTitle("乘客类型");
                        builder.setSingleChoiceItems(passenger, index, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                index = which;
                                if(index == 0){
                                    datas[4] =passenger[0];
                                    listdata.get(4).put("datas",passenger[0]);
                                }if(index == 1){
                                    datas[4] = passenger[1];
                                    listdata.get(4).put("datas",passenger[1]);
                                }

                            }
                        });

                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.create().show();
                        break;
                    case 5:
                        //电话
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MyAccount.this,R.style.AlertDialogCustom);
                        View view1= LayoutInflater.from(MyAccount.this).inflate(R.layout.activity_password_dialog,null,false);
                        final EditText modifyphone = view1.findViewById(R.id.et_common);
                        builder1.setTitle("请输入你的手机号");
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
                                String phone = modifyphone.getText().toString().trim();
                                if(phone.equals("") || phone.equals("13812345678") || phone.length() != 11){
                                    Toast.makeText(MyAccount.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();

                                }else {
                                    datas[5] = phone;
                                    listdata.get(5).put("datas",phone);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                        alertDialog=builder1.create();
                        alertDialog.show();
                        break;
                }

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
        }
        return true;
    }

}
