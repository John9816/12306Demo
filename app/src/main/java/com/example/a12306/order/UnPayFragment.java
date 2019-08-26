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
import com.example.a12306.ticket.TicketToBeConfirmed;

public class UnPayFragment extends Fragment {
    private ListView lv_unPay;
    public static AllPayAdapter unPayAdapter;
    private static final String TAG = "UnPaidFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_order_unpay_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lv_unPay = getActivity().findViewById(R.id.lv_unPay);
        unPayAdapter = new AllPayAdapter(getActivity(), TicketToBeConfirmed.unpayTicket);
        lv_unPay.setAdapter(unPayAdapter);


        lv_unPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Intent intent = new Intent(getActivity(), WaitePay.class);
                intent.putExtra("position", position);
                intent.putExtra("orderId", TicketToBeConfirmed.unpayTicket.get(position).get("orderId").toString());
                startActivity(intent);*/
            }
        });

    }
}
