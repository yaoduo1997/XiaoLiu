package com.example.asus.xiaoliu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.LoginCache;
import com.example.asus.xiaoliu.functiontools.Show;
import com.example.asus.xiaoliu.functiontools.TimeCount;
import com.example.asus.xiaoliu.registrations.ActivityReg;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends Activity implements View.OnClickListener{

    private TimeCount time;
    private TextView tvPwdLogin;
    private TextView tvSign;
    private EditText etPhone;
    private EditText etYanZhengMa;
    private Button btnLogin;
    private Button btnGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.addActivity(this);

        etPhone=findViewById(R.id.et_yourphone);
        etYanZhengMa=findViewById(R.id.et_verificaioncode);
        btnLogin=findViewById(R.id.btn_login);
        btnGetCode=findViewById(R.id.btn_getvercode);
        tvPwdLogin=findViewById(R.id.tv_login_mi);
        tvSign=findViewById(R.id.tv_register);

        btnGetCode.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvPwdLogin.setOnClickListener(this);
        tvSign.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String phone=etPhone.getText().toString();
                String code=etYanZhengMa.getText().toString();
                if(phone!=null && code!=null && !"".equals(phone) && !"".equals(code)) {
                    BmobUser.loginBySMSCode(phone, code, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                LoginCache.fetchUserInfo();
                                Log.e("MainActivity", "短信登录成功");
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this,"该用户未注册过,请去注册",Toast.LENGTH_SHORT).show();
                                Log.e("MainActivity", "短信登录失败");
                            }
                        }
                    });
                }else{
                    Show.toast(this, "您输入的手机号或者验证码为空！");
                }
                break;
            case R.id.tv_login_mi:
                startActivity(new Intent(MainActivity.this,LoginMi.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(MainActivity.this,ActivityReg.class));
                break;
            case R.id.btn_getvercode:
                final String phone1=etPhone.getText().toString();
                if(phone1!=null &&!"".equals(phone1)) {
                    TimeCount timeCount = new TimeCount(60000, 1000,btnGetCode);
                    timeCount.start();
                    BmobSMS.requestSMSCode(phone1, "", new QueryListener<Integer>() {
                        @Override
                        public void done(Integer smsId, BmobException e) {
                            if (e == null) {
                                Log.e("Login", "短信发送成功");
                            } else {
                                Log.e("Login", "短信发送失败" + e.toString());
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "您输入的手机号为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
