package com.example.asus.xiaoliu.Async.JPushasy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.asus.xiaoliu.HomeActivity;

public class JPushAsync extends AsyncTask {
    private Context context;
    private String orderClientId;
    private String orderId;
    private String liuGouShi;

    public JPushAsync(Context context,String orderClientId,String orderId,String liuGouShi){
        this.context = context;
        this.orderClientId = orderClientId;
        this.orderId = orderId;
        this.liuGouShi = liuGouShi;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JydPushClient jydPushClient = new JydPushClient(orderClientId,orderId,liuGouShi);
        jydPushClient.onDo();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        context.startActivity(new Intent(context,HomeActivity.class));
    }
}
