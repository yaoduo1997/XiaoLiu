package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShouThreeItemContent extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walkersdetails);
        ActivityManager.addActivity(this);

        ImageView fanhui=(ImageView)findViewById(R.id.iv_return);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(ShouThreeItemContent.this,ShouThree.class);
                startActivity(intent);
            }
        });

        ImageView ivImage=(ImageView)findViewById(R.id.iv_image);//获取头像控件
        TextView tvName=(TextView)findViewById(R.id.tv_walker_name);//获取姓名控件
        TextView tvAge=(TextView)findViewById(R.id.tv_walker_age);//获取年龄控件
        TextView tvYear=(TextView)findViewById(R.id.tv_walker_year);//获取工作年份控件
        TextView tvMessage=(TextView)findViewById(R.id.tv_walker_message);//获取简介控件
        ImageView ivSex = (ImageView)findViewById(R.id.iv_walker_sex) ;//获取性别控件
        final TextView tvPhone = (TextView)findViewById(R.id.tv_phone);//获取手机号控件

        Intent intent=getIntent();
        //设置头像控件
        String name=intent.getStringExtra("name");
        tvName.setText(name);
        //设置性别控件
        String sex=intent.getStringExtra("sex");
        if("女".equals(sex)){
            ivSex.setImageResource(R.drawable.female);
        }
        //设置手机号控件
        String phone = intent.getStringExtra("phone");
        tvPhone.setText(phone);
        //设置年龄控件
        int age=intent.getIntExtra("age",25);
        tvAge.setText(age+"");
        //设置工作年份控件
        int year=intent.getIntExtra("num",1);
        tvYear.setText(year+"");
        //设置简介控件
        String message=intent.getStringExtra("message");
        tvMessage.setText(message);
        //设置头像控件
        String url=intent.getStringExtra("url");
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop();
        if(url!=null) {
            Glide.with(this).load(url).apply(requestOptions).into(ivImage);
        }else{
            Glide.with(this).load(R.mipmap.moren).apply(requestOptions).into(ivImage);
        }

        //联系遛狗师
        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入的电话号码
                String phone = tvPhone.getText().toString().trim();
                //创建打电话的意图
                Intent intent1 = new Intent();
                //设置拨打电话的动作
                intent1.setAction(Intent.ACTION_DIAL);
                //设置拨打电话的号码
                intent1.setData(Uri.parse("tel:" + phone));
                //开启打电话的意图
                startActivity(intent1);
            }
        });

    }
}
