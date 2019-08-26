package com.example.a12306.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a12306.R;
import com.example.a12306.order.adapter.AllPayAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class AllPayFragment extends Fragment {
    private ListView lv_allpay;
    public static ArrayList<HashMap<String, Object>> allPaidTicket = new ArrayList<>();
    public static AllPayAdapter allPayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_allpay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_allpay = getActivity().findViewById(R.id.lv_allpay);
        allPayAdapter = new AllPayAdapter(getActivity(), allPaidTicket);
        lv_allpay.setAdapter(allPayAdapter);

       lv_allpay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (allPaidTicket.get(position).get("payState").equals("已支付")) {
                    /*Intent intent = new Intent(getActivity(), Paidstate.class);
                    intent.putExtra("position", position);
                    startActivity(intent);*/
                }
            }
        });
    }
}
