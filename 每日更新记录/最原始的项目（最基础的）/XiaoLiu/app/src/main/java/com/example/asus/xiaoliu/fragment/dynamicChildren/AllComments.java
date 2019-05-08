package com.example.asus.xiaoliu.fragment.dynamicChildren;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Comment;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class AllComments extends AppCompatActivity {

    private ListView lvAllComments;
    private User user;
    private String dyId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allcomments);

        //返回
        ImageView ivPre = findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AllComments.this, HomeActivity.class);
                intent.putExtra("signMark","3");
                startActivity(intent);
                finish();
            }
        });

        Intent intent = getIntent();
        dyId = intent.getStringExtra("dyId");

        //获取动态信息
        BmobQuery<Dynamic> selectDynamic=new BmobQuery<>();
        selectDynamic.addWhereEqualTo("objectId",dyId);
        selectDynamic.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
                    Dynamic dy = list.get(0);
                    user = list.get(0).getDynamicUser();
                    //获取动态图片
                    ImageView imgTX = findViewById(R.id.dynamic_item_img);
                    BmobFile bmobFile=dy.getDynamicImage();
                    DownLoad downLoad=new DownLoad();
                    downLoad.downloadFile(bmobFile,imgTX);
                    TextView tvDate = findViewById(R.id.date);
                    //发布时间
                    tvDate.setText(dy.getCreatedAt());
                    Log.e("发布时间",dy.getCreatedAt());
                    Log.e("数据查询成功",list.size()+"");
                    //查询写动态的用户
                    BmobQuery<User> selectUser = new BmobQuery<>();
                    selectUser.addWhereEqualTo("objectId",user.getObjectId());
                    selectUser.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            if(e==null){
                                //发布者头像
                                ImageView ivImg = findViewById(R.id.touxiang);
                                BmobFile bmobFile = list.get(0).getUserImage();
                                DownLoad downLoad=new DownLoad();
                                downLoad.downloadFile(bmobFile,ivImg);
                                Log.e("发布者头像",list.get(0).getUserImage().toString());
                                //发布者姓名
                                TextView tvName = findViewById(R.id.nicheng);
                                tvName.setText(list.get(0).getUsername());
                                Log.e("发布者姓名",list.get(0).getUsername());
                                //发布者手机型号
                                TextView tvDevice = findViewById(R.id.device);
                                if(list.get(0).getDevice()!=null){
                                    tvDevice.setText(list.get(0).getDevice());
                                    Log.e("发布者手机型号",list.get(0).getDevice());
                                }
                            }else{
                                Log.e("用户查询失败",e.toString());
                            }
                        }
                    });
                }else {
                    Toast.makeText(AllComments.this,"数据查询失败"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //评论列表
        lvAllComments = findViewById(R.id.lv_allcomments);
        //初始化列表
        initAllCommentsList();

    }

    private void initAllCommentsList(){
        BmobQuery<Dynamic> selectDynamic=new BmobQuery<>();
        selectDynamic.addWhereEqualTo("objectId",dyId);
        selectDynamic.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e == null) {
                    Dynamic dynamic = list.get(0);
                    BmobQuery<Comment> commentBmobQuery = new BmobQuery<>();
                    commentBmobQuery.addWhereEqualTo("commentDynamic",dynamic);
                    commentBmobQuery.findObjects(new FindListener<Comment>() {
                        @Override
                        public void done(List<Comment> list, BmobException e) {
                            CommentsAdapter commentsAdapter = new CommentsAdapter(AllComments.this,R.layout.allcomments_items,list);
                            lvAllComments.setAdapter(commentsAdapter);
                        }
                    });
                }else{
                    Log.e("查询动态出错：",e.toString());
                }
            }
        });
    }

    private class CommentsAdapter extends BaseAdapter {

        private Context context;
        private int itemLaydoutID;
        private List<Comment> datalist;

        public CommentsAdapter(Context context, int itemLayoutID, List<Comment> datalist) {
            this.context = context;
            this.itemLaydoutID = itemLayoutID;
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
                convertView=inflater.inflate(itemLaydoutID,null);
            }
            final View finalConvertView = convertView;
            Comment comment = datalist.get(position);
            //获取评论者内容
            TextView tvContent = convertView.findViewById(R.id.comment_content);
            if(comment.getCommentContent()!=null){
                tvContent.setText(comment.getCommentContent());
            }
            //获取评论者昵称
            final TextView tvName = convertView.findViewById(R.id.comment_name);
            BmobQuery<User> userBmobQuery = new BmobQuery<>();
            userBmobQuery.addWhereEqualTo("objectId",comment.getCommentUser().getObjectId());
            userBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        tvName.setText(list.get(0).getUsername());
                        Log.e("评论的人:",list.get(0).getUsername());
                    }else{
                        Log.e("查询评论者出错",e.toString());
                    }
                }
            });

            return convertView;
        }
    }

}
