package com.example.asus.xiaoliu.Async.JPushasy;

import android.annotation.SuppressLint;
import android.util.Log;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JydPushClient {

    private static String MASTER_SECRET = "47245ae3785407b7267b0418";
    private static String APP_KEY = "b8f02af9b321e0e3bb0190f1";
    private static JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY,  null, ClientConfig.getInstance());
    public static final String TITLE = "订单接收";
    public static String ALERT = "您的订单编号为73db2c5548的订单，已被遛狗师王凯接收！";
    private static String orderClientId;

    public JydPushClient(String orderClientId,String orderId,String liuGouShi){
        this.orderClientId = orderClientId;
        this.ALERT = "您的订单编号为"+orderId+"73db2c5548的订单，已被遛狗师"+liuGouShi+"接收！";
    }

    @SuppressLint("LongLogTag")
    public void onDo() {
        //生成推送的内容，这里我们先测试全部推送
        PushPayload payload=buildPushObject_android_alias_alertWithTitle();

        try {
            System.out.println(payload.toString());
            PushResult result = jPushClient.sendPush(payload);
            System.out.println(result.toString()+"................................");
            Log.e("Got result - " ,result.toString());

        } catch (APIConnectionException e) {
            Log.e("Connection error. Should retry later. ", e.toString());

        } catch (APIRequestException e) {
            Log.e("Error response from JPush server. Should review and fix it. ", e.toString());
            Log.e("HTTP Status: ", String.valueOf(e.getStatus()));
            Log.e("Error Code: ", String.valueOf(e.getErrorCode()));
            Log.e("Error Message: ",e.getErrorMessage());
            Log.e("Msg ID: ", String.valueOf(e.getMsgId()));
        }
    }

    public static PushPayload buildPushObject_android_alias_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(orderClientId))
                .setNotification(Notification.android(ALERT, TITLE, null))
                .build();
    }

}
