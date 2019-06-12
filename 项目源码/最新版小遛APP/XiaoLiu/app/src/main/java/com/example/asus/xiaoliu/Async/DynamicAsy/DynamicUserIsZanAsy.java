package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class DynamicUserIsZanAsy extends AsyncTask {
    private Dynamic dynamic;
    private ImageView ivPrise;

    public DynamicUserIsZanAsy(Dynamic dynamic, ImageView ivPrise) {
        this.dynamic = dynamic;
        this.ivPrise = ivPrise;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereRelatedTo("zanUser", new BmobPointer(dynamic));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    ivPrise.setImageResource(R.drawable.zan);
                    for(int i=0;i<object.size();i++){
                        Log.e("两个ID",object.get(i).getObjectId()+"用户者ID"+BmobUser.getCurrentUser(User.class).getObjectId());
                        if (object.get(i).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
                            ivPrise.setImageResource(R.drawable.zan1);
                            Log.e("赞过了","zan1");
                        }else{
                            Log.e("没赞过","zan");
                        }
                    }
                    Log.e("bmob点赞","查询个数："+object.size());
                }else{
                    ivPrise.setImageResource(R.drawable.zan);
                    Log.e("bmob点赞","失败："+e.getMessage());
                }
            }

        });
        return null;
    }
}
