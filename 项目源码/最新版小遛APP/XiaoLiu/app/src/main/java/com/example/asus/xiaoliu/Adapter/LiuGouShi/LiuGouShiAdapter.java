package com.example.asus.xiaoliu.Adapter.LiuGouShi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

public class LiuGouShiAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<User> datalist;
        public LiuGouShiAdapter(Context context, int itemLayoutID, List<User>datalist)
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
            LayoutInflater inflater=LayoutInflater.from(context);
            View view=inflater.inflate(itemLayoutID,null);
            // 根据Item位置，获取datalist对应位置的数据
            User user=datalist.get(position);
            //获取，设置姓名控件
            TextView tvName=(TextView)view.findViewById(R.id.tv_name);
            tvName.setText(user.getUsername());
            //获取，设置简介控件
            TextView tvMessage=(TextView)view.findViewById(R.id.tv_message);
            tvMessage.setText(user.getUserMessage());

            ImageView imageView = (ImageView) view.findViewById(R.id.miv_image);
            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop();
            if(user.getUserImage()!=null) {
                Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(imageView);
            }else{
                Glide.with(context).load(R.mipmap.moren).apply(requestOptions).into(imageView);
            }
            return  view;
        }
}
