package com.example.asus.xiaoliu.fragment.myChildren;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.HomeActivity;
import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.CustomDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditPerson extends AppCompatActivity {
    private ImageView btnzuojiantou;

    private RelativeLayout selectDate;
    private TextView currentDate;
    private CustomDatePicker datePicker;
    private String time;
    private String date;
    private EditText nicheng;
    private Spinner xingbie;
    private String userSex;
    private TextView userBirth;
    private EditText qianming;
    private EditText shichang;
    private Button btnsubmit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person);
        ActivityManager.addActivity(this);

        btnzuojiantou=(ImageView)findViewById(R.id.btn_zuojiantou);
        btnzuojiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(EditPerson.this,HomeActivity.class);
                intent.putExtra("signMark","5");
                startActivity(intent);
                finish();
            }
        });
        selectDate = (RelativeLayout) findViewById(R.id.selectDate);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getId();
                datePicker.show(date);
            }
        });
        currentDate = (TextView) findViewById(R.id.currentDate);

        initPicker();
        nicheng=(EditText)findViewById(R.id.nicheng);
        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("".equals(nicheng.getText().toString())){
                    AlertDialog.Builder builder=new AlertDialog.Builder(EditPerson.this);
                    View  view=getLayoutInflater().inflate(R.layout.dialog_view,null);
                    builder.setTitle("您的昵称未填写信息请返回填写信息！");
                    builder.setView(view);
                    builder.setNegativeButton("返回",null);
                    builder.create().show();
                }
            }
        });
        //获得spinner值
        xingbie=(Spinner)findViewById(R.id.xingbie);
        userSex=(String)xingbie.getSelectedItem();
        xingbie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userSex=(String)xingbie.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userBirth=(TextView) findViewById(R.id.currentDate);
        qianming=(EditText)findViewById(R.id.qianming);
        shichang=(EditText)findViewById(R.id.yanggoushichang);
        btnsubmit=(Button)findViewById(R.id.btn_submit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = BmobUser.getCurrentUser(User.class);
                if(!user.getUsername().equals(nicheng.getText().toString())) {
                    user.setUsername(nicheng.getText().toString());
                }
                String str=userBirth.getText().toString();
                Log.e("msg",str);
                Date date=getDate(str);
                try {
                    int age=getAge(date);
                    Log.e("年龄", Integer.toString(age));
                    int sc= Integer.parseInt(shichang.getText().toString());
                    user.setUserAge(age);
                    user.setUserBirthday(str);
                    user.setUserSex(userSex);
                    user.setWorkYear(sc);
                    user.setUserMessage(qianming.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.e("msg","资料修改成功");
                            Toast.makeText(EditPerson.this,"修改成功", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.setClass(EditPerson.this,HomeActivity.class);
                            intent.putExtra("signMark","5");
                            String nicheng1=nicheng.getText().toString();
                            String shic=shichang.getText().toString();
                            intent.putExtra("shichang",shic);
                            intent.putExtra("nicheng",nicheng1);
                            startActivity(intent);
                        } else {
                            Log.e("msg", "失败"+e.toString());
                        }
                    }
                });
            }
        });
        selectUser();
    }
    public  void selectUser(){
        User user1= BmobUser.getCurrentUser(User.class);
        if(user1!=null) {
            BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
            categoryBmobQuery.addWhereEqualTo("objectId", user1.getObjectId());
            categoryBmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> object, BmobException e) {
                    if (e == null) {
                        Log.e("Identidity", "查询成功" + object.get(0).getObjectId());
                        BmobFile bmobFile=object.get(0).getUserImage();
                        //如果用户设置了昵称就显示用户设置的如果用户没有设置昵称就显示自定义的一个昵称
                        if(object.get(0).getUsername() != null && !"".equals(object.get(0).getUsername())){
                            nicheng.setText(object.get(0).getUsername());
                        }
                        //性别的设置
                        if(object.get(0).getUserSex() != null && !"".equals(object.get(0).getUserSex())){
                            if(object.get(0).getUserSex().equals("男")){
                                xingbie.setSelection(0);
                            }else{
                                xingbie.setSelection(1);
                            }

                        }
                        //生日设置
                        if(object.get(0).getUserBirthday()!=null && !"".equals(object.get(0).getUserBirthday())) {
                            userBirth.setText(object.get(0).getUserBirthday());
                        }
                        //简介
                        if(object.get(0).getUserMessage()!=null && !"".equals(object.get(0).getUserMessage())){
                            qianming.setText(object.get(0).getUserMessage());
                        }
                        //如果用户设置了养狗年份或者遛狗师担任年份就显示用户设置的如果用户没有设置昵称就显示自定义的一个年份
                        if(Integer.toString(object.get(0).getWorkYear()) != null && !"".equals(Integer.toString(object.get(0).getWorkYear()))){
                            shichang.setText(Integer.toString(object.get(0).getWorkYear()));
                        }
                    } else {
                        Log.e("Identidity", "查询失败" + e.toString());
                    }
                }
            });
        }
    }
    private void initPicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        time = sdf.format(new Date());
        date = time.split(" ")[0];
        //设置当前显示的日期
        currentDate.setText(date);
        /**
         * 设置年月日
         */
        datePicker = new CustomDatePicker(this, "请选择日期", new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                currentDate.setText(time.split(" ")[0]);
            }
        }, "1930-01-01 00:00", time);
        datePicker.showSpecificTime(false); //显示时和分
        datePicker.setIsLoop(false);
        datePicker.setDayIsLoop(true);
        datePicker.setMonIsLoop(true);
    }
    public Date getDate(String userBirth){

        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date date=format.parse(userBirth);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getAge(Date date)throws Exception {
        Calendar cal= Calendar.getInstance();
        if (cal.before(date)){
            throw new IllegalArgumentException(
                    "错误");
        }
        int yearNow=cal.get(Calendar.YEAR);
        int monthNow=cal.get(Calendar.MONTH);
        int dayOfMonthNow=cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(date);

        int yearBirth=cal.get(Calendar.YEAR);
        int monthBirth=cal.get(Calendar.MONTH);
        int dayOfMonthBirth=cal.get(Calendar.DAY_OF_MONTH);

        int age=yearNow-yearBirth;
        return age;
    }
}
