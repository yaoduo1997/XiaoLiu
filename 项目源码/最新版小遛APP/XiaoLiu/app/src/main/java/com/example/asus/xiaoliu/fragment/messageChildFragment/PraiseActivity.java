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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.Dynamic;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.DownLoad;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PraiseActivity extends AppCompatActivity {

    private ListView lvPraiseList;
    private User user;
    private Dynamic dy = new Dynamic();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_praise);

        ImageView ivPre = findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PraiseActivity.this, HomeActivity.class);
                intent.putExtra("signMark","2");
                startActivity(intent);
                finish();
            }
        });

        lvPraiseList = findViewById(R.id.lv_praise);
        initPraiseList();

    }

    public void initPraiseList() {
        BmobQuery<Dynamic> selectDynamic = new BmobQuery<>();
        selectDynamic.order("-createdAt");//按照创建动态的时间降序排列，最新创建的会排在前面
        selectDynamic.addWhereEqualTo("dynamicUser", BmobUser.getCurrentUser(User.class));
        selectDynamic.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if (e == null) {
                    Log.e("数据查询成功", list.size() + "");
                    PraiseAdapter praiseAdapter=new PraiseAdapter(PraiseActivity.this, R.layout.message_praise_items,list);
                    lvPraiseList.setAdapter(praiseAdapter);
                    Log.e("initPraiseList","初始化成功");
                } else {
                    Toast.makeText(PraiseActivity.this, "数据查询失败" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class PraiseAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Dynamic> datalist;

        public PraiseAdapter(Context context, int itemLayoutID, List<Dynamic> datalist) {
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

            if(convertView==null){
                LayoutInflater inflater= LayoutInflater.from(context);
                convertView=inflater.inflate(itemLayoutID,null);
            }
            //从list取出对象
            Dynamic dynamic = datalist.get(position);

            final View finalConvertView = convertView;

            final TextView tvPraiseName = convertView.findViewById(R.id.tv_praise_name);//赞的人的名字
            final TextView tvAllPraiseUser = convertView.findViewById(R.id.allpraiseuser);//赞的总共的人数
            //查询出所有的赞的用户
            BmobQuery<User> query = new BmobQuery<User>();
            query.addWhereRelatedTo("zanUser", new BmobPointer(dynamic));
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object,BmobException e) {
                    if(e==null){
                        int i=1;
                        String names = "";
                        for(User u:object){
                            if(i==object.size()){
                                names= names+u.getUsername();
                                tvPraiseName.setText(names);
                            }else{
                                names= names+u.getUsername()+"、";
                            }
                            i++;
                        }
                        tvAllPraiseUser.setText(String.valueOf(object.size()));
                        Log.e("所有赞的用户","查询个数："+object.size());
                    }else{
                        Log.e("所有赞的用户","失败："+e.getMessage());
                    }
                }

            });

            //显示动态内容
            TextView tvDynamicContent = convertView.findViewById(R.id.tv_dynamic_content);//动态的内容
            tvDynamicContent.setText(dynamic.getDynamicContent());
            //显示发布动态人的名字和头像
            BmobQuery<User> bmobQuery=new BmobQuery<>();
            bmobQuery.addWhereEqualTo("objectId",dynamic.getDynamicUser().getObjectId());
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        Log.e("user","查询成功");
                        User user=list.get(0);
                        ImageView ivDynamicImg = finalConvertView.findViewById(R.id.iv_dynamic_publish);//发布动态的人的头像


                        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop();
                        Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(ivDynamicImg);
                        TextView tvDynamicName = finalConvertView.findViewById(R.id.tv_nicheng);//发布动态的人的名字
                        tvDynamicName.setText(user.getUsername());
                    }else{
                        Log.e("user","查询失败！"+e.toString());
                    }
                }
            });

            return convertView;
        }
    }
}
