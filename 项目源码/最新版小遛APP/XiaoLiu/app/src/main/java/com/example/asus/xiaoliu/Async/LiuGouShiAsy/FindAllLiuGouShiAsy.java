package com.example.asus.xiaoliu.Async.LiuGouShiAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus.xiaoliu.Adapter.LiuGouShi.LiuGouShiAdapter;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouThree;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouThreeItemContent;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FindAllLiuGouShiAsy extends AsyncTask {
    private Context context;
    private ListView listView;

    public FindAllLiuGouShiAsy(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.addWhereEqualTo("userIdentity", "遛狗师");
        categoryBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(final List<User> object, BmobException e) {
                if (e == null) {
                    Log.e("msg", "查询数据成功");
                    LiuGouShiAdapter liuGouShiAdapter=new LiuGouShiAdapter(context,R.layout.shouthreeitems,object);
                    listView.setAdapter(liuGouShiAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User user=object.get(position);
                            Intent intent=new Intent();
                            intent.setClass(context,ShouThreeItemContent.class);
                            intent.putExtra("name",user.getUsername());
                            intent.putExtra("phone",user.getMobilePhoneNumber());
                            intent.putExtra("sex",user.getUserSex());
                            intent.putExtra("age",user.getUserAge());
                            intent.putExtra("num",user.getWorkYear());
                            intent.putExtra("message",user.getUserMessage());
                            if(user.getUserImage()!=null) {
                                intent.putExtra("url", user.getUserImage().getUrl());
                            }
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
        return null;
    }
}
