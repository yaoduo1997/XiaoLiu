package com.example.asus.xiaoliu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus.xiaoliu.Async.DynamicAsy.FindAllDynamicAsy;
import com.example.asus.xiaoliu.MessageActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.fragment.dynamicChildren.ReleaseDynamic;

import java.util.concurrent.Executors;

public class DynamicFragment extends Fragment {
    private View mContentView;
    private ListView listView;

    private User user;
    private int flag=0;
    public DynamicFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_dynamic_layout,container,false);

       listView=view.findViewById(R.id.dynamic_list);
        ImageView ivToMessage=view.findViewById(R.id.iv_to_Message);
        ivToMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        ImageView imageView=view.findViewById(R.id.iv_add_dynamic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getContext(), ReleaseDynamic.class);
                startActivity(intent);
            }
        });
        new FindAllDynamicAsy(getActivity(),listView).executeOnExecutor(Executors.newCachedThreadPool());

       return  view;

    }

}
