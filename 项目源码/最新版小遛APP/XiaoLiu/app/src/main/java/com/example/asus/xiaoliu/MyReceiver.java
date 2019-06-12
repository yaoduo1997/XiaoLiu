package com.example.asus.xiaoliu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.asus.xiaoliu.BaiduMap.activity.TracingActivity;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取封装推送的消息的Bundle对象
        Bundle bundle = intent.getExtras();
        //获取推送的通知类型
        String action = intent.getAction();
        switch (action){
            case JPushInterface.ACTION_MESSAGE_RECEIVED://接收推送下来的自定义消息
                Log.e("yduo","收到推送下来的自定义消息");
                break;
            case JPushInterface.ACTION_NOTIFICATION_OPENED://普通通知（用户点击打开了通知）
                //获取通知标题
                String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
                //获取通知内容
                String content = bundle.getString(JPushInterface.EXTRA_ALERT);
                Log.e("yduo","收到通知"+title+","+content);
                Intent intentNormal = new Intent();
                intentNormal.setClass(context,TracingActivity.class);
                intentNormal.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentNormal);
                break;
        }
    }
}
