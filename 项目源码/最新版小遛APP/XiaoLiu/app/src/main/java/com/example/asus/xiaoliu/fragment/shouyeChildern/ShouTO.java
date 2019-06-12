package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class ShouTO extends AppCompatActivity {
    private ImageView imgPre = null;
    private ImageView imgNext = null;
    private Spinner spinner = null;
    private String dateCir;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouto);
        ActivityManager.addActivity(this);

        spinner = (Spinner)findViewById(R.id.spn_1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources resources=getResources();
                String[]good=resources.getStringArray(R.array.dogdate);
                dateCir=good[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ShouTO.this,"未选中日期",Toast.LENGTH_SHORT).show();
            }
        });

        imgNext=(ImageView)findViewById(R.id.iv_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("dateC",dateCir);
                intent1.setClass(ShouTO.this,ShouTT.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(ShouTO.this).toBundle());
            }
        });

        imgPre=(ImageView)findViewById(R.id.iv_pre);
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ShouTO.this,HomeActivity.class));
               finish();
            }
        });

    }
}
