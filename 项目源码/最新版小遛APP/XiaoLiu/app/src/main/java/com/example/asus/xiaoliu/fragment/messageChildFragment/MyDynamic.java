package com.example.asus.xiaoliu.fragment.messageChildFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyDynamic extends AppCompatActivity {
    private ListView listView;

    private View mContentView;
    private Dynamic dynamic;
    private LinearLayout llComent;
    private LinearLayout llMyDynamic;
    private LinearLayout llGuangChang;
    private User user;
    private int i = 0;
    private int flag = 0;
    private int positions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dynamic_layout);

        llGuangChang = findViewById(R.id.ll_guangchang);
        llGuangChang.setVisibility(View.GONE);
        llMyDynamic = findViewById(R.id.ll_mydynamic);
        llMyDynamic.setVisibility(View.VISIBLE);
        ImageView ivPre = findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyDynamic.this, HomeActivity.class);
                intent.putExtra("signMark","2");
                startActivity(intent);
                finish();
            }
        });

        listView=findViewById(R.id.dynamic_list);
        initList();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positions=position;
                showDialog();
                return true;
            }
        });
    }
    public void  showDialog(){

        AlertDialog.Builder builder=new AlertDialog.Builder(MyDynamic.this);
        View  view=getLayoutInflater().inflate(R.layout.dialog_view,null);
        builder.setTitle("小遛提示您:");
        TextView textView=view.findViewById(R.id.tv_content);
        textView.setText("您确定要删除这条说说嘛？");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
                dynamicBmobQuery.addWhereEqualTo("dynamicUser", BmobUser.getCurrentUser(User.class));
                dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
                    @Override
                    public void done(List<Dynamic> list, BmobException e) {
                        if(e==null){
                            Log.e("selectDynamic","查询要删除的动态成功");
                            Dynamic dynamic=list.get(positions);
                            dynamic.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e==null){
                                        Log.e("删除成功","===============删除动态成功");
                                        Intent intent=new Intent();
                                        intent.setClass(MyDynamic.this,MyDynamic.class);
                                        startActivity(intent);
                                    }else {
                                        Log.e("删除","===============删除动态失败"+e.toString());
                                    }
                                }
                            });
                        }else {
                            Log.e("selectDynamic","查询要删除的动态失败"+e.toString());
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }
    private  void initList(){
        BmobQuery<Dynamic> dynamicBmobQuery=new BmobQuery<>();
        dynamicBmobQuery.addWhereEqualTo("dynamicUser", BmobUser.getCurrentUser(User.class));
        dynamicBmobQuery.findObjects(new FindListener<Dynamic>() {
            @Override
            public void done(List<Dynamic> list, BmobException e) {
                if(e==null){
                    Log.e("initlist","初始化成功");
                    DynamicAdapter dynamicAdapter=new DynamicAdapter(MyDynamic.this,R.layout.dynamic_items,list);
                    listView.setAdapter(dynamicAdapter);
                }else {
                    Log.e("initList","初始化失败"+e.toString());
                }
            }
        });
    }

    private class DynamicAdapter extends BaseAdapter {

        private Context context;
        private int itemLayoutID;
        private List<Dynamic> datalist;

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
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutID, null);
            llComent = convertView.findViewById(R.id.ll_comment);
            llComent.setVisibility(View.GONE);
            dynamic = datalist.get(position);


            final TextView tvName = convertView.findViewById(R.id.nicheng);
            TextView tvContent = convertView.findViewById(R.id.dynamic_item_txt);

            TextView tvCnum = convertView.findViewById(R.id.tv_comment_num);
            TextView tvPnum = convertView.findViewById(R.id.tv_praise_num);
            TextView tvDate = convertView.findViewById(R.id.date);

            tvDate.setText(dynamic.getCreatedAt());
            tvContent.setText(dynamic.getDynamicContent());
            ImageView imgContent = convertView.findViewById(R.id.dynamic_item_img);
            if (dynamic.getDynamicImage() != null) {
                RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
                Glide.with(context).load(dynamic.getDynamicImage().getUrl()).apply(requestOptions).into(imgContent);
            }else{
                imgContent.setVisibility(View.GONE);
            }
            User user= BmobUser.getCurrentUser(User.class);
            ImageView imgTx=convertView.findViewById(R.id.touxiang);

            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(context).load(user.getUserImage().getUrl()).apply(requestOptions).into(imgTx);

            Log.e("userId", dynamic.getDynamicUser().getObjectId());
            tvPnum.setText(String.valueOf(dynamic.getZanNumber()));
            tvCnum.setText(String.valueOf(dynamic.getPlNumber()));
            return convertView;
        }
    }
}
