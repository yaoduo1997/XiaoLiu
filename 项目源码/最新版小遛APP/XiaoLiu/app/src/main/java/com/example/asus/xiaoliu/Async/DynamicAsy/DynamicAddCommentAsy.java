package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.entity.Comment;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicAddCommentAsy extends AsyncTask {
    private Dynamic dynamic;
    private String content;
    private Context context;

    public DynamicAddCommentAsy(Dynamic dynamic, String content, Context context) {
        this.dynamic = dynamic;
        this.content = content;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
        dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
                    Comment comment=new Comment();
                    comment.setCommentDynamic(dynamic);
                    comment.setCommentContent(content);
                    comment.setCommentPhoneType(Build.MODEL);
                    dynamic.setPlNumber(dynamic.getPlNumber()+1);
                    comment.setCommentUser(BmobUser.getCurrentUser(User.class));
                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Log.e("comment","评论添加成功");
                                dynamic.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.e("dynamic","动态更新成功");
                                            Intent intent=new Intent();
                                            intent.putExtra("signMark",String.valueOf(3));
                                            intent.setClass(context,HomeActivity.class);
                                            context.startActivity(intent);
                                        }else {
                                            Log.e("dynamic","动态添加失败"+e.toString());
                                        }
                                    }
                                });
                            }else {
                                Log.e("comment","评论添加失败");
                            }
                        }
                    });
                }
            }
        });
        return null;
    }
}
