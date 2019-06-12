package com.example.asus.xiaoliu.Async.JPushasy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.asus.xiaoliu.HomeActivity;

public class JPushAsyncFinsh extends AsyncTask {
    private Context context;
    private String orderClientId;
    private String liuGouShi;

    public JPushAsyncFinsh(Context context, String orderClientId,String liuGouShi){
        this.context = context;
        this.orderClientId = orderClientId;
        this.liuGouShi = liuGouShi;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        JydPushClientFinsh jydPushClient = new JydPushClientFinsh(orderClientId,liuGouShi);
        jydPushClient.onDo();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        context.startActivity(new Intent(context,HomeActivity.class));
    }
}
