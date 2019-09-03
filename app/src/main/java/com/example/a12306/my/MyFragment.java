package com.example.a12306.my;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.example.a12306.utils.CONSTANT;
import com.example.a12306.utils.Md5Utils;
import com.example.a12306.utils.NetUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;
/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
//用户管理界面
public class MyFragment extends Fragment {
    private View view;
    private EditText modifypw;
    private ListView listView;
    private Button btn_esc;
    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog = null;
    private static final String TAG = "MyFragment";
    private  OkHttpClient client = new OkHttpClient();
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
                //1、检查网络连接是否正常
                if (!NetUtils.check(getActivity())) {
                    Toast.makeText(getActivity(), "网络异常，请检查！", Toast.LENGTH_LONG).show();
                    return;//后续代码不执行
                }
                //执行异步任务，退出登录
                new LogoutTask().execute();

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
                        builder.setTitle("请输入原密码");
                        MyFragment.this.builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                alertDialog.dismiss();
                            }
                        });
                        MyFragment.this.builder.setView(view);
                        MyFragment.this.builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String inputpasswors = modifypw.getText().toString().trim();
                                QueryPassword(inputpasswors);

                            }
                        });
                        alertDialog= MyFragment.this.builder.create();
                        alertDialog.show();
                        break;
                    default:
                        break;

                }
            }


       });

}

    private void QueryPassword(final String oldPassword) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    RequestBody requestBody = new FormBody.Builder()
                            .add("oldPassword",oldPassword)
                            .add("action","query")
                            .build();

                    Request request = new Request.Builder()
                            .addHeader("Cookie",CONST.getCookie(getActivity()))
                            .url(CONSTANT.HOST + "/otn/AccountPassword")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    Log.d(TAG, "response: "+response);
                    String responseData = response.body().string();
                    Log.d(TAG, "responseData: "+responseData);

                                if ("\"1\"".equals(responseData)){
                                    alertDialog.dismiss();
                                    Intent intent2 = new Intent(getActivity(),MyPassword.class);
                                    startActivity(intent2);
                                }else {
                                    Toast.makeText(getActivity(),"原密码错误请重新输入",Toast.LENGTH_SHORT).show();
                                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //执行异步任务退出登陆
   class LogoutTask extends AsyncTask<String,String,String> {

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(getActivity(),null,"正在加载中......",false,true);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String result = null;
        try {

            Request request = new Request.Builder()
                    .addHeader("Cookie", CONST.getCookie(getActivity()))
                    .url(CONSTANT.HOST + "/otn/Logout")
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.d(TAG, "获取的服务器数据： " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        if (progressDialog != null)
            progressDialog.dismiss();

        if ("\"1\"".equals(result)) {
            SharedPreferences preferences = getActivity().getSharedPreferences("userinfo",MODE_PRIVATE);
            Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT)
                    .show();
            preferences.edit().clear().commit();
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else if ("\"0\"".equals(result)) {
            Toast.makeText(getActivity(), "退出登录失败", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getActivity(), "服务器错误，请重试", Toast.LENGTH_SHORT)
                    .show();
            Intent intent = new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }
}


}

