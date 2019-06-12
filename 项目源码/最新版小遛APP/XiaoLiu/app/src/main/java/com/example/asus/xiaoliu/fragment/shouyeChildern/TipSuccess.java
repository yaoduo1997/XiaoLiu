package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.util.Calendar;


public class TipSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip_success);
        ActivityManager.addActivity(this);

        Intent intent=getIntent();
        String orderId=intent.getStringExtra("orderId");

        TextView textView1= (TextView) findViewById(R.id.tv_order_id);
        textView1.setText(orderId);

        Calendar calendar=Calendar.getInstance();
        String  mMonth=String.valueOf(calendar.get(Calendar.MONTH)+1);
        String  mDay=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        String  mYear=String.valueOf(calendar.get(Calendar.YEAR));
        String mHour=String.valueOf(calendar.get(Calendar.HOUR));
        String mMinute=String.valueOf(calendar.get(Calendar.MINUTE));
        if(Integer.parseInt(mHour)<10){
            mHour="0"+mHour;
        }
        if(Integer.parseInt(mMinute)<10){
            mMinute="0"+mMinute;
        }
        TextView textView=(TextView)findViewById(R.id.tv_order_time);
        textView.setText(mYear+"年"+mMonth+"月"+mDay+"日"+mHour+":"+mMinute);

        ImageView imageView=(ImageView) findViewById(R.id.iv_return);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(TipSuccess.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
