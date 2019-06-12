package com.example.asus.xiaoliu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.fragment.messageChildFragment.MessageComment;
import com.example.asus.xiaoliu.fragment.messageChildFragment.MyDynamic;
import com.example.asus.xiaoliu.fragment.messageChildFragment.PraiseActivity;

public class MessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_message_layout);


        LinearLayout zhuanfa=findViewById(R.id.ll_zhuanfa);
        zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),MyDynamic.class);
                startActivity(intent);
            }
        });

        LinearLayout comment=findViewById(R.id.ll_pinglun);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(), MessageComment.class);
                startActivity(intent);
            }
        });

        LinearLayout zan=findViewById(R.id.ll_zan);
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), PraiseActivity.class);
                //intent.putExtra("signMark","2");
                startActivity(intent);
            }
        });
    }
}
