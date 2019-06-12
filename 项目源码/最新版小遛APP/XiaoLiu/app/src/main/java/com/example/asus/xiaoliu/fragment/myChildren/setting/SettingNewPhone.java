package com.example.asus.xiaoliu.fragment.myChildren.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.entity.User;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.LoginCache;
import com.example.asus.xiaoliu.functiontools.Show;
import com.example.asus.xiaoliu.functiontools.TimeCount;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class SettingNewPhone extends AppCompatActivity {
    private EditText etPhone;
    private EditText etYanZhengMa;
    private Button btnYanZhengMa;
    private Button btnOk;
    private ImageView ivPre;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_newphone);
        ActivityManager.addActivity(this);

        etPhone=(EditText)findViewById(R.id.et_newphone);
        etYanZhengMa=(EditText)findViewById(R.id.et_yanzhengma);
        btnYanZhengMa=(Button)findViewById(R.id.btn_yanzhengma);
        btnOk=(Button)findViewById(R.id.btn_OK);
        ivPre=(ImageView)findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SettingNewPhone.this,SettingSafe.class);
                startActivity(intent);
            }
        });

        btnYanZhengMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone=etPhone.getText().toString();
                if(phone!=null &&!"".equals(phone)) {
                    if(phone.equals(BmobUser.getCurrentUser(User.class).getMobilePhoneNumber())){
                        Show.toast(SettingNewPhone.this,"您输入的手机号跟之前绑定的号码一致，无需重复绑定！");
                    }else{
                        TimeCount timeCount = new TimeCount(60000, 1000,btnYanZhengMa);
                        timeCount.start();
                        BmobSMS.requestSMSCode(phone, "小遛", new QueryListener<Integer>() {
                            @Override
                            public void done(Integer smsId, BmobException e) {
                                if (e == null) {
                                    Log.e("SettingNewPhone","发送验证码成功，短信ID：" + smsId + "\n");
                                } else {
                                    Log.e("SettingNewPhone","发送短信失败" + e.toString() + "\n");
                                }
                            }
                        });
                    }
                }else {
                    Show.toast(SettingNewPhone.this,"您输入的手机号为空！");
                }
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=etYanZhengMa.getText().toString();
                final String phone=etPhone.getText().toString();
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                           Log.e("SettingPhone","验证码验证成功");
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setMobilePhoneNumber(phone);
                            //绑定
                            user.setMobilePhoneNumberVerified(true);
                            //解绑
                            //user.setMobilePhoneNumberVerified(false);
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                       Log.e("phone","解绑或者绑定手机号成功");
                                       Show.toast(SettingNewPhone.this,"更换绑定手机号成功！");
                                       //更新缓存的用户信息
                                        LoginCache.fetchUserInfo();
                                       Intent intent=new Intent();
                                       intent.setClass(SettingNewPhone.this,SetingMain.class);
                                       startActivity(intent);
                                    } else {
                                       Log.e("phone","解绑或者绑定手机号失败");
                                    }
                                }
                            });
                        } else {
                          Log.e("settingphone","短信验证码验证失败");
                        }
                    }
                });
            }
        });

    }
}
