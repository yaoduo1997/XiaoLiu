package com.example.asus.xiaoliu.Adapter.SignAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.xiaoliu.Async.SignAsy.FindDogById;
import com.example.asus.xiaoliu.Async.SignAsy.FindOrderMandataryById;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public  class OrderAdapter extends BaseAdapter {

    private Context context;
    private int itemLayoutID;
    private List<Order> datalist;
    private Dog dog;
    private User user;
    private BmobFile bmobFile;
    public OrderAdapter(Context context, int itemLayoutID, List<Order>datalist)
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
        //位置，转换View, 新生成的View的父容器
        //使用布局填充器，根据构造函数中接收的布局文件ID创建相应对象
//            if(convertView==null){
//                LayoutInflater inflater=LayoutInflater.from(context);
//                convertView=inflater.inflate(itemLayoutID,null);
//            }
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(itemLayoutID,null);
        //根据身份不同显示不同的数据
//        LinearLayout llButton = convertView.findViewById(R.id.ll_button);
//        llButton.setVisibility(View.GONE);
        // 根据Item位置，获取datalist对应位置的数据
        final Order order=datalist.get(position);
        new FindDogById(convertView,order,context).executeOnExecutor(Executors.newCachedThreadPool());
        if(order.getOrderMandatary()!=null&&order.getOrderMandatary().getObjectId()!=null) {
            new FindOrderMandataryById(order, convertView).executeOnExecutor(Executors.newCachedThreadPool());
        }

        return  convertView;
    }
}
