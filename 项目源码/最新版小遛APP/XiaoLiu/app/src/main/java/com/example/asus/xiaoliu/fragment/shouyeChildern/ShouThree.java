package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.Async.DynamicAsy.FindAllDynamicAsy;
import com.example.asus.xiaoliu.Async.LiuGouShiAsy.FindAllLiuGouShiAsy;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

public class ShouThree extends AppCompatActivity {
    private ListView listView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouthree);
        ActivityManager.addActivity(this);
        listView = (ListView) findViewById(R.id.ll_walkers);
        new FindAllLiuGouShiAsy(this, listView).executeOnExecutor(Executors.newCachedThreadPool());
        ImageView fanhui = (ImageView) findViewById(R.id.iv_return);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf = new Intent();
                intentf.setClass(ShouThree.this, HomeActivity.class);
                startActivity(intentf);
                finish();
            }
        });
    }
}
