package com.example.asus.xiaoliu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouFour;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouOO;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouTO;
import com.example.asus.xiaoliu.fragment.shouyeChildern.ShouThree;

import cn.bmob.v3.BmobUser;

public class IndexFragment extends Fragment {

    private LinearLayout llShou1;
    private LinearLayout llShou2;
    private LinearLayout llShou3;
    private LinearLayout llShou4;

    private ImageView ivShou1;
    private ImageView ivShou2;
    private ImageView ivShou3;
    private ImageView ivShou4;

    public IndexFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_index_layout,container,false);
        llShou1 = (LinearLayout)view.findViewById(R.id.ll_shou1);
        llShou2 = (LinearLayout)view.findViewById(R.id.ll_shou2);
        llShou3 = (LinearLayout)view.findViewById(R.id.ll_shou3);
        llShou4 = (LinearLayout)view.findViewById(R.id.ll_shou4);
        if(BmobUser.getCurrentUser(User.class).getUserIdentity().equals("狗狗主人")){
            llShou4.setVisibility(View.GONE);
        }else if(BmobUser.getCurrentUser(User.class).getUserIdentity().equals("遛狗师")){
            llShou1.setVisibility(View.GONE);
            llShou2.setVisibility(View.GONE);
        }

        ivShou1 = (ImageView)view.findViewById(R.id.ivShou1);
        ivShou1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShouOO.class);
                startActivity(intent);
            }
        });
        ivShou2 = (ImageView)view.findViewById(R.id.ivShou2);
        ivShou2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShouTO.class);
                startActivity(intent);
            }
        });
        ivShou3 = (ImageView)view.findViewById(R.id.ivShou3);
        ivShou3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ShouThree.class);
                startActivity(intent);
            }
        });
        ivShou4 = (ImageView)view.findViewById(R.id.ivShou4);
        ivShou4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ShouFour.class));
            }
        });
        return view;

    }
}
