package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class FeeScale extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fee_scale);
        ActivityManager.addActivity(this);

        final Intent intent=getIntent();
        final String selectDog=intent.getStringExtra("selectDog");
        final String startTime=intent.getStringExtra("startTime");
        final String selectDuration=intent.getStringExtra("duration");
        final String selectAddress = intent.getStringExtra("selectAddress");

        ImageView imgReturn =(ImageView)findViewById(R.id.iv_cancel);
        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("startTime",startTime);
                intent1.putExtra("duration",selectDuration);
                intent1.putExtra("selectAddress",selectAddress);
                intent1.setClass(FeeScale.this,ShouOF.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
