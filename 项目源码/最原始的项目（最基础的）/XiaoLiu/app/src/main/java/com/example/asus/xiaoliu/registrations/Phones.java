package com.example.asus.xiaoliu.registrations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class Phones extends AppCompatActivity {

    private ImageView ivFanhui;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_phone);
        ActivityManager.addActivity(this);

        ivFanhui = (ImageView)findViewById(R.id.iv_fanhui);
        ivFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Phones.this,RegistStepOne.class));
            }
        });
    }
}
