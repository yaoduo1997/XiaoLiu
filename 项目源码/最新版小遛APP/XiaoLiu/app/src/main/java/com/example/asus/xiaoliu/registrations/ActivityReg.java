package com.example.asus.xiaoliu.registrations;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;

public class ActivityReg extends AppCompatActivity{

    private Button btnDogManager;
    private Button btnDogWalkers;
    private PropertyAnimatorClickListener paClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distinct);
        ActivityManager.addActivity(this);

        btnDogManager = (Button)findViewById(R.id.btn_dogManager);
        btnDogWalkers = (Button)findViewById(R.id.btn_dogWalkers);

        paClickListener = new PropertyAnimatorClickListener();
        btnDogManager.setOnClickListener(paClickListener);
        btnDogWalkers.setOnClickListener(paClickListener);
    }
    class PropertyAnimatorClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
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
}
