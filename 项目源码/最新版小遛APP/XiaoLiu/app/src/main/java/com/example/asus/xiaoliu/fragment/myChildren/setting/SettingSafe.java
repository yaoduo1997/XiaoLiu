package com.example.asus.xiaoliu.fragment.myChildren.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class SettingSafe extends AppCompatActivity {
    private ImageView ivPre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_safe);
        ActivityManager.addActivity(this);

        ivPre=(ImageView)findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SettingSafe.this,SetingMain.class);
                startActivity(intent);
                finish();
            }
        });
        LinearLayout llPhone=(LinearLayout)findViewById(R.id.ll_changephone);
        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SettingSafe.this,SettingNewPhone.class);
                startActivity(intent);
            }
        });
        LinearLayout llPwd=(LinearLayout)findViewById(R.id.ll_changepassword);
        llPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SettingSafe.this,SettingChangePwd.class);
                startActivity(intent);
            }
        });
//        LinearLayout llThird=(LinearLayout)findViewById(R.id.ll_third);
//        llThird.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(SettingSafe.this,SettingThird.class);
//                startActivity(intent);
//            }
//        });
    }
}
