package com.example.asus.xiaoliu.Async.SignAsy;

import android.os.AsyncTask;

import com.example.asus.xiaoliu.entity.Order;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UpdateUn extends AsyncTask {
    private String orderId;

    public UpdateUn(String orderId) {
        this.orderId = orderId;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Order order=new Order();
        order.setOrderState("进行中");
        order.update(orderId,new UpdateListener() {
            @Override
            public void done(BmobException e) {

            }
        });
        return null;
    }
}
