package com.example.asus.xiaoliu.Async.SignAsy;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindOrderMandataryById extends AsyncTask {
    private Order order;
    private View view;
    private User user;

    public FindOrderMandataryById(Order order, View view) {
        this.order = order;
        this.view = view;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<User> su = new BmobQuery<>();
        su.addWhereEqualTo("objectId", order.getOrderMandatary().getObjectId());
        su.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        user = list.get(0);
                        TextView tvLgs = (TextView) view.findViewById(R.id.wxs_lgs);
                        tvLgs.setText("遛狗师"+user.getUsername());
                    }
                }
            }
        });
        return null;
    }
}
