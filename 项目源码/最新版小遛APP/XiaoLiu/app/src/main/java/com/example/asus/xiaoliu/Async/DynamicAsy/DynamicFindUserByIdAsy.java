package com.example.asus.xiaoliu.Async.DynamicAsy;

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
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DynamicFindUserByIdAsy extends AsyncTask {
    private Dynamic dynamic;
    private View view;
    private User user;
    private Context context;

    public DynamicFindUserByIdAsy(Dynamic dynamic, View view,Context context) {
        this.dynamic = dynamic;
        this.view = view;
        this.context=context;

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("objectId",dynamic.getDynamicUser().getObjectId());
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    ImageView imgTX= view.findViewById(R.id.touxiang);
                    Log.e("user","查询成功");
                    user=list.get(0);
                    RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                    Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(imgTX);
                    TextView tvName=view.findViewById(R.id.nicheng);
                    tvName.setText(user.getUsername());
                }
            }
        });
        return null;
    }
}
