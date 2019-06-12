package com.example.asus.xiaoliu.Async.SignAsy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dog;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindDogById extends AsyncTask{
    private String dogId;
    private View view;
    private Dog dog;
    private BmobFile bmobFile;
    private Order order;
    private Context context;
    public FindDogById(View view,Order order,Context context) {
        this.view = view;
        this.order=order;
        this.context=context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        dogId=order.getDog().getObjectId();
        BmobQuery<Dog> sd=new BmobQuery<>();
        sd.addWhereEqualTo("objectId",dogId);
        sd.findObjects(new FindListener<Dog>() {
            @Override
            public void done(List<Dog> list, BmobException e) {
                if(e==null){
                    dog=list.get(0);
                    bmobFile=dog.getDogImage();
                    if(bmobFile!=null) {
                        ImageView imageView = (ImageView)view.findViewById(R.id.wxs_image);
                        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(context).load(bmobFile.getUrl()).apply(requestOptions).into(imageView);
                    }else {
                        Log.e("狗狗照片","null");
                    }

                    Log.e("selectDog","查询成功");
                    Log.e("dogname",dog.getDogName());

                    TextView tvState=(TextView) view.findViewById(R.id.wxs_state);
                    TextView tvStart=(TextView) view.findViewById(R.id.wxs_order_start);
                    TextView tvEnd=(TextView)view.findViewById(R.id.wxs_order_end);
                    TextView tvDog=(TextView) view.findViewById(R.id.wxs_order_dog);
                    TextView tvId=(TextView)view.findViewById(R.id.wxs_id);
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
        return null;
    }
}
