package com.example.asus.xiaoliu.fragment.messageChildFragment;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Comment;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MessageComment extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_comment);

        ImageView ivPre = findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MessageComment.this, HomeActivity.class);
                intent.putExtra("signMark","2");
                startActivity(intent);
                finish();
            }
        });
        listView=findViewById(R.id.lv_comment);

        BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
        dynamicBmobQuery.addWhereEqualTo("dynamicUser", BmobUser.getCurrentUser(User.class));
        dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
                    Log.e("查询成功","====================size======"+list.size());

                    List<BmobQuery<Comment>>queries=new ArrayList<BmobQuery<Comment>>();
                    for(int i=0;i<list.size();++i){
                        BmobQuery<Comment> commentBmobQuery=new BmobQuery<>();
                        commentBmobQuery.addWhereEqualTo("commentDynamic",list.get(i));
                        queries.add(commentBmobQuery);
                    }
                    BmobQuery<Comment> commentBmobQuery=new BmobQuery<>();
                    commentBmobQuery.or(queries);
                    commentBmobQuery.findObjects(new FindListener<Comment>() {
                        @Override
                        public void done(List<Comment> list, BmobException e) {
                            if(e==null){
                                Log.e("or","===========================>"+"查询成功"+list.size());
                                MessageAdapter messageAdapter=new MessageAdapter(MessageComment.this,R.layout.message_comment_items,list);
                                listView.setAdapter(messageAdapter);

                                for(Comment comment:list){
                                    Log.e("commentid","==========================>"+comment.getObjectId());
                                }
                            }else {
                                Log.e("or","====================================》"+e.toString());
                            }
                        }
                    });
                }else {
                    Log.e("查询失败","==============================="+e.toString());
                }
            }
        });



    }

    private class MessageAdapter extends BaseAdapter{
        private Context context;
        private int itemLayoutID;
        private List<Comment> datalist;

        public MessageAdapter(Context context, int itemLayoutID, List<Comment> datalist) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(context);
            convertView=inflater.inflate(itemLayoutID,null);
            Comment comment=datalist.get(position);

            final ImageView ivCommentTX=convertView.findViewById(R.id.iv_dynamic_comment);
            final TextView tvCommentName=convertView.findViewById(R.id.tv_comment_name);
            TextView tvTime=convertView.findViewById(R.id.tv_time);
            TextView tvPhoneType=convertView.findViewById(R.id.tv_phonetype);
            TextView tvCommentContent=convertView.findViewById(R.id.tv_pinglun);
            tvCommentContent.setText(comment.getCommentContent());
            tvTime.setText(comment.getCreatedAt());
            if(comment.getCommentPhoneType()!=null){
                tvPhoneType.setText(comment.getCommentPhoneType());
            }
            final BmobQuery<User> userBmobQuery=new BmobQuery<>();
            userBmobQuery.addWhereEqualTo("objectId",comment.getCommentUser().getObjectId());
            userBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        Log.e("commentuser","============================+查询成功");
                        User user=list.get(0);
                        if(user.getUserImage()!=null){
                            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                            Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(ivCommentTX);
                        }tvCommentName.setText(user.getUsername());
                    }else {
                        Log.e("commentUser","===============================查询失败"+e.toString());
                    }
                }
            });
            BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
            dynamicBmobQuery.addWhereEqualTo("objectId",comment.getCommentDynamic().getObjectId());
            final View finalConvertView = convertView;
            dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
                @Override
                public void done(List<Dynamic> list, BmobException e) {
                    if(e==null){
                        Log.e("dynamic","==============================评论的动态查询成功");
                        Dynamic dynamic=list.get(0);
                        TextView tvDynamicContent= finalConvertView.findViewById(R.id.tv_dynamic_content);
                        tvDynamicContent.setText(dynamic.getDynamicContent());
                    }else {
                        Log.e("dynamic","==============================评论的动态查询失败"+e.toString());
                    }
                }
            });
            User user= BmobUser.getCurrentUser(User.class);
            TextView tvDynamicName=convertView.findViewById(R.id.tv_nicheng);
            tvDynamicName.setText(user.getUsername());
            ImageView imgDynamicTX=convertView.findViewById(R.id.iv_dynamic_publish);

            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(imgDynamicTX);
            return convertView;
        }
    }

}
