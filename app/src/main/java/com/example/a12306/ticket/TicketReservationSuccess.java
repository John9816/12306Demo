package com.example.a12306.ticket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.a12306.R;
import com.example.a12306.order.AllPayFragment;
import com.example.a12306.others.CONST;

public class TicketReservationSuccess extends AppCompatActivity {
    private Toolbar toolbar;
    private Button back;
    private int code;
    private static final String TAG = "TicketReservationSuccess";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_reservation_success);
            toolbar = CONST.usrToolbar(R.id.successhead, "车票预定5/5", this, 0);
            final Intent intent = getIntent();
            code = intent.getIntExtra("code", 1);
            if (code == 1) {
                toolbar = CONST.usrToolbar(R.id.successhead, "车票支付页面", this, 0);
            }
        back = findViewById(R.id.end);
        back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TicketReservation.ticketReservation.finish();
                    TicketReservationDetail.ticketReservationDetail.finish();
                    AddPassenger.addPassenger.finish();
                    TicketToBeConfirmed.ticketToBeConfirmed.finish();
                    finish();
                    TicketToBeConfirmed.allPayTicket = TicketToBeConfirmed.data;
                    TicketToBeConfirmed.allPayTicket.get(0).put("payState", "已支付");
                    AllPayFragment.allPayAdapter.notifyDataSetChanged();
                    //Intent intent1 = new Intent(TicketReservationSuccess.this, Paidstate.class);
                    //startActivity(intent1);
                }
            });
        }
    }

