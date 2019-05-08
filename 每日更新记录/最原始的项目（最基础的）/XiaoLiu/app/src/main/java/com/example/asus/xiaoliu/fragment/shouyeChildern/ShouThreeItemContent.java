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
        int age=intent.getIntExtra("age",0);
        tvAge.setText(age+"");
        //设置工作年份控件
        int year=intent.getIntExtra("num",0);
        tvYear.setText(year+"");
        //设置简介控件
        String message=intent.getStringExtra("message");
        tvMessage.setText(message);
        //设置头像控件
        String url=intent.getStringExtra("url");
        LoadImageAsyncTask asyncTask=new LoadImageAsyncTask(url,ivImage);
        asyncTask.execute("");

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

    //URL资源设置为imageview
    private class LoadImageAsyncTask extends AsyncTask {
        private String mUrl;
        private ImageView mImageView;
        private Bitmap mBitmap;

        LoadImageAsyncTask(String url,ImageView imageView){
            mImageView=imageView;
            mUrl=url;
        }

        //运行在子线程中
        //子线程
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                //创建URL对象
                URL url=new URL(mUrl);
                //创建URL连接对象
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                //设置连接的请求方式
                connection.setRequestMethod("GET");
                //设置连接超时时间
                connection.setConnectTimeout(8000);
                //设置数据传输超时时间
                connection.setReadTimeout(8000);
                //获取服务器返回数据的流，数据输入流
                InputStream in=connection.getInputStream();
                //从输入流读取数据，转换成Bitmap，解码
                mBitmap= BitmapFactory.decodeStream(in);
                //关闭流
                in.close();
                //断开连接
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("test",e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test",e.toString());
            }
            return null;
        }

        //doInBackground()方法执行完成之后调用
        //UI主线程
        @Override
        protected void onPostExecute(Object o) {
            mImageView.setImageBitmap(mBitmap);
            super.onPostExecute(o);
        }
    }
}
