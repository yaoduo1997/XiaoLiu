package com.example.asus.xiaoliu.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Comment;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.dynamicChildren.AllComments;
import com.example.asus.xiaoliu.fragment.dynamicChildren.ReleaseDynamic;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DynamicFragment extends Fragment {
    private View mContentView;
    private ListView listView;
    private LinearLayout llComent;
    private User user;
    private int flag=0;
    public DynamicFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_dynamic_layout,container,false);

       listView=view.findViewById(R.id.dynamic_list);
       initList();

        ImageView imageView=view.findViewById(R.id.iv_add_dynamic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ReleaseDynamic.class);
                startActivity(intent);
            }
        });

       return  view;

    }
    public  void initList(){
        BmobQuery<Dynamic> selectDynamic=new BmobQuery<>();
        selectDynamic.order("-createdAt");//按照创建动态的时间降序排列，最新创建的会排在前面
        selectDynamic.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
//                    Toast.makeText(getActivity(),"数据查询成功"+list.size(),Toast.LENGTH_SHORT).show();
                    Log.e("数据查询成功",list.size()+"");
                    DynamicAdapter dynamicAdapter=new DynamicAdapter(getActivity(), R.layout.dynamic_items,list);
                    listView.setAdapter(dynamicAdapter);
                }else {
                    Toast.makeText(getActivity(),"数据查询失败"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class DynamicAdapter extends BaseAdapter{

        private Context context;
        private int itemLayoutID;
        private List<Dynamic>datalist;

        public DynamicAdapter(Context context, int itemLayoutID, List<Dynamic> datalist) {
            this.context = context;
            this.itemLayoutID = itemLayoutID;
            this.datalist = datalist;
        }

        @Override
        public int getCount() {
            return datalist.size();
        }


        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //位置，转换View, 新生成的View的父容器
            //使用布局填充器，根据构造函数中接收的布局文件ID创建相应对象
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(context);
                convertView=inflater.inflate(itemLayoutID,null);
            }
//            LayoutInflater inflater=LayoutInflater.from(context);
//            convertView=inflater.inflate(itemLayoutID,null);
            llComent = convertView.findViewById(R.id.ll_comment);

            final Dynamic dynamic=datalist.get(position);

            final View finalConvertView = convertView;

            TextView tvContent=convertView.findViewById(R.id.dynamic_item_txt);
            TextView tvCnum=convertView.findViewById(R.id.tv_comment_num);
            TextView tvPnum=convertView.findViewById(R.id.tv_praise_num);
            TextView tvDate=convertView.findViewById(R.id.date);
            TextView tvDevice=convertView.findViewById(R.id.device);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
            tvDate.setText(dynamic.getCreatedAt());
            tvContent.setText(dynamic.getDynamicContent());
            ImageView imgContent=convertView.findViewById(R.id.dynamic_item_img);
            if(dynamic.getDynamicImage()!=null) {
                DownLoad load = new DownLoad();
                load.downloadFile(dynamic.getDynamicImage(), imgContent);
                Log.e("获取的图片",dynamic.getDynamicImage().toString());
            }else{
                imgContent.setVisibility(View.GONE);
            }
            BmobQuery<User> bmobQuery=new BmobQuery<>();
            bmobQuery.addWhereEqualTo("objectId",dynamic.getDynamicUser().getObjectId());
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        ImageView imgTX= finalConvertView.findViewById(R.id.touxiang);
                        Log.e("user","查询成功");
                        user=list.get(0);
                        BmobFile bmobFile=user.getUserImage();
                        DownLoad downLoad=new DownLoad();
                        downLoad.downloadFile(bmobFile,imgTX);
                        Log.e("头像的获取信息",bmobFile.toString());
                        TextView tvName=finalConvertView.findViewById(R.id.nicheng);
                        tvName.setText(user.getUsername());
                    }
                }
            });

            Log.e("userId",dynamic.getDynamicUser().getObjectId());
            tvPnum.setText(String.valueOf(dynamic.getZanNumber()));
            tvCnum.setText(String.valueOf(dynamic.getPlNumber()));
            tvDevice.setText(dynamic.getDevice());

            final ImageView ivPrise=convertView.findViewById(R.id.iv_praise);//赞
            //判断用户对动态是否点赞，点赞的图片是红色的，没有点赞的是黑色的
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereRelatedTo("zanUser", new BmobPointer(dynamic));
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object,BmobException e) {
                    if(e==null){
                        for(int i=0;i<object.size();i++){
                            if (object.get(i).getObjectId().equals(BmobUser.getCurrentUser(User.class).getObjectId())) {
                                ivPrise.setImageResource(R.drawable.zan1);
                                Log.e("赞过了","zan1");
                            }else{
                                ivPrise.setImageResource(R.drawable.zan);
                                Log.e("没赞过","zan");
                            }
                        }
                        Log.e("bmob点赞","查询个数："+object.size());
                    }else{
                        Log.e("bmob点赞","失败："+e.getMessage());
                    }
                }

            });

            //评论
            Button btnOk=convertView.findViewById(R.id.btn_ok);
            final EditText etContent=convertView.findViewById(R.id.et_content);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String content=etContent.getText().toString();
                    if(content!=null&&!"".equals(content)){
                        final Comment comment=new Comment();
                        BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
                        dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
                            @Override
                            public void done(List<Dynamic> list, BmobException e) {
                                if(e==null){
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
                                                            intent.setClass(getActivity(),HomeActivity.class);
                                                            startActivity(intent);
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
                    }else {
                        Toast.makeText(getActivity(),"不能评论空值哦",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            //点赞
            ivPrise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
                                            if(flag!=0){
                                                BmobRelation relation=new BmobRelation();
                                                relation.remove(BmobUser.getCurrentUser(User.class));
                                                dynamic.setZanUser(relation);
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
                                                            Intent intent = new Intent();
                                                            intent.putExtra("signMark", String.valueOf(3));
                                                            intent.setClass(getActivity(), HomeActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Log.e("bob", "多对多数据关联删除失败");
                                                        }
                                                    }
                                                });
                                            }else {
                                                BmobRelation relation = new BmobRelation();
                                                relation.add(BmobUser.getCurrentUser(User.class));
                                                dynamic.setZanUser(relation);
                                                dynamic.setZanNumber(dynamic.getZanNumber() + 1);
                                                dynamic.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            Log.e("bmob", "多对多数据关联成功");
                                                            Intent intent = new Intent();
                                                            intent.putExtra("signMark", String.valueOf(3));
                                                            intent.setClass(getActivity(), HomeActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Log.e("bob", "多对多数据关联失败");
                                                        }
                                                    }
                                                });
                                            }
                                        }else {
                                            Log.e("zan","查询失败"+e.toString());
                                            if(flag==0) {
                                                BmobRelation relation = new BmobRelation();
                                                relation.add(BmobUser.getCurrentUser(User.class));
                                                dynamic.setZanUser(relation);
                                                dynamic.setZanNumber(dynamic.getZanNumber() + 1);
                                                dynamic.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            Log.e("bmob", "多对多数据关联成功");
                                                            Intent intent = new Intent();
                                                            intent.putExtra("signMark", String.valueOf(3));
                                                            intent.setClass(getActivity(), HomeActivity.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Log.e("bob", "多对多数据关联失败");
                                                        }
                                                    }
                                                });
                                            }

                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            });

            //查看所有评论,实现的不够完美
            ImageView ivAllComments=convertView.findViewById(R.id.iv_unfold);
            ivAllComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), AllComments.class);
                    intent.putExtra("dyId",dynamic.getObjectId());
                    startActivity(intent);
                }
            });
