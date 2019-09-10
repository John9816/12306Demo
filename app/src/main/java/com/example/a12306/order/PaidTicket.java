package com.example.a12306.order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a12306.R;
import com.example.a12306.order.adapter.TobePayAdapter;
import com.example.a12306.others.CONST;
import com.example.a12306.ticket.TicketToBeConfirmed;
import com.example.a12306.ticket.adapter.TicketToBeConfirmedAdapter;
import com.example.a12306.utils.QRCodeUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.zip.CheckedOutputStream;

/**
 * author : wingel
 * e-mail : 1255542159@qq.com
 * desc   :
 * version: 1.0
 */
public class PaidTicket extends AppCompatActivity {

    private Button btn_check;
    private ListView lv_paid;
    private Intent intent;
    private TextView textView;
    private ImageView  imageView;
    private String OrderTime,trainNo,trainDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid_ticket);
        ImmersionBar.with(this).init();
        textView = (TextView)findViewById(R.id.tv_orderId);
        intent = getIntent();
        textView.setText("您的订单编号为：" + intent.getStringExtra("orderId"));
        trainNo = intent.getStringExtra("trainNO");
        trainDate  = intent.getStringExtra("trainDate");
        OrderTime = intent.getStringExtra("OrderTime");
        lv_paid = findViewById(R.id.lv_paid);
        final TobePayAdapter adapter = new TobePayAdapter(this, CONST.passengerListBeanList,trainNo,trainDate);
        lv_paid.setAdapter(adapter);

        lv_paid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaidTicket.this);
                builder.setItems(new String[]{"改签", "退票"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                            case 1:
                                String orderId = intent.getStringExtra("orderId");
                                String id = CONST.passengerListBeanList.get(position).getId();
                                String idType = CONST.passengerListBeanList.get(position).getIdType();
                                CONST.RefundThread(PaidTicket.this,id,idType,orderId);
                                finish();
                                adapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                builder.create().show();
                return false;
            }
        });


        btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PaidTicket.this,R.style.edit_AlertDialog_style);
                dialog.setContentView(R.layout.check_qrcode);
                imageView =(ImageView) dialog.findViewById(R.id.img);
                QRCodeUtils qrCodeUtils = new QRCodeUtils();
                String content = "订单号:"+intent.getStringExtra("orderId") + "订单时间:" + OrderTime +
                        "车次:" + trainNo + "发车时间:" + trainDate;
                qrCodeUtils.generateQrcodeAndDisplay(content,imageView);
                dialog.setCanceledOnTouchOutside(true);
                Window w = dialog.getWindow();
                WindowManager.LayoutParams lp = w.getAttributes();
                lp.x = 0;
                lp.y = 40;
                dialog.onWindowAttributesChanged(lp);
                imageView.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
dialog.show();
            }

        });
    }
}
