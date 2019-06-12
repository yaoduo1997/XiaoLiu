package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.Async.JPushasy.JPushAsync;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class ShouFour extends AppCompatActivity{
    private ListView listView;
    private OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build();//超时设置
    private String orderClientId;
    private String orderId;
    private String LiuGouShi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouforth);

        LiuGouShi = BmobUser.getCurrentUser(User.class).getUsername();
        listView = (ListView)findViewById(R.id.ll_orders);
        OrderList();

        ImageView fanhui =(ImageView) findViewById(R.id.iv_return);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent();
                intentf.setClass(ShouFour.this, HomeActivity.class);
                startActivity(intentf);
                finish();
            }
        });
    }
    private void OrderList(){
        BmobQuery<Order> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereDoesNotExists("orderMandatary");
        categoryBmobQuery.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    Log.e("msg", "查询数据成功"+object.size());
//                    show("查询数据成功");
                    lgsAdapter adapter = new lgsAdapter(ShouFour.this, R.layout.activity_task_item, object);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private  class lgsAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Order> datalist;
        public lgsAdapter(Context context, int itemLayoutID, List<Order>datalist)
        {
            this.context=context;
            this.itemLayoutID=itemLayoutID;
            this.datalist=datalist;
        }
        //返回数据个数
        @Override
        public int getCount() {
            return datalist.size();
        }
        //根据ListView中Item的位置返回Item对应数据
        @Override
        public Object getItem(int position) {
            return   datalist.get(position);
        }
        //根据ListView中Item位置，返回Item中相应数据ID
        @Override
        public long getItemId(int position) {
            return position;
        }
        //根据位置返回对应位置的Item的显示View
        public View getView(int position, View convertView, ViewGroup parent) {
            //设置推送者的别名
            JPushInterface.setAlias(getApplicationContext(),0,BmobUser.getCurrentUser(User.class).getObjectId());
            //位置，转换View, 新生成的View的父容器
            //使用布局填充器，根据构造函数中接收的布局文件ID创建相应对象
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(itemLayoutID,null);
            // 根据Item位置，获取datalist对应位置的数据
            final Order order=datalist.get(position);
            //获取，设置姓名控件
            final TextView tvName=(TextView)view.findViewById(R.id.username);
            BmobQuery<User> bmobQuery = new BmobQuery<User>();
            bmobQuery.getObject(order.getOrderClient().getObjectId(), new QueryListener<User>() {
                @Override
                public void done(User object,BmobException e) {
                    if(e==null){
//                        show("查询成功");
                        tvName.setText(object.getUsername());
                    }else{
                        show("查询失败1：" + e.getMessage());
                    }
                }
            });

            TextView tvDate=(TextView)view.findViewById(R.id.order_time);
            tvDate.setText(order.getOrderStartTime());
            TextView tvAddress=(TextView)view.findViewById(R.id.order_address);
            tvAddress.setText(order.getOrderAddress());
            final TextView tvKind=(TextView)view.findViewById(R.id.kind);
            BmobQuery<Dog> bmobQuery2 = new BmobQuery<Dog>();
            bmobQuery2.getObject(order.getDog().getObjectId(), new QueryListener<Dog>() {
                @Override
                public void done(Dog object,BmobException e) {
                    if(e==null){
                        //show("查询成功");
                        tvKind.setText(object.getDogType());
                    }else{
                        show("查询失败2：" + e.getMessage());
                    }
                }
            });

            TextView tvTimes=(TextView)view.findViewById(R.id.order_times);
            tvTimes.setText(order.getTime()+"");
            // 返回创建好的View（已经设置完数据）
            Button btnAccept = (Button)view.findViewById(R.id.accept);
            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobQuery<Order> bmobQuery1 = new BmobQuery<Order>();
                    bmobQuery1.getObject(order.getObjectId(), new QueryListener<Order>() {
                        @Override
                        public void done(Order object,BmobException e) {
                            if(e==null){
                                orderClientId = object.getOrderClient().getObjectId();
                                orderId = object.getObjectId();
                                Log.e("接受通知者:  ",orderClientId);
                                User user = (User) BmobUser.getCurrentUser(User.class);
                                object.setOrderMandatary(user);
                                object.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            show("接单成功");
                                            Log.e("接单","ok");
                                            new JPushAsync(getApplicationContext(),orderClientId,orderId,LiuGouShi).executeOnExecutor(Executors.newCachedThreadPool());
                                        }else {
                                            Log.e("接单","失败"+e.toString());
                                        }
                                    }
                                });
                                Log.e("接受订单了",user.getUsername());
                            }else{
                                show("查询失败：" + e.getMessage());
                            }
                        }
                    });
                }
            });

            return  view;
        }
    }

    private void show(String msg) {//测试打印成功信息
        Toast.makeText(ShouFour.this, msg, Toast.LENGTH_LONG).show();
    }

}