//            //转发
//            ivAllComments.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    BmobQuery<Dynamic>bmobQuery=new BmobQuery<>();
//                    bmobQuery.findObjects(new FindListener<Dynamic>() {
//                        @Override
//                        public void done(List<Dynamic> list, BmobException e) {
//                            if(e==null){
//                                final Dynamic dynamic=list.get(position);
//                                BmobRelation bmobRelation=new BmobRelation();
//                                bmobRelation.add(BmobUser.getCurrentUser(User.class));
//                                dynamic.setZfUser(bmobRelation);
//                                dynamic.setZfNumber(dynamic.getZfNumber()+1);
//                                dynamic.update(new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if(e==null){
//                                            Log.e("bmob","关联成功");
//                                            Dynamic dynamic1=dynamic;
//                                            dynamic1.setZfNumber(0);
//                                            dynamic1.setZanNumber(0);
//                                            dynamic1.setPlNumber(0);
//                                            dynamic1.setZfUser(null);
//                                            dynamic1.setZanUser(null);
//                                            dynamic1.setDynamicUser(BmobUser.getCurrentUser(User.class));
//                                            dynamic1.save(new SaveListener<String>() {
//                                                @Override
//                                                public void done(String s, BmobException e) {
//                                                    if(e==null){
//                                                        Log.e("bmob","转发成功");
//                                                        Intent intent=new Intent();
//                                                        intent.putExtra("signMark",String.valueOf(3));
//                                                        intent.setClass(getActivity(),HomeActivity.class);
//                                                        startActivity(intent);
//                                                    }else {
//                                                        Log.e("bmob","转发失败"+e.toString());
//                                                    }
//                                                }
//                                            });
//
//                                        }else
//                                        {
//                                            Log.e("bmob","转发失败"+e.toString());
//                                        }
//                                    }
//                                });
//                            }else {
//                                Log.e("bmob","查询动态失败"+e.toString());
//                            }
//                        }
//                    });
//                }
//            });

            return convertView;
        }
    }
}
