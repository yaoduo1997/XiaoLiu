package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

        listView=(ListView)findViewById(R.id.ll_walkers);
        lgsList();

        ImageView fanhui=(ImageView)findViewById(R.id.iv_return);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentf=new Intent();
                intentf.setClass(ShouThree.this,HomeActivity.class);
                startActivity(intentf);
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                TextView tvId=(TextView)view.findViewById(R.id.tv_id);
                String Id=tvId.getText().toString();
                //show(Id);
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.getObject(Id, new QueryListener<User>() {
                    @Override
                    public void done(User user,BmobException e) {
                        if(e==null){
                            Intent intent=new Intent();
                            intent.setClass(ShouThree.this,ShouThreeItemContent.class);
                            intent.putExtra("name",user.getUsername());
                            intent.putExtra("phone",user.getMobilePhoneNumber());
                            intent.putExtra("sex",user.getUserSex());
                            intent.putExtra("age",user.getUserAge());
                            intent.putExtra("num",user.getWorkYear());
                            intent.putExtra("message",user.getUserMessage());
                            intent.putExtra("url",user.getUserImage().getUrl());//如果数据库有图片文件，也可通过这种方式获取url
//                            show("跳转");
                            startActivity(intent);
                        }else{
                            show("查询失败：" + e.getMessage());
                        }
                    }
                });

            }
        });
    }

    private void lgsList() {
        BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("userIdentity", "遛狗师");
        categoryBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                   Log.e("msg", "查询数据成功");
//                    show("查询数据成功");
                    lgsAdapter adapter = new lgsAdapter(ShouThree.this, R.layout.shouthreeitems, object);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    private  class lgsAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<User>datalist;
        public lgsAdapter(Context context, int itemLayoutID, List<User>datalist)
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
            //获取，设置id控件
            TextView tvId=(TextView)view.findViewById(R.id.tv_id);
            tvId.setText(user.getObjectId());
            //获取，设置头像控件
            ImageView imageView=(ImageView)view.findViewById(R.id.miv_image);
            LoadImageAsyncTask asyncTask=new LoadImageAsyncTask(user.getUserImage().getUrl(),imageView);
            asyncTask.execute("");
            // 返回创建好的View（已经设置完数据）
            return  view;
        }
    }

    private void show(String msg) {//测试打印成功信息
        Toast.makeText(ShouThree.this, msg, Toast.LENGTH_LONG).show();
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
