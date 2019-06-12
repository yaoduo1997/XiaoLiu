package com.example.asus.xiaoliu;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.asus.xiaoliu.Async.JPushasy.JPushAsyncFinsh;
import com.example.asus.xiaoliu.entity.Order;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.TabDb;

import java.util.concurrent.Executors;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.jpush.android.api.JPushInterface;

public class HomeActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost tabHost;
    private Integer signMark = null;
    private String orderClientId;
    private String LiuGouShi;
    private String oId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        ActivityManager.addActivity(this);
        //设置别名
        JPushInterface.setAlias(this,0, BmobUser.getCurrentUser(User.class).getObjectId());
        tabHost=(FragmentTabHost)super.findViewById(android.R.id.tabhost);
        tabHost.setup(this,super.getSupportFragmentManager(),
                R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);
        initTab();
        LiuGouShi = BmobUser.getCurrentUser(User.class).getUsername();
        if(getIntent().getStringExtra("signMark")!=null && !"".equals(getIntent().getStringExtra("signMark"))){
            Intent intent = getIntent();
            signMark = Integer.parseInt(intent.getStringExtra("signMark"))-1;
            tabHost.setCurrentTab(signMark);
            if(getIntent().getStringExtra("oid")!=null && !"".equals(getIntent().getStringExtra("oid"))){
                oId = intent.getStringExtra("oid");
                BmobQuery<Order> bmobQuery = new BmobQuery<Order>();
                bmobQuery.getObject(oId, new QueryListener<Order>() {
                    @Override
                    public void done(Order object,BmobException e) {
                        if(e==null){
                            orderClientId = object.getOrderClient().getObjectId();
                            Log.e("查询成功：",orderClientId);
                        }else{
                            Log.e("查询失败：",e.getMessage());
                        }
                    }
                });
//                new JPushAsyncFinsh(getApplicationContext(),orderClientId,LiuGouShi).executeOnExecutor(Executors.newCachedThreadPool());
            }
        }
    }
    private void initTab(){
        String tabs[]= TabDb.getTabsTxt();
        for (int i=0;i<tabs.length;i++){
            TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
            tabHost.setTag(i);
        }
//        if(signMark==null){
//            for (int i=0;i<tabs.length;i++){
//                TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
//                tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
//                tabHost.setTag(i);
//            }
//        }else{
//            for (int i=0;i<tabs.length;i++){
//                TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView1(i));
//                if(i==signMark){
//                    tabHost.addTab(tabSpec,TabDb.getFragments()[signMark],null);
//
//                }else{
//                    tabHost.addTab(tabSpec,TabDb.getFragments()[i],null);
//                }
//                tabHost.setTag(i);
//            }
//        }

    }
    private View getTabView(int idx){
        View view=LayoutInflater.from(this).inflate(R.layout.footer_tabs,null);
        ((TextView)view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        if(idx==0){
            ((TextView)view.findViewById(R.id.tvTab))
                    .setTextColor(this.getResources()
                            .getColor(R.color.systemColor));
            ((ImageView)view.findViewById(R.id.ivImg))
                    .setImageResource(TabDb.getTabsImgLight()[idx]);
        }else{
            ((ImageView)view.findViewById(R.id.ivImg))
                    .setImageResource(TabDb.getTabsImg()[idx]);
        }
        return view;
    }

    private View getTabView1(int idx){
        Log.e("getTablView1","进入指定方法");
        View view=LayoutInflater.from(this).inflate(R.layout.footer_tabs,null);
        ((TextView)view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        if(idx==signMark){
            ((TextView)view.findViewById(R.id.tvTab))
                    .setTextColor(this.getResources()
                            .getColor(R.color.systemColor));
            ((ImageView)view.findViewById(R.id.ivImg))
                    .setImageResource(TabDb.getTabsImgLight()[idx]);
            Log.e("signMark:"," "+idx);
        }else{
            ((TextView)view.findViewById(R.id.tvTab))
                    .setTextColor(this.getResources()
                            .getColor(R.color.tab_text_color));
            ((ImageView)view.findViewById(R.id.ivImg))
                    .setImageResource(TabDb.getTabsImg()[idx]);
            Log.e("不是红色的idx:"," "+idx);
        }
        return view;
    }

    @Override
    public void onTabChanged(String tabId) {
        updateTab();
        Log.e("进入了更新方法：","onTabChanged");
    }
    private void updateTab(){
        TabWidget tabw=tabHost.getTabWidget();
        for(int i=0;i<tabw.getChildCount();i++){
            View view=tabw.getChildAt(i);
            ImageView iv=(ImageView)view.findViewById(R.id.ivImg);
            if(i==tabHost.getCurrentTab()){
                ((TextView)view.findViewById(R.id.tvTab))
                        .setTextColor(this.getResources().getColor(R.color.systemColor));
                iv.setImageResource(TabDb.getTabsImgLight()[i]);
            }else{
                ((TextView)view.findViewById(R.id.tvTab))
                        .setTextColor(getResources()
                                .getColor(R.color.foot_txt_gray));
                iv.setImageResource(TabDb.getTabsImg()[i]);
            }

        }
    }

}
