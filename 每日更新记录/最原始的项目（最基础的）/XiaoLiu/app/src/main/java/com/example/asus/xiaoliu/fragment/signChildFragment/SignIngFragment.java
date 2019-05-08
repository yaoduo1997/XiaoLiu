package com.example.asus.xiaoliu.fragment.signChildFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class SignIngFragment extends Fragment {
    private ListView listView;
    private Dog dog;
    private View v;
    private User user;
    private BmobFile bmobFile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.sign_all_view, null);
        listView=(ListView)v.findViewById(R.id.lv_test);
        OrderList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return v;
    }

    private void OrderList() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Order> yy = new BmobQuery<>();
        if(user.getUserIdentity().equals("狗狗主人")) {
            yy.addWhereEqualTo("orderClient", user);
        }else{
            yy.addWhereEqualTo("orderMandatary",user);
        }
        yy.addWhereEqualTo("orderState", "进行中");
        yy.findObjects(new FindListener<Order>() {
            @Override
            public void done(List<Order> object, BmobException e) {
                if (e == null) {
                    Log.e("进行中", "查询数据成功"+object.size());
//                    show("查询数据成功"+object.size());
                    //为了将最新内容显示出来，将列表中的内容进行反转。
//                    Collections.reverse(object);
                    OrderAdapter adapter = new OrderAdapter(getActivity(), R.layout.sign_orderitems, object);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private  class OrderAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Order>datalist;
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
            LinearLayout llButton = convertView.findViewById(R.id.ll_button);
            Button btnStart = convertView.findViewById(R.id.btn_start);
            Button btnEnd = convertView.findViewById(R.id.btn_end);
            if(BmobUser.getCurrentUser(User.class).getUserIdentity().equals("狗狗主人")){
                llButton.setVisibility(View.GONE);
            }else{
                btnStart.setVisibility(View.GONE);
                btnEnd.setVisibility(View.VISIBLE);
            }
            // 根据Item位置，获取datalist对应位置的数据
            final Order order=datalist.get(position);

            BmobQuery<Dog> sd=new BmobQuery<>();
            sd.addWhereEqualTo("objectId",datalist.get(position).getDog().getObjectId());
            final View finalConvertView = convertView;
            sd.findObjects(new FindListener<Dog>() {
                @Override
                public void done(List<Dog> list, BmobException e) {
                    if(e==null){
                        dog=list.get(0);
                        bmobFile=dog.getDogImage();
                        if(bmobFile!=null){
                            ImageView imageView=(ImageView) finalConvertView.findViewById(R.id.wxs_image);
                            DownLoad downLoad=new DownLoad();
                            downLoad.downloadFile(bmobFile,imageView);
                        }
                        Log.e("selectDog","查询成功");
                        Log.e("dogname",dog.getDogName());

                        TextView tvState=(TextView) finalConvertView.findViewById(R.id.wxs_state);
                        TextView tvStart=(TextView) finalConvertView.findViewById(R.id.wxs_order_start);
                        TextView tvEnd=(TextView) finalConvertView.findViewById(R.id.wxs_order_end);
                        TextView tvDog=(TextView) finalConvertView.findViewById(R.id.wxs_order_dog);
                        TextView tvId=(TextView) finalConvertView.findViewById(R.id.wxs_id);
                        tvState.setText(order.getOrderState());

                        tvStart.setText(order.getOrderStartTime());
                        tvEnd.setText(order.getOrderEndTime());
                        tvDog.setText(dog.getDogName());
                        tvId.setText(order.getObjectId());
                    }else {
                        Log.e("selectdog","查询失败"+e.toString());
                    }
                }
            });

            if(order.getOrderMandatary()!=null&&order.getOrderMandatary().getObjectId()!=null) {
                BmobQuery<User> su = new BmobQuery<>();
                su.addWhereEqualTo("objectId", order.getOrderMandatary().getObjectId());
                su.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null) {
                            if (list.size() != 0) {
                                user = list.get(0);
                                TextView tvLgs = (TextView) finalConvertView.findViewById(R.id.wxs_lgs);
                                tvLgs.setText("        遛狗师"+user.getUsername());
                            }
                        }
                    }
                });
            }

            //结束订单
            btnEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        order.setOrderState("已完成");
                        order.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Log.e("修改成功",order.getObjectId());
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), HomeActivity.class);
                                    intent.putExtra("signMark","4");
                                    startActivity(intent);
                                }else{
                                    Log.e("修改不成功！",e.toString());
                                }
                            }
                        });
                    }
            });
            return  convertView;
        }
    }
}
