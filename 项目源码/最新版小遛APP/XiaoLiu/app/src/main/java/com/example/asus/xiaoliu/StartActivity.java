package com.example.asus.xiaoliu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.jpush.android.api.JPushInterface;

public class StartActivity extends AppCompatActivity {
    Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "7770110c3abc9cfeefc98f12480a6f16");
        //启用调试模式
        JPushInterface.setDebugMode(true);
        //初始化极光推送服务
        JPushInterface.init(getApplicationContext());
        if(BmobUser.getCurrentUser(User.class)!=null){
            startActivity(new Intent(StartActivity.this,HomeActivity.class));
        }else{
            //全屏显示
            requestWindowFeature(Window.FEATURE_NO_TITLE);  //无title
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏

            setContentView(R.layout.activity_start_layout);
            ActivityManager.addActivity(this);
            handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getApplication(),GuideActivity.class));
                    finish();
                }
            },3000);
        }

    }
}
