package com.example.asus.xiaoliu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.DynamicFragment;
import com.example.asus.xiaoliu.fragment.IndexFragment;
import com.example.asus.xiaoliu.fragment.MessageFragment;
import com.example.asus.xiaoliu.fragment.MyFragment;
import com.example.asus.xiaoliu.fragment.SignFragment;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

import cn.bmob.v3.BmobUser;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private IndexFragment MindexFragment;
    private MessageFragment MmessageFragment;
    private SignFragment MsignFragment;
    private DynamicFragment MdynamicFragment;
    private MyFragment MmyFragment;
    private Fragment mCurrent;

    private RelativeLayout indexLayout;
    private RelativeLayout messageLayout;
    private RelativeLayout signLayout;
    private RelativeLayout dynamicLayout;
    private RelativeLayout myLayout;

    private TextView indexView;
    private TextView messageView;
    private TextView signView;
    private TextView dynamicView;
    private TextView myView;

    private TextView tvIndex;
    private TextView tvMessage;
    private TextView tvSign;
    private TextView tvDynamic;
    private TextView tvMy;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        ActivityManager.addActivity(this);
    //    changeBackgroundIcon();

        //设置用户的手机型号
        BmobUser.getCurrentUser(User.class).setDevice(Build.MODEL);

        initView();

        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(getIntent().getStringExtra("signMark")==null){
            MindexFragment=new IndexFragment();
            fragmentTransaction.replace(R.id.content_layout,MindexFragment);
            Log.e("没有传值","首页");
        }else{
            Intent intent = getIntent();
            String sign = getIntent().getStringExtra("signMark");
            if("1".equals(sign)){
                MindexFragment=new IndexFragment();
                fragmentTransaction.replace(R.id.content_layout,MindexFragment);
            }else if("2".equals(sign)){
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi1);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo);
                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvMessage.setTextColor(this.getResources().getColor(R.color.systemColor));
                MmessageFragment=new MessageFragment();
                fragmentTransaction.replace(R.id.content_layout,MmessageFragment);
            }else if("3".equals(sign)){
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai1);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo);
                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvDynamic.setTextColor(this.getResources().getColor(R.color.systemColor));
                MdynamicFragment=new DynamicFragment();
                fragmentTransaction.replace(R.id.content_layout,MdynamicFragment);
            }else if("4".equals(sign)){
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan1);
                myView.setBackgroundResource(R.drawable.wo);
                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvSign.setTextColor(this.getResources().getColor(R.color.systemColor));
                MsignFragment=new SignFragment();
                fragmentTransaction.replace(R.id.content_layout,MsignFragment);
            }else if("5".equals(sign)){
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo1);
                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvMy.setTextColor(this.getResources().getColor(R.color.systemColor));
                Log.e("intent传递","5");
                MmyFragment=new MyFragment();
                fragmentTransaction.replace(R.id.content_layout,MmyFragment);
            }
        }
        fragmentTransaction.commit();

    }
    private void initView(){
        indexLayout=(RelativeLayout)findViewById(R.id.index_layout_view);
        indexLayout.setOnClickListener(this);
        messageLayout=(RelativeLayout)findViewById(R.id.message_layout_view);
        messageLayout.setOnClickListener(this);
        dynamicLayout=(RelativeLayout)findViewById(R.id.dynamic_layout_view);
        dynamicLayout.setOnClickListener(this);
        signLayout=(RelativeLayout)findViewById(R.id.sign_layout_view);
        signLayout.setOnClickListener(this);
        myLayout=(RelativeLayout)findViewById(R.id.my_layout_view);
        myLayout.setOnClickListener(this);

        indexView=(TextView)findViewById(R.id.index_image_view);
        messageView=(TextView)findViewById(R.id.message_image_view);
        dynamicView=(TextView)findViewById(R.id.dynamic_image_view);
        signView=(TextView)findViewById(R.id.sign_image_view);
        myView=(TextView)findViewById(R.id.my_image_view);

        tvIndex=findViewById(R.id.index_text_view);
        tvMessage=findViewById(R.id.message_text_view);
        tvDynamic=findViewById(R.id.dynamic_text_view);
        tvSign=findViewById(R.id.sign_text_view);
        tvMy=findViewById(R.id.my_text_view);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void hideFragment(Fragment fragment, FragmentTransaction fragmentTransaction){
        if(fragment!=null){
            fragmentTransaction.hide(fragment);
        }
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.index_layout_view:
                indexView.setBackgroundResource(R.drawable.shouye1);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo);

                tvIndex.setTextColor(this.getResources().getColor(R.color.systemColor));
                tvMessage.setTextColor(this.getResources().getColor(R.color.black));
                tvDynamic.setTextColor(this.getResources().getColor(R.color.black));
                tvSign.setTextColor(this.getResources().getColor(R.color.black));
                tvMy.setTextColor(this.getResources().getColor(R.color.black));

                hideFragment(MmessageFragment,fragmentTransaction);
                hideFragment(MdynamicFragment,fragmentTransaction);
                hideFragment(MsignFragment,fragmentTransaction);
                hideFragment(MmyFragment,fragmentTransaction);

                if(MindexFragment==null){
                    MindexFragment=new IndexFragment();
                    fragmentTransaction.add(R.id.content_layout,MindexFragment);
                }else{
                    mCurrent=MindexFragment;
                    fragmentTransaction.show(MindexFragment);
                }
                break;
            case R.id.message_layout_view:
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi1);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo);

                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvMessage.setTextColor(this.getResources().getColor(R.color.systemColor));
                tvDynamic.setTextColor(this.getResources().getColor(R.color.black));
                tvSign.setTextColor(this.getResources().getColor(R.color.black));
                tvMy.setTextColor(this.getResources().getColor(R.color.black));

                hideFragment(MindexFragment,fragmentTransaction);
                hideFragment(MdynamicFragment,fragmentTransaction);
                hideFragment(MsignFragment,fragmentTransaction);
                hideFragment(MmyFragment,fragmentTransaction);

                if(MmessageFragment==null){
                    MmessageFragment=new MessageFragment();
                    fragmentTransaction.add(R.id.content_layout,MmessageFragment);
                }else{
                    mCurrent=MmessageFragment;
                    fragmentTransaction.show(MmessageFragment);
                }
                break;
            case R.id.dynamic_layout_view:
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai1);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo);

                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvMessage.setTextColor(this.getResources().getColor(R.color.black));
                tvDynamic.setTextColor(this.getResources().getColor(R.color.systemColor));
                tvSign.setTextColor(this.getResources().getColor(R.color.black));
                tvMy.setTextColor(this.getResources().getColor(R.color.black));

                hideFragment(MmessageFragment,fragmentTransaction);
                hideFragment(MsignFragment,fragmentTransaction);
                hideFragment(MindexFragment,fragmentTransaction);
                hideFragment(MmyFragment,fragmentTransaction);

                if(MdynamicFragment==null){
                    MdynamicFragment=new DynamicFragment();
                    fragmentTransaction.add(R.id.content_layout,MdynamicFragment);
                }else{
                    mCurrent=MdynamicFragment;
                    fragmentTransaction.show(MdynamicFragment);
                }
                break;
            case R.id.sign_layout_view:
            indexView.setBackgroundResource(R.drawable.shouye);
            messageView.setBackgroundResource(R.drawable.xiaoxi);
            dynamicView.setBackgroundResource(R.drawable.dongtai);
            signView.setBackgroundResource(R.drawable.dingdan1);
            myView.setBackgroundResource(R.drawable.wo);

            tvIndex.setTextColor(this.getResources().getColor(R.color.black));
            tvMessage.setTextColor(this.getResources().getColor(R.color.black));
            tvDynamic.setTextColor(this.getResources().getColor(R.color.black));
            tvSign.setTextColor(this.getResources().getColor(R.color.systemColor));
            tvMy.setTextColor(this.getResources().getColor(R.color.black));

            hideFragment(MmessageFragment,fragmentTransaction);
            hideFragment(MindexFragment,fragmentTransaction);
            hideFragment(MdynamicFragment,fragmentTransaction);
            hideFragment(MmyFragment,fragmentTransaction);

            if(MsignFragment==null){
                MsignFragment=new SignFragment();
                fragmentTransaction.add(R.id.content_layout,MsignFragment);
            }else{
                mCurrent=MsignFragment;
                fragmentTransaction.show(MsignFragment);
            }
            break;
            case R.id.my_layout_view:
                indexView.setBackgroundResource(R.drawable.shouye);
                messageView.setBackgroundResource(R.drawable.xiaoxi);
                dynamicView.setBackgroundResource(R.drawable.dongtai);
                signView.setBackgroundResource(R.drawable.dingdan);
                myView.setBackgroundResource(R.drawable.wo1);

                tvIndex.setTextColor(this.getResources().getColor(R.color.black));
                tvMessage.setTextColor(this.getResources().getColor(R.color.black));
                tvDynamic.setTextColor(this.getResources().getColor(R.color.black));
                tvSign.setTextColor(this.getResources().getColor(R.color.black));
                tvMy.setTextColor(this.getResources().getColor(R.color.systemColor));

                hideFragment(MmessageFragment,fragmentTransaction);
                hideFragment(MsignFragment,fragmentTransaction);
                hideFragment(MindexFragment,fragmentTransaction);
                hideFragment(MdynamicFragment,fragmentTransaction);

                if(MmyFragment==null){
                    MmyFragment=new MyFragment();
                    fragmentTransaction.add(R.id.content_layout,MmyFragment);
                }else{
                    mCurrent=MmyFragment;
                    fragmentTransaction.show(MmyFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }
}
