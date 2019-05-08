package com.example.asus.xiaoliu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.fragment.messageChildFragment.MessageComment;
import com.example.asus.xiaoliu.fragment.messageChildFragment.MyDynamic;
import com.example.asus.xiaoliu.fragment.messageChildFragment.PraiseActivity;

public class MessageFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message_layout,container,false);

        LinearLayout zhuanfa=(LinearLayout)view.findViewById(R.id.ll_zhuanfa);
        zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyDynamic.class);
                startActivity(intent);
            }
        });

        LinearLayout comment=(LinearLayout)view.findViewById(R.id.ll_pinglun);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), MessageComment.class);
                startActivity(intent);
            }
        });

        LinearLayout zan=(LinearLayout)view.findViewById(R.id.ll_zan);
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PraiseActivity.class);
                //intent.putExtra("signMark","2");
                startActivity(intent);
            }
        });

        return view;

    }

}
