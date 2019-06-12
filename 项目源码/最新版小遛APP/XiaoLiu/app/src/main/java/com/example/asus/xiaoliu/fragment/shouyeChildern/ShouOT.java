package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShouOT extends AppCompatActivity implements View.OnClickListener{
    private  TextView tvTime;

    private ImageView selectTime;
    private TextView  currentTime;
    private String date;
    //    currentDate
    private CustomDatePicker datePicker,timePicker;
    private String time;

    private  String selectDuration;
    private Spinner spinner;

    private ImageView ivCalender;
    private ImageView ivTime;
    private ImageView imgPre;
    private ImageView imgNext;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouot);
        ActivityManager.addActivity(this);

        TextView tvShouTwo = (TextView)findViewById(R.id.tv_shoutwo);
        tvShouTwo.setVisibility(View.GONE);//不显示也不占空间

        selectTime =  (ImageView) findViewById(R.id.iv_time);
        selectTime.setOnClickListener(this);
        currentTime = (TextView) findViewById(R.id.tv_time);
        initPicker();

        tvTime=(TextView)findViewById(R.id.tv_time);
        final Intent intent=getIntent();
        final String selectDog=intent.getStringExtra("selectDog");
        final String selectAddress = intent.getStringExtra("selectAddress");
        Log.e("选中的地址：",selectAddress);

        spinner=(Spinner)findViewById(R.id.spn_1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Resources resources=getResources();
                String[]dur=resources.getStringArray(R.array.duration);
                selectDuration=dur[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(ShouOT.this,"无",Toast.LENGTH_SHORT).show();
            }
        });

        imgNext=(ImageView)findViewById(R.id.iv_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("selectAddress",selectAddress);
                intent1.putExtra("startTime",tvTime.getText());
                intent1.putExtra("duration",selectDuration);
                intent1.setClass(ShouOT.this,ShouOF.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(ShouOT.this).toBundle());
            }
        });

        imgPre=(ImageView)findViewById(R.id.iv_pre);
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShouOT.this,ShouOO.class));
                finish();
            }
        });
    }
    private void initPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
//        currentDate.setText(date);
        //设置当前显示的时间
        currentTime.setText(time);

        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
//                currentDate.setText(time.split(" ")[0]);
            }
        }, "2007-01-01 00:00", time);
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);

        timePicker = new CustomDatePicker(this, "请选择时间", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                currentTime.setText(time);
            }
        }, "2007-01-01 00:00", "2027-12-31 23:59");//"2027-12-31 23:59"
        timePicker.showSpecificTime(true);
        timePicker.setIsLoop(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.selectDate:
//                // 日期格式为yyyy-MM-dd
//                datePicker.show(date);
//                break;

            case R.id.iv_time:
                // 日期格式为yyyy-MM-dd HH:mm
                timePicker.show(time);
                break;
        }
    }
}
