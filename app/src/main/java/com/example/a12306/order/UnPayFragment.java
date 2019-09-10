package com.example.a12306.order;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.a12306.order.adapter.UnPayAdapter;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class UnPayFragment extends Fragment {
    private ListView lv_unPay;
    public static UnPayAdapter unPayAdapter;
    private static final String TAG = "UnPaidFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order_unpay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImmersionBar.with(this).init();
        lv_unPay = getActivity().findViewById(R.id.lv_unPay);
       unPayAdapter = new UnPayAdapter(getActivity(),new ArrayList<TicketNew>());
        lv_unPay.setAdapter(unPayAdapter);

        unPayAdapter.notifyDataSetChanged();
        lv_unPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getActivity(), ToBePay.class);
                intent.putExtra("orderId", CONST.unPayNewList.get(position).getId());
                intent.putExtra("trainNO",CONST.unPayNewList.get(position).getTrain().getTrainNo());
                intent.putExtra("trainDate",CONST.unPayNewList.get(position).getTrain().getStartTrainDate());
                Log.d(TAG, "onItemClick: "+CONST.unPayNewList.get(position).getPassengerList().size());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        CONST.UnPayThread(getActivity(),unPayAdapter);
    }
}
