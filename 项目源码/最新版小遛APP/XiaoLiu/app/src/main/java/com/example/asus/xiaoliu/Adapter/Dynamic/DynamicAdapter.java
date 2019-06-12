package com.example.asus.xiaoliu.Adapter.Dynamic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.Async.DynamicAsy.DownloadImgAsy;
import com.example.asus.xiaoliu.Async.DynamicAsy.DynamicAddCommentAsy;
import com.example.asus.xiaoliu.Async.DynamicAsy.DynamicFindUserByIdAsy;
import com.example.asus.xiaoliu.Async.DynamicAsy.DynamicUserIsZanAsy;
import com.example.asus.xiaoliu.Async.DynamicAsy.DynamicZanAsy;
import com.example.asus.xiaoliu.Async.DynamicAsy.DynamicZhuanFaAsy;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Comment;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.dynamicChildren.AllComments;


import java.util.List;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public  class DynamicAdapter extends BaseAdapter {

    private Context context;
    private int itemLayoutID;
    private List<Dynamic> datalist;
    private LinearLayout llComent;
    private ImageView lookComments;

    public DynamicAdapter(Context context, int itemLayoutID, List<Dynamic> datalist) {
        this.context = context;
        this.itemLayoutID = itemLayoutID;
        this.datalist = datalist;
    }

    @Override
    public int getCount() {
        return datalist.size();
    }


    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //位置，转换View, 新生成的View的父容器
        //使用布局填充器，根据构造函数中接收的布局文件ID创建相应对象
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(itemLayoutID,null);
        }
//            LayoutInflater inflater=LayoutInflater.from(context);
//            convertView=inflater.inflate(itemLayoutID,null);
        llComent = convertView.findViewById(R.id.ll_comment);

        final Dynamic dynamic=datalist.get(position);

        TextView tvContent=convertView.findViewById(R.id.dynamic_item_txt);
        TextView tvCnum=convertView.findViewById(R.id.tv_comment_num);
        lookComments = convertView.findViewById(R.id.iv_comment);
        lookComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Dynamic dynamic=datalist.get(position);
                intent.setClass(context, AllComments.class);
                intent.putExtra("dyId",String.valueOf(dynamic.getObjectId()));
                context.startActivity(intent);
            }
        });
        final TextView tvPnum=convertView.findViewById(R.id.tv_praise_num);
        TextView tvDate=convertView.findViewById(R.id.date);
        TextView tvDevice=convertView.findViewById(R.id.device);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        tvDate.setText(dynamic.getCreatedAt());
        tvContent.setText(dynamic.getDynamicContent());
        ImageView imgContent=convertView.findViewById(R.id.dynamic_item_img);
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

        if(dynamic.getDynamicImage()!=null) {
            Glide.with(context).load(dynamic.getDynamicImage().getUrl()).apply(requestOptions).into(imgContent);
        }else{
            imgContent.setVisibility(View.GONE);
        }
        new DynamicFindUserByIdAsy(dynamic,convertView,context).executeOnExecutor(Executors.newCachedThreadPool());

        Log.e("userId",dynamic.getDynamicUser().getObjectId());
        tvPnum.setText(String.valueOf(dynamic.getZanNumber()));
        tvCnum.setText(String.valueOf(dynamic.getPlNumber()));
        tvDevice.setText(dynamic.getDevice());

        final ImageView ivPrise=convertView.findViewById(R.id.iv_praise);//赞
        //判断用户对动态是否点赞，点赞的图片是红色的，没有点赞的是黑色的
        new DynamicUserIsZanAsy(dynamic,ivPrise).executeOnExecutor(Executors.newCachedThreadPool());

        //添加评论
        Button btnOk=convertView.findViewById(R.id.btn_ok);
        final EditText etContent=convertView.findViewById(R.id.et_content);
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content=etContent.getText().toString();
                if(content!=null&&!"".equals(content)){
                        new DynamicAddCommentAsy(dynamic,content,context).executeOnExecutor(Executors.newCachedThreadPool());
                }else {
                    Toast.makeText(context,"不能评论空值哦",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点赞
        ivPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DynamicZanAsy(dynamic,context,tvPnum,ivPrise).executeOnExecutor(Executors.newCachedThreadPool());
            }
        });
        //点击评论图片显示输入框
        llComent=convertView.findViewById(R.id.ll_comment);
        ImageView ivComment=convertView.findViewById(R.id.iv_comment);
//        ivComment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                    if(llComent.getVisibility()==View.VISIBLE){
//                        llComent.setVisibility(View.GONE);
//                    }else{
//                        llComent.setVisibility(View.VISIBLE);
//                    }
//                return false;
//            }
//        });
//        ivComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(llComent.getVisibility()==View.VISIBLE){
//                    llComent.setVisibility(View.GONE);
//                }else{
//                    llComent.setVisibility(View.VISIBLE);
//                }
//            }
//        });

        //转发
        ImageView ivAllComments=convertView.findViewById(R.id.iv_unfold);
        ivAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DynamicZhuanFaAsy(dynamic,context).executeOnExecutor(Executors.newCachedThreadPool());
            }
        });

        return convertView;
    }
}