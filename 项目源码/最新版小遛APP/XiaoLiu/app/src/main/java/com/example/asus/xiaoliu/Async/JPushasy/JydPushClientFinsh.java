package com.example.asus.xiaoliu.Async.JPushasy;

import android.annotation.SuppressLint;
import android.util.Log;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JydPushClientFinsh {

    private static String MASTER_SECRET = "47245ae3785407b7267b0418";
    private static String APP_KEY = "b8f02af9b321e0e3bb0190f1";
    private static JPushClient jPushClient = new JPushClient(MASTER_SECRET, APP_KEY,  null, ClientConfig.getInstance());
    public static final String TITLE = "完成遛狗";
    public static String ALERT = "遛狗师王凯已经遛完了您的狗狗，并将狗狗送到指定位置！";
    private static String orderClientId;

    public JydPushClientFinsh(String orderClientId, String liuGouShi){
        this.orderClientId = orderClientId;
        this.ALERT = "遛狗师"+liuGouShi+"已经遛完了您的狗狗，并将狗狗送到指定位置！";
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
