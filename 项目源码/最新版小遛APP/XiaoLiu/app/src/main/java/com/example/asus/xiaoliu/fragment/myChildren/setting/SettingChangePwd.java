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
import android.widget.Toast;

import com.example.asus.xiaoliu.R;
import com.example.asus.xiaoliu.functiontools.ActivityManager;
import com.example.asus.xiaoliu.functiontools.LoginCache;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SettingChangePwd extends AppCompatActivity {
    private EditText etOldPwd;
    private EditText etNewPwd;
    private EditText etNewPwd2;
    private Button btnOk;
    private ImageView ivPre;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_changepassword);
        ActivityManager.addActivity(this);

        etOldPwd=(EditText)findViewById(R.id.et_oldpassword);
        etNewPwd=(EditText)findViewById(R.id.et_newpassword);
        etNewPwd2=(EditText)findViewById(R.id.et_newpwd);
        btnOk=(Button)findViewById(R.id.btn_OK);
        ivPre=(ImageView)findViewById(R.id.iv_fanhui);
        ivPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(SettingChangePwd.this,SettingSafe.class);
                startActivity(intent);
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd=etOldPwd.getText().toString();
                String pwd2=etNewPwd.getText().toString();
                String pwd3=etNewPwd2.getText().toString();
                if(pwd!=null&&!"".equals(pwd)){
                    if(pwd2!=null&&!"".equals(pwd2)){
                        if(pwd3!=null&&!"".equals(pwd3)){
                            if(pwd2.length()>=6&&pwd2.length()<=16&&pwd3.length()>=6&&pwd3.length()<=16) {
                                if (pwd2.equals(pwd3)) {
                                    BmobUser.updateCurrentUserPassword(pwd, pwd2, new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Log.e("changepwd", "改密码成功");
                                                Intent intent=new Intent();
                                                intent.setClass(SettingChangePwd.this,SetingMain.class);
                                                //Toast.makeText(SettingChangePwd.this,"以后请跳转到登录界面，清除缓存", Toast.LENGTH_SHORT).show();
                                                //退出登录
                                                //BmobUser.logOut();
                                                //更新缓存信息
                                                LoginCache.fetchUserInfo();
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//关掉所要到的界面中间的activity
                                                startActivity(intent);
                                            } else {
                                                Log.e("changepwd", "改密码失败" + e.toString());
                                                Toast.makeText(SettingChangePwd.this, e.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    Toast.makeText(SettingChangePwd.this,"确认密码不一致", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(SettingChangePwd.this,"新的密码应该控制在6到16位之间", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SettingChangePwd.this,"确认密码不能为空", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SettingChangePwd.this,"新的密码不能为空", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SettingChangePwd.this,"旧的密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
