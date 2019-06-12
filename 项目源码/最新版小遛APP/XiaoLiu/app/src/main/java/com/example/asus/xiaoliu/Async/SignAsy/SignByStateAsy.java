package com.example.asus.xiaoliu.Async.SignAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.xiaoliu.Adapter.SignAdapter.OrderAdapter;
import com.example.asus.xiaoliu.BaiduMap.activity.TracingActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ProcessOn;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class SignByStateAsy extends AsyncTask {
    private String signState;
    private ListView listView;
    private Context context;
    private View progress;
    private LinearLayout layout;

    public SignByStateAsy(String signState, ListView listView, Context context) {
        this.signState = signState;
        this.listView = listView;
        this.context = context;
    }
    public SignByStateAsy(String signState, ListView listView, Context context,View progress,LinearLayout layout) {
        this.signState = signState;
        this.listView = listView;
        this.context = context;
        this.progress = progress;
        this.layout = layout;
    }
    @Override
    protected Object doInBackground(final Object[] objects) {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Order> yy = new BmobQuery<>();
        if(user.getUserIdentity().equals("狗狗主人")) {
            yy.addWhereEqualTo("orderClient", user);
        }else{
            yy.addWhereEqualTo("orderMandatary",user);
        }
        yy.addWhereEqualTo("orderState", signState);
        yy.findObjects(new FindListener<Order>() {
            @Override
            public void done(final List<Order> object, BmobException e) {
                if (e == null) {
                    Log.e("OrderList", "查询数据成功"+object.size());
                    //为了将最新内容显示出来，将列表中的内容进行反转。
                    BmobQuery<User>bmobQuery=new BmobQuery<>();
                    bmobQuery.getObject(BmobUser.getCurrentUser(User.class).getObjectId(), new QueryListener<User>() {
                        @Override
                        public void done(final User user, BmobException e) {
                            Collections.reverse(object);
                            OrderAdapter adapter;
                            if((user.getUserIdentity().equals("遛狗师")&&signState.equals("未开始"))){
                                    adapter=new OrderAdapter(context,R.layout.sign_orderitems,object);
                            }else{
                                adapter = new OrderAdapter(context, R.layout.sign_orderitemss, object);
                            }
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                        Log.e("click","-------------------------item被点击");
                                        if (signState.equals("进行中")||signState.equals("已完成")) {
                                            Order order = object.get(position);
                                            final String oid = order.getObjectId();
                                            final String entityName = order.getEntityName();

                                            Intent intent = new Intent();
                                            intent.putExtra("orderId", oid);
                                            Log.e("clicke", "====================" + user.toString());
                                            intent.putExtra("identity", String.valueOf(user.getUserIdentity()));
                                            intent.putExtra("entityName", entityName);
                                            intent.setClass(context, TracingActivity.class);
                                            context.startActivity(intent);
                                        }

                                        else if(signState.equals("未开始")&&user.getUserIdentity().equals("遛狗师")){
                                            Button btn=view.findViewById(R.id.btn_start);
                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    new ProcessOn(progress,layout).RouteProcess();
                                                    Order order = object.get(position);
                                                    final String oid = order.getObjectId();
                                                    final String entityName = order.getEntityName();
                                                    new UpdateUn(oid).executeOnExecutor(Executors.newCachedThreadPool());
                                                    Intent intent = new Intent();
                                                    intent.putExtra("orderId", oid);
                                                    Log.e("clicke", "====================" + user.toString());
                                                    intent.putExtra("identity", String.valueOf(user.getUserIdentity()));
                                                    intent.putExtra("entityName", entityName);
                                                    intent.setClass(context, TracingActivity.class);
                                                    context.startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                });
                        }
                    });
                }
            }
        });

        return null;
    }
}
