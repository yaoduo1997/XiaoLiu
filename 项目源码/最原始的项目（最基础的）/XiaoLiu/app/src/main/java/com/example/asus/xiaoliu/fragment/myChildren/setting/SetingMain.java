package com.example.asus.xiaoliu.fragment.myChildren.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.MainActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import cn.bmob.v3.BmobUser;

public class SetingMain extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_set);
        ActivityManager.addActivity(this);

        ImageView ivPre=(ImageView)findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SetingMain.this, HomeActivity.class);
                intent.putExtra("signMark","5");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                startActivity(intent);
            }
        });

        LinearLayout llSafe=(LinearLayout)findViewById(R.id.ll_safe);
        llSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SetingMain.this,SettingSafe.class);
                startActivity(intent);
            }
        });

        LinearLayout llMessage=(LinearLayout)findViewById(R.id.ll_message);
        llMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SetingMain.this,SettingMessage.class);
                startActivity(intent);
            }
        });
        LinearLayout llYinSi=(LinearLayout)findViewById(R.id.ll_yinsi);
        llYinSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SetingMain.this,SettingYinSi.class);
                startActivity(intent);
            }
        });

        Button btnExit=(Button)findViewById(R.id.btn_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SetingMain.this,MainActivity.class);
                ActivityManager.exit();
                BmobUser.logOut();
                startActivity(intent);
            }
        });
    }
}
