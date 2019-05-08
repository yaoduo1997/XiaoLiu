package com.example.asus.xiaoliu.registrations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class ActivityReg extends AppCompatActivity implements View.OnClickListener{

    private Button btnDogManager;
    private Button btnDogWalkers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distinct);
        ActivityManager.addActivity(this);

        btnDogManager = (Button)findViewById(R.id.btn_dogManager);
        btnDogWalkers = (Button)findViewById(R.id.btn_dogWalkers);

        btnDogManager.setOnClickListener(this);
        btnDogWalkers.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_dogManager:
                intent.setClass(ActivityReg.this,RegistStepOne.class);
                intent.putExtra("identity","狗狗主人");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_dogWalkers:
                intent.setClass(ActivityReg.this,RegistStepOne.class);
                intent.putExtra("identity","遛狗师");
                startActivity(intent);
                finish();
                break;
        }
    }
}
