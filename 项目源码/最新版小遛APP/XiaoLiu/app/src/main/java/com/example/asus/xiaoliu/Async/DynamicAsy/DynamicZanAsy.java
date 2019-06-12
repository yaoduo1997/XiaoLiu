package com.example.asus.xiaoliu.Async.DynamicAsy;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicZanAsy extends AsyncTask {
    private Dynamic dynamic;
    private int flag=0;
    private Context context;
    private TextView tvZan;
    private ImageView imageView;

    public DynamicZanAsy(Dynamic dynamic, Context context,TextView tvZan,ImageView imageView) {
        this.dynamic = dynamic;
        this.context = context;
        this.tvZan=tvZan;
        this.imageView=imageView;
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
        dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
                    BmobQuery<User> query=new BmobQuery<>();
                    query.addWhereRelatedTo("zanUser", new BmobPointer(dynamic));
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null){ ;
                                for(int i=0;i<list.size();++i){
                                    if(list.get(i).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())){
                                        flag++;
                                    }
                                }
                                if(flag==1){
                                    BmobRelation relation=new BmobRelation();
                                    relation.remove(BmobUser.getCurrentUser(User.class));
                                    dynamic.setZanUser(relation);

                                    final int num=dynamic.getZanNumber()-1;

                                    if(dynamic.getZanNumber()>0){
                                        dynamic.setZanNumber(dynamic.getZanNumber()-1);
                                    }else{
                                        dynamic.setZanNumber(dynamic.getZanNumber());
                                    }

                                    dynamic.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.e("bmob", "多对多删除关联成功");
                                                tvZan.setText(num+"");

//                                                Intent intent = new Intent();
//                                                intent.putExtra("signMark", String.valueOf(3));
//                                                intent.setClass(context, HomeActivity.class);
//                                                context.startActivity(intent);
//                                                tvZan.setText(num);
                                                Glide.with(context).load(R.drawable.zan).into(imageView);
                                            } else {
                                                Log.e("bob", "多对多数据关联删除失败");
                                            }
                                        }
                                    });
                                }else {
                                    final int num=dynamic.getZanNumber()+1;
                                    BmobRelation relation = new BmobRelation();
                                    relation.add(BmobUser.getCurrentUser(User.class));
                                    dynamic.setZanUser(relation);
                                    dynamic.setZanNumber(dynamic.getZanNumber() + 1);
                                    dynamic.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.e("bmob", "多对多数据关联成功");
//                                                Intent intent = new Intent();
//                                                intent.putExtra("signMark", String.valueOf(3));
//                                                intent.setClass(context, HomeActivity.class);
//                                                context.startActivity(intent);
                                                tvZan.setText(num+"");
                                                Glide.with(context).load(R.drawable.zan1).into(imageView);
                                            } else {
                                                Log.e("bob", "多对多数据关联失败");
                                            }
                                        }
                                    });
                                }
                            }else {
                                Log.e("zan","查询失败"+e.toString());
//                                if(flag==0) {
//                                    BmobRelation relation = new BmobRelation();
//                                    relation.add(BmobUser.getCurrentUser(User.class));
//                                    dynamic.setZanUser(relation);
//                                    dynamic.setZanNumber(dynamic.getZanNumber() + 1);
//                                    dynamic.update(new UpdateListener() {
//                                        @Override
//                                        public void done(BmobException e) {
//                                            if (e == null) {
//                                                Log.e("bmob", "多对多数据关联成功");
//                                                Intent intent = new Intent();
//                                                intent.putExtra("signMark", String.valueOf(3));
//                                                intent.setClass(context, HomeActivity.class);
//                                                context.startActivity(intent);
//                                            } else {
//                                                Log.e("bob", "多对多数据关联失败");
//                                            }
//                                        }
//                                    });
//                                }

                            }
                            flag=0;
                        }
                    });

                }
            }
        });
        return null;
    }
}
