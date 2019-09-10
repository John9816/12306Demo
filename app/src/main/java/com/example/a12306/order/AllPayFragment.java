package com.example.a12306.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a12306.R;
import com.example.a12306.bean.TicketNew;
import com.example.a12306.order.adapter.AllPayAdapter;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.example.a12306.utils.CONSTANT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class AllPayFragment extends Fragment {
    private ListView lv_allpay;
    //public static ArrayList<HashMap<String, Object>> allPaidTicket = new ArrayList<>();
    public static AllPayAdapter allPayAdapter;
    public static String[] status;
    private static final String TAG = "AllPayFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_allpay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_allpay = getActivity().findViewById(R.id.lv_allpay);
        allPayAdapter = new AllPayAdapter(getActivity(), new ArrayList<TicketNew>());
        lv_allpay.setAdapter(allPayAdapter);

       lv_allpay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("1".equals(CONST.ticketNewList.get(position).getStatus())) {
                    Intent intent = new Intent(getActivity(), PaidTicket.class);
                    intent.putExtra("orderId", CONST.ticketNewList.get(position).getId());
                    intent.putExtra("trainNO",CONST.ticketNewList.get(position).getTrain().getTrainNo());
                    intent.putExtra("trainDate",CONST.ticketNewList.get(position).getTrain().getStartTrainDate());
                    intent.putExtra("OrderTime",CONST.ticketNewList.get(position).getOrderTime());
                    startActivity(intent);
                }if ("0".equals(CONST.ticketNewList.get(position).getStatus())){
                    Intent intent = new Intent(getActivity(), ToBePay.class);
                    intent.putExtra("orderId", CONST.unPayNewList.get(position).getId());
                    intent.putExtra("trainNO",CONST.unPayNewList.get(position).getTrain().getTrainNo());
                    intent.putExtra("trainDate",CONST.unPayNewList.get(position).getTrain().getStartTrainDate());
                    startActivity(intent);
                }

              
            }
        });
    
    }

    @Override
    public void onResume() {
        super.onResume();
        CONST.OrderThread(getActivity(),allPayAdapter);
    }
}
