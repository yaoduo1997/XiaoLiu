package com.example.asus.xiaoliu.registrations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.TimeCount;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegistStepOne extends AppCompatActivity {

    private ImageView ivReturn;
    private Button userAgreement;
    private LinearLayout llPhones;
    private Button btnSign;
    private String identity;
    private EditText etPhone;
    private EditText etCode;
    private Button btnGetCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActivityManager.addActivity(this);

        Intent intent = getIntent();
        identity = intent.getStringExtra("identity");
        etCode=(EditText)findViewById(R.id.et_verificaioncode);
        etPhone=(EditText)findViewById(R.id.et_yourphone);
        btnGetCode=(Button)findViewById(R.id.btn_getvercode);
        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.requestSMSCode(etPhone.getText().toString(), "", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        TimeCount timeCount = new TimeCount(60000, 1000,btnGetCode);
                        timeCount.start();
                        if (e == null) {
                            Log.e("Login","短信发送成功");
                        } else {
                            Log.e("Login","短信发送失败"+e.toString());
                        }
                    }
                });
            }
        });

        //查看用户服务
        userAgreement = (Button)findViewById(R.id.btn_agreement);
        userAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistStepOne.this,UserAgreement.class));
            }
        });
        //返回上个页面
        ivReturn = (ImageView)findViewById(R.id.iv_return);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistStepOne.this,ActivityReg.class));
            }
        });
//        //获得区号
        llPhones = (LinearLayout)findViewById(R.id.ll_phones);
        llPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistStepOne.this,Phones.class));
            }
        });
        //注册之后进入完善用户信息的界面,身份不同进入的界面也不同
        btnSign = (Button)findViewById(R.id.btn_sign);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setMobilePhoneNumber(etPhone.getText().toString());
                user.setUserIdentity(identity);
                user.signOrLogin(etCode.getText().toString(), new SaveListener<User>() {

                    @Override
                    public void done(User user,BmobException e) {
                        if (e == null) {
                            Log.e("RegistStepOne","短信注册或登录成功：");
                            if("狗狗主人".equals(identity)){
                                Intent intent1 = new Intent();
                                intent1.setClass(RegistStepOne.this,CustomerMessage.class);
                                intent1.putExtra("identity","狗狗主人");
                                startActivity(intent1);
                            }else if("遛狗师".equals(identity)){
                                Intent intent1 = new Intent();
                                intent1.setClass(RegistStepOne.this,CustomerMessage.class);
                                intent1.putExtra("identity","遛狗师");
                                startActivity(intent1);
                            }else {
                                Log.e("registStepOne","传参失败");
                            }
                        } else {
                            Log.e("RegistStepOne","短信注册或登录失败："+e.toString());
                        }
                    }
                });
            }
        });
    }
}
