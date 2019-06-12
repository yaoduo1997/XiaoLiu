package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicZhuanFaAsy extends AsyncTask {
    private  Dynamic dynamic;
    private Context context;

    public DynamicZhuanFaAsy(Dynamic dynamic, Context context) {
        this.dynamic = dynamic;
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        BmobQuery<Dynamic> bmobQuery=new BmobQuery<>();
                    bmobQuery.findObjects(new FindListener<Dynamic>() {
                        @Override
                        public void done(List<Dynamic> list, BmobException e) {
                            if(e==null){
                                BmobRelation bmobRelation=new BmobRelation();
                                bmobRelation.add(BmobUser.getCurrentUser(User.class));
                                dynamic.setZfUser(bmobRelation);
                                dynamic.setZfNumber(dynamic.getZfNumber()+1);
                                dynamic.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            Log.e("bmob","关联成功");
                                            Dynamic dynamic1=dynamic;
                                            dynamic1.setZfNumber(0);
                                            dynamic1.setZanNumber(0);
                                            dynamic1.setPlNumber(0);
                                            dynamic1.setZfUser(null);
                                            dynamic1.setZanUser(null);
                                            dynamic1.setDynamicUser(BmobUser.getCurrentUser(User.class));
                                            dynamic1.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if(e==null){
                                                        Log.e("bmob","转发成功");
                                                        Intent intent=new Intent();
                                                        intent.putExtra("signMark",String.valueOf(3));
                                                        intent.setClass(context,HomeActivity.class);
                                                        context.startActivity(intent);
                                                    }else {
                                                        Log.e("bmob","转发失败"+e.toString());
                                                    }
                                                }
                                            });

                                        }else
                                        {
                                            Log.e("bmob","转发失败"+e.toString());
                                        }
                                    }
                                });
                            }else {
                                Log.e("bmob","查询动态失败"+e.toString());
                            }
                        }
                    });
        return null;
    }
}
