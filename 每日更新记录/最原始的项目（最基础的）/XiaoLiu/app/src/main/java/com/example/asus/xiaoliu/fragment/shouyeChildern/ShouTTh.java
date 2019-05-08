package com.example.asus.xiaoliu.fragment.shouyeChildern;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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


public class ShouTTh extends Activity implements View.OnClickListener{
    private ImageView selectTime;
    private TextView  currentTime;
    private String date;
    //    currentDate
    private CustomDatePicker datePicker,timePicker;
    private String time;
    private ImageView ivTime;
    private ImageView imgPre;
    private ImageView imgNext;
    private String dateC;
    private String selectDog;
    private String selectAddress;

    private  String selectDuration;
    private Spinner spinner;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shouot);
        ActivityManager.addActivity(this);

        TextView tvShouOne = (TextView)findViewById(R.id.tv_shouone);
        tvShouOne.setVisibility(View.GONE);//不显示也不占空间

        selectTime =  (ImageView)findViewById(R.id.iv_time);
        selectTime.setOnClickListener(this);
        currentTime = (TextView) findViewById(R.id.tv_time);
        initPicker();


        Intent intent = getIntent();
        dateC = intent.getStringExtra("dateC");
        selectDog = intent.getStringExtra("selectDog");
        selectAddress = intent.getStringExtra("selectAddress");

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
                Toast.makeText(ShouTTh.this,"无",Toast.LENGTH_SHORT).show();
            }
        });

        imgNext=(ImageView)findViewById(R.id.iv_next);
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("dateC",dateC);
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("selectAddress",selectAddress);
                intent1.putExtra("startTime",currentTime.getText());
                intent1.putExtra("duration",selectDuration);
                intent1.setClass(ShouTTh.this,ShouTFi.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(ShouTTh.this).toBundle());
            }
        });

        imgPre=(ImageView)findViewById(R.id.iv_pre);
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.putExtra("dateC",dateC);
                intent1.putExtra("selectDog",selectDog);
                intent1.putExtra("selectAddress",selectAddress);
                intent1.setClass(ShouTTh.this,ShouTT.class);
                startActivity(intent1);
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
