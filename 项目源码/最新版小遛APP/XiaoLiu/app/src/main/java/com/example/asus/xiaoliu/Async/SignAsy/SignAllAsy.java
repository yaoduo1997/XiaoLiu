package com.example.asus.xiaoliu.Async.SignAsy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.asus.xiaoliu.Adapter.SignAdapter.OrderAdapter;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;

import java.util.Collections;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SignAllAsy extends AsyncTask {
    private ListView listView;
    private Context context;

    public SignAllAsy(ListView listView, Context context) {
        this.listView = listView;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Order> yy = new BmobQuery<>();
        if(user.getUserIdentity().equals("狗狗主人")) {
            yy.addWhereEqualTo("orderClient", user);
        }else{
            yy.addWhereEqualTo("orderMandatary",user);
        }
        yy.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    Log.e("OrderList所有", "查询数据成功"+object.size());
                    //为了将最新内容显示出来，将列表中的内容进行反转。
                    Collections.reverse(object);
                    OrderAdapter adapter = new OrderAdapter(context, R.layout.sign_orderitemss, object);
                    listView.setAdapter(adapter);
                }
            }
        });
        return null;
    }
}
